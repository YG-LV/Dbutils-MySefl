package com.nf.db;

import com.nf.db.util.CleanerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 此类是用来执行sql的增删查改（crud）
 */
public class SqlExecutor {

    /**
     * 数据库操作语句
     * @param conn 连接数据库对象
     * @param sql 数据库SQL可执行语句
     * @param params 参数
     * @return 执行影响行数
     * @throws SQLException
     */
    public int uprdate(Connection conn,String sql,Object... params) throws SQLException {

        int rows = 0;//返回参数：执行影响行数默认为0

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            rows = stmt.executeUpdate();
        }catch (SQLException e){
            throw new DaoException("数据库操作失败！",e);
        }finally {
            stmt.close();
            //以上出现异常，连接是不会关闭的
            CleanerUtils.closeQuietly(conn);
        }

        return rows;
    }
}
