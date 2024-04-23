package com.nf.db;

import com.nf.db.util.CleanerUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 此类是用来执行sql的增删查改（crud）
 */
public class SqlExecutor {

    /**
     * 数据库连接池对象
     */
    private DataSource dataSource;

    /**
     * 构造函数（无参）
     */
    public SqlExecutor() {
    }

    /**
     * 构造函数
     * 实例化连接池对象
     * @param dataSource 连接池对象
     */
    public SqlExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 数据库操作（增、删、改）方法
     * 执行语句后返回执行影响的行数
     * @param sql 数据库SQL可执行语句
     * @param params 参数
     * @return 执行影响行数
     */
    public int update(String sql,Object... params){
        Connection connection = getConnection(dataSource);
        return uprdate(connection,sql,params);
    }

    /**
     * 数据库操作（增、删、改）方法
     * 执行语句后返回执行影响的行数
     * @param connection 连接数据库对象
     * @param sql 数据库SQL可执行语句
     * @param params 参数
     * @return 执行影响行数
     */
    public int uprdate(Connection connection,String sql,Object... params){

        int rows = 0;//返回参数：执行影响行数默认为0

        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(sql);

            fillStatement(params,stmt);

            rows = stmt.executeUpdate();
        }catch (SQLException e){
            throw new DaoException("数据库操作失败！",e);
        }finally {
            CleanerUtils.closeQuietly(stmt,connection);
        }

        return rows;
    }

    /**
     * 获取连接池的数据库连接对象参数
     * @return 返回数据库连接对象
     */
    private Connection getConnection(DataSource ds){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将参数安全代入数据库语句预编译对象中
     * 异常则抛出数据库访问异常
     * @param patams 需要安全代入的参数
     * @param stmt 数据库语句预编译对象
     * @throws SQLException 数据库访问异常
     */
    private void fillStatement(Object[] patams,PreparedStatement stmt) throws SQLException {
        for (int i = 0; i < patams.length; i++) {
            //设置参数时，总是从1开始的，所以参数位置需要加1
            stmt.setObject(i+1,patams[i]);
        }
    }
}
