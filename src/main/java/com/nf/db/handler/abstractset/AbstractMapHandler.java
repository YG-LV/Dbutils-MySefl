package com.nf.db.handler.abstractset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapHandler<K,V> extends AbstractResultSetHandler<Map<K,V>>{
    protected int keyColumnIndex = 1;
    protected String keyColumnName = null;
    public AbstractMapHandler(){this(1,null);}
    protected AbstractMapHandler(int keyColumnIndex, String keyColumnName) {
        setKeyColumn(keyColumnIndex,keyColumnName);
    }
    @Override
    public Map<K,V> handler(ResultSet rs) throws SQLException {
        Map<K,V> result = createMap();
        while (rs.next()){
            K key = this.createKey(rs);
            V value = this.createValue(rs);
            result.put(key,value);
        }
        return result;
    }

    protected Map<K,V> createMap(){
        return new HashMap<>();
    }

    protected K createKey(ResultSet rs) throws SQLException{
       return DEFAULT_ROWPROCESSOR.toScalar( rs , keyColumnName , keyColumnIndex );
    }
    protected abstract V createValue(ResultSet rs) throws SQLException;

    protected void setKeyColumn(int keyColumnIndex, String keyColumnName){
        this.keyColumnIndex = keyColumnIndex;
        this.keyColumnName = keyColumnName;
    }
}
