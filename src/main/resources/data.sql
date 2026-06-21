TRUNCATE TABLE comments CASCADE;
TRUNCATE TABLE bookings CASCADE;
TRUNCATE TABLE items CASCADE;
TRUNCATE TABLE users CASCADE;

INSERT INTO users (name, email)
SELECT
    'Пользователь ' || i,
    'user' || i || '@shareit.ru'
FROM generate_series(1, 150) s(i);

INSERT INTO items (user_id, name, description, available)
SELECT
    u.user_id,
    'Инструмент №' || row_number() OVER (),
    'Отличный и очень надежный инструмент для домашнего использования под номером ' || row_number() OVER (),
    TRUE
FROM users u
CROSS JOIN generate_series(1, 3) g(j)
WHERE u.user_id <= 100;

WITH RECURSIVE phrases(id, text) AS (
    VALUES
    (1, 'Я очень рад, что воспользовался этим замечательным сервисом и взял вещь. Никаких нареканий нет, состояние идеальное, работает как швейцарские точные часы.'),
    (2, 'Все прошло просто великолепно, владелец быстро ответил и все объяснил. Огромное спасибо за предоставленную возможность выполнить ремонт быстро и качественно.'),
    (3, 'Качество предмета превзошло все мои ожидания, буду обязательно брать еще. В следующий раз обязательно обращусь именно к этому проверенному человеку.'),
    (4, 'Очень полезная в хозяйстве штука, рекомендую абсолютно каждому для работы. Сделка прошла успешно, все вовремя, без лишних задержек и глупых вопросов.')
),
generated_bookings_1 AS (
    SELECT
        u.user_id,
        (SELECT item_id FROM items ORDER BY hashtext((u.user_id + g.j)::text) LIMIT 1) AS item_id,
        NOW() - (g.j || ' days')::INTERVAL - INTERVAL '5 hours' AS start_date,
        NOW() - (g.j || ' days')::INTERVAL AS end_date,
        'APPROVED'::VARCHAR AS status,
        NOW() - INTERVAL '15 days' AS created_at,
        g.j AS step
    FROM users u
    CROSS JOIN generate_series(1, 3) g(j)
    WHERE u.user_id BETWEEN 101 AND 150
),
inserted_bookings_1 AS (
    INSERT INTO bookings (user_id, item_id, start_date, end_date, status, created_at)
    SELECT user_id, item_id, start_date, end_date, status, created_at FROM generated_bookings_1
    RETURNING booking_id, user_id, item_id, end_date
)
INSERT INTO comments (user_id, item_id, text, created_at)
SELECT
    b.user_id,
    b.item_id,
    (SELECT text FROM phrases WHERE id = (mod(b.booking_id, 4) + 1)),
    b.end_date + INTERVAL '5 minutes'
FROM inserted_bookings_1 b
WHERE mod(b.booking_id, 3) = 0;

WITH RECURSIVE phrases(id, text) AS (
    VALUES
    (1, 'Я очень рад, что воспользовался этим замечательным сервисом и взял вещь. Никаких нареканий нет, состояние идеальное, работает как швейцарские точные часы.'),
    (2, 'Все прошло просто великолепно, владелец быстро ответил и все объяснил. Огромное спасибо за предоставленную возможность выполнить ремонт быстро и качественно.'),
    (3, 'Качество предмета превзошло все мои ожидания, буду обязательно брать еще. В следующий раз обязательно обращусь именно к этому проверенному человеку.'),
    (4, 'Очень полезная в хозяйстве штука, рекомендую абсолютно каждому для работы. Сделка прошла успешно, все вовремя, без лишних задержек и глупых вопросов.')
),
generated_bookings_2 AS (
    SELECT
        u.user_id,
        i.item_id,
        NOW() - (g.j || ' days')::INTERVAL - INTERVAL '3 hours' AS start_date,
        NOW() - (g.j || ' days')::INTERVAL AS end_date,
        'APPROVED'::VARCHAR AS status,
        NOW() - INTERVAL '12 days' AS created_at,
        g.j AS step
    FROM users u
    CROSS JOIN generate_series(1, 2) g(j)
    JOIN items i ON i.user_id = CASE WHEN u.user_id = 100 THEN 1 ELSE u.user_id + 1 END
    WHERE u.user_id <= 100
),
inserted_bookings_2 AS (
    INSERT INTO bookings (user_id, item_id, start_date, end_date, status, created_at)
    SELECT user_id, item_id, start_date, end_date, status, created_at FROM generated_bookings_2
    RETURNING booking_id, user_id, item_id, end_date
)
INSERT INTO comments (user_id, item_id, text, created_at)
SELECT
    b.user_id,
    b.item_id,
    (SELECT text FROM phrases WHERE id = (mod(b.booking_id, 4) + 1)),
    b.end_date + INTERVAL '10 minutes'
FROM inserted_bookings_2 b
WHERE mod(b.booking_id, 4) != 0;