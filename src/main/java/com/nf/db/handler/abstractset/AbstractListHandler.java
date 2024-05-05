package com.nf.db.handler.abstractset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListHandler<T> extends AbstractResultSetHandler<List<T>>{
    @Override
    public List<T> handler(ResultSet rs) throws SQLException {
        List<T> result = new ArrayList<>();
        while (rs.next()){
            T row = this.rowHandler(rs);
            result.add(row);
        }
        return result;
    }

    public abstract T rowHandler(ResultSet rs) throws SQLException;
}
