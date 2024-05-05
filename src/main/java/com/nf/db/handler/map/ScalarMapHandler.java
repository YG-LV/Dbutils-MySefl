package com.nf.db.handler.map;

import com.nf.db.handler.abstractset.AbstractMapHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScalarMapHandler<K,V> extends AbstractMapHandler<K,V> {
    private int valueColumnIndex = 1;
    private String valueColumnName = null;

    public ScalarMapHandler(int valueColumnIndex) {
        this(1,valueColumnIndex);
    }

    public ScalarMapHandler(int keyColumnIndex, int valueColumnIndex) {
        this(keyColumnIndex,null,valueColumnIndex,null);
    }

    public ScalarMapHandler(String keyColumnName, String valueColumnName) {
        this(1,keyColumnName,1,valueColumnName);
    }

    private ScalarMapHandler(int keyComlumnIndex,String keyColumnName,int valueComlumnIndex,String valueColumnName){
        super(keyComlumnIndex,keyColumnName);
        this.valueColumnIndex = valueComlumnIndex;
        this.valueColumnName = valueColumnName;
    }

    @Override
    protected V createValue(ResultSet rs) throws SQLException {
        return DEFAULT_ROWPROCESSOR.toScalar(rs,valueColumnName,valueColumnIndex);
    }

}
