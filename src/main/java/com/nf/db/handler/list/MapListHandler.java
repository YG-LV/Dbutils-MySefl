package com.nf.db.handler.list;

import com.nf.db.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapListHandler implements ResultSetHandler<List<Map<String,Object>>> {
    @Override
    public List<Map<String, Object>> handler(ResultSet rs) throws SQLException {
        List<Map<String,Object>> result = new ArrayList<>();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()){
            Map<String,Object> map = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                if (columnName==null || 0==columnName.length()){
                    columnName = metaData.getColumnName(i);
                }
                map.put(columnName,rs.getObject(i));
            }
            result.add(map);
        }

        return result;
    }
}
