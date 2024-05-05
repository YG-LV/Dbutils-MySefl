package com.nf.db.handler.list;

import com.nf.db.handler.abstractset.AbstractListHandler;
import com.nf.db.handler.abstractset.AbstractResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColumnListHandler<T> extends AbstractListHandler<T> {
    private final int COLUMNINDEX;
    private final String COLUMNNAME;

    public ColumnListHandler() {
        this(1,null);
    }

    public ColumnListHandler(int COLUMNINDEX) {
        this(COLUMNINDEX,null);
    }

    public ColumnListHandler(String COLUMNNAME) {
        this(1,COLUMNNAME);
    }

    private ColumnListHandler(int COLUMNINDEX, String COLUMNNAME) {
        this.COLUMNINDEX = COLUMNINDEX;
        this.COLUMNNAME = COLUMNNAME;
    }

    @Override
    public T rowHandler(ResultSet rs) throws SQLException {
        return DEFAULT_ROWPROCESSOR.toScalar(rs,COLUMNNAME,COLUMNINDEX);
    }
}
