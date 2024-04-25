package com.nf.db.handler;

import com.nf.db.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MapHandler implements ResultSetHandler<Map<String,Object>> {

    @Override
    public Map<String, Object> handler(ResultSet rs) throws SQLException {

        if(!rs.next()) return null;

        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();

        Map<String,Object> result = HashMap.newHashMap(columnCount);

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            if (columnName==null || 0==columnName.length()){
                columnName = metaData.getColumnLabel(i);
            }
            result.put(columnName,rs.getObject(i));
        }

        return result;
    }
}
