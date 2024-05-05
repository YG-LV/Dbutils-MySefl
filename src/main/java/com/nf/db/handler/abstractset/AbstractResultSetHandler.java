package com.nf.db.handler.abstractset;

import com.nf.db.DefaultRowProcessor;
import com.nf.db.ResultSetHandler;
import com.nf.db.RowProcessor;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractResultSetHandler<T>
        implements ResultSetHandler <T> {
    public final RowProcessor DEFAULT_ROWPROCESSOR;

    public AbstractResultSetHandler() {
        this(new DefaultRowProcessor());
    }

    public AbstractResultSetHandler(RowProcessor rowProcessor) {
        this.DEFAULT_ROWPROCESSOR = rowProcessor;
    }

}
