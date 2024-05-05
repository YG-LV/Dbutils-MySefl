package com.nf.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface RowProcessor {
    Object[] toArray(ResultSet rs) throws SQLException;
    Map<String,Object> toMap(ResultSet rs) throws SQLException;
    <T> T toBean(ResultSet rs,Class<? extends T> beanCls) throws SQLException;
    <T> T toScalar(ResultSet rs,String columnName,int columnIndex) throws SQLException;
}
