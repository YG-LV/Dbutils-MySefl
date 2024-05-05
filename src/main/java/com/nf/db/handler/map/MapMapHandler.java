package com.nf.db.handler.map;

import com.nf.db.handler.abstractset.AbstractMapHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MapMapHandler<K> extends AbstractMapHandler<K, Map<String,Object>> {
    public MapMapHandler() {
    }

    public MapMapHandler(int keyColunnIndex){
        super(keyColunnIndex,null);
    }
    public MapMapHandler(String keyColumnName) {
        super(1, keyColumnName);
    }

    @Override
    protected Map<String, Object> createValue(ResultSet rs) throws SQLException {
        return DEFAULT_ROWPROCESSOR.toMap(rs);
    }
}
