package com.nf.db.handler.properties;

import com.nf.db.PropertyHandler;

import java.time.LocalDate;
import java.sql.Date;

public class LocalDatePropertyHandler implements PropertyHandler {
    @Override
    public boolean match(Class<?> beanCls, Object value) {
        return value instanceof Date && beanCls == LocalDate.class;
    }

    @Override
    public Object apply(Class<?> beanCls, Object value) {
        return ((Date)value).toLocalDate();
    }
}
