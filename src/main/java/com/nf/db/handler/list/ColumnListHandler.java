package com.nf.db.handler.list;

import com.nf.db.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColumnListHandler <T> implements ResultSetHandler<List<T>> {
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
    public List<T> handler(ResultSet rs) throws SQLException {
        List<T> result = new ArrayList<>();

        while (rs.next()){
            T ts = (T) (COLUMNNAME==null?rs.getObject(COLUMNINDEX):rs.getObject(COLUMNNAME));
            result.add(ts);
        }

        return result;
    }
}
