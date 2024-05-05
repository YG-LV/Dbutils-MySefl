package com.nf.db.handler.list;

import com.nf.db.handler.abstractset.AbstractListHandler;
import com.nf.db.handler.abstractset.AbstractResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArrayListHandler extends AbstractListHandler<Object[]> {
    @Override
    public Object[] rowHandler(ResultSet rs) throws SQLException {
        return DEFAULT_ROWPROCESSOR.toArray(rs);
    }
}
