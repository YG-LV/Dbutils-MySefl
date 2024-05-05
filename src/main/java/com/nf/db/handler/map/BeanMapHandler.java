package com.nf.db.handler.map;

import com.nf.db.handler.abstractset.AbstractMapHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BeanMapHandler<K,V> extends AbstractMapHandler<K,V> {
    private Class<? extends V> beanClass;
    public BeanMapHandler(Class<? extends V> beanClass) {super();this.beanClass = beanClass;}
    public BeanMapHandler(Class<? extends V> beanClass,int keyColumnIndex){super(keyColumnIndex,null);this.beanClass = beanClass;}
    public BeanMapHandler(Class<? extends V> beanClass,String keyColumnName){super(1,keyColumnName);this.beanClass = beanClass;}

    @Override
    protected V createValue(ResultSet rs) throws SQLException {
        return DEFAULT_ROWPROCESSOR.toBean(rs,beanClass);
    }
}
