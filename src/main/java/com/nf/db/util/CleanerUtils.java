package com.nf.db.util;

import java.sql.Connection;
import java.sql.SQLException;

public class CleanerUtils {
    public static void close(Connection con) throws SQLException {
        if(con!=null){
            con.close();
        }
    }

    public static void closeQuietly(Connection con){
        try {
            con.close();
        } catch (SQLException e) {

        }
    }
}
