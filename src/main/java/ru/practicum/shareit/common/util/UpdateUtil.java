package ru.practicum.shareit.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
public class UpdateUtil<M, R> {

    public M update(M model, R request) throws Exception {
        Field[] fields = request.getClass().getDeclaredFields();

        for (Field field : fields) {
            Method getter = findGetter(request, field);
            if (getter.invoke(request) != null) {
                Method setter = findSetter(model, field);
                setter.invoke(model, getter.invoke(request));
            }
        }

        return model;
    }

    private Method findGetter(R obj, Field field) throws NoSuchMethodException {
        String fieldName = StringUtils.capitalize(field.getName());
        return obj.getClass().getDeclaredMethod("get".concat(fieldName));
    }

    private Method findSetter(M obj, Field field) throws NoSuchMethodException {
        String fieldName = StringUtils.capitalize(field.getName());
        return obj.getClass().getDeclaredMethod("set".concat(fieldName), field.getType());
    }
}
