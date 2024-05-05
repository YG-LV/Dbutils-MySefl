package com.nf.db.handler.properties;

import com.nf.db.PropertyHandler;

import java.time.LocalDate;
import java.sql.Date;

/**
 * 判断值的类型
 * 更改值的类型
 */
public class LocalDatePropertyHandler implements PropertyHandler {
    /**
     * 判断值是否为Date类型
     * @param beanCls 反射的Class类型
     * @param value 值
     * @return 是否判断是否正确
     */
    @Override
    public boolean match(Class<?> beanCls, Object value) {
        return value instanceof Date && beanCls == LocalDate.class;
    }

    /**
     * 改变值的类型
     * @param beanCls 反射的Class类型
     * @param value 值
     * @return 转换类型值
     */
    @Override
    public Object apply(Class<?> beanCls, Object value) {
        return ((Date)value).toLocalDate();
    }
}
