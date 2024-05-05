package com.nf.db;

import java.lang.reflect.Field;

public interface PropertyHandler {
    boolean match(Class<?> beanCls,Object value);
    Object apply(Class<?> beanCls, Object value);
}
