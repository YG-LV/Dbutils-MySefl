package com.nf.db.handler;

import com.nf.db.RowProcessor;
import com.nf.db.handler.abstractset.AbstractResultSetHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MapHandler extends AbstractResultSetHandler<Map<String,Object>> {

    @Override
    public Map<String, Object> handler(ResultSet rs) throws SQLException {

        if(!rs.next()) return null;

        Map<String, Object> result = DEFAULT_ROWPROCESSOR.toMap(rs);

        return result;
    }
}
