### https://dbdiagram.io

```text
Table users {
  user_id bigint [primary key]
  name varchar(100) [not null]
  email varchar(100) [not null]
}

Table items {
  item_id bigint [primary key]
  user_id bigint [not null]
  name varchar(100) [not null]
  description text
  available boolean
}

Table bookings {
    booking_id bigint [primary key]
    user_id bigint [not null]
    item_id bigint [not null]
    start_date date [not null]
    end_date date [not null]
    status varchar(100) [not null]
    created_at [not null]
}

Ref user_items: items.user_id > users.user_id
```