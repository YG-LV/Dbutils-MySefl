package com.nf.db.handler.map;

import com.nf.db.handler.abstractset.AbstractMapHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArrayMapHandler<K> extends AbstractMapHandler<K,Object[]> {

    public ArrayMapHandler(){super();}
    public ArrayMapHandler(int keyColumnIndex){
        super(keyColumnIndex,null);
    }
    public ArrayMapHandler(String keyColumnName){
        super(1,keyColumnName);
    }
    @Override
    public Object[] createValue(ResultSet rs) throws SQLException {
        return DEFAULT_ROWPROCESSOR.toArray(rs);
    }
}
