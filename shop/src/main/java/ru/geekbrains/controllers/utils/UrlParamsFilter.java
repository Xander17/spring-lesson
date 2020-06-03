package ru.geekbrains.controllers.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.geekbrains.services.filters.ProductFilter;

import java.lang.reflect.Field;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlParamsFilter {

    @SneakyThrows
    public static String get(ProductFilter filter) {
        Field[] fields = filter.getClass().getDeclaredFields();
        StringBuilder s = new StringBuilder();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(filter);
            s.append("&").append(name).append("=").append(value == null ? "" : value);
        }
        return s.substring(1);
    }
}
