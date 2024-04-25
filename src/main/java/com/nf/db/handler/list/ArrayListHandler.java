package com.nf.db.handler.list;

import com.nf.db.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArrayListHandler implements ResultSetHandler <List<Object[]>> {
    @Override
    public List<Object[]> handler(ResultSet rs) throws SQLException {
        List<Object[]> result = new ArrayList<>();

        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()){
            Object[] objs = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                objs[i] = rs.getObject(i+1);
            }
            result.add(objs);
        }

        return result;
    }
}
