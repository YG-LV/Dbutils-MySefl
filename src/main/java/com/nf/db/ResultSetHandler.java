package com.nf.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler <T>{
    T handler(ResultSet rs) throws SQLException;
}
