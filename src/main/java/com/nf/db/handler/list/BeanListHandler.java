package com.nf.db.handler.list;

import com.nf.db.handler.abstractset.AbstractListHandler;
import com.nf.db.handler.abstractset.AbstractResultSetHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanListHandler<T> extends AbstractListHandler<T> {

    public final Class<? extends T> beanClass;
    public BeanListHandler(Class<? extends T> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public T rowHandler(ResultSet rs) throws SQLException {
        return DEFAULT_ROWPROCESSOR.toBean(rs,beanClass);
    }
}
