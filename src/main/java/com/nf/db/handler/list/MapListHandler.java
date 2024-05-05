package com.nf.db.handler.list;

import com.nf.db.handler.abstractset.AbstractListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MapListHandler extends AbstractListHandler<Map<String,Object>> {
    @Override
    public Map<String, Object> rowHandler(ResultSet rs) throws SQLException {
        return DEFAULT_ROWPROCESSOR.toMap(rs);
    }
}
