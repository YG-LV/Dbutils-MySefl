package com.nf.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * 工具类
 * 此类是一个居于工具性质的类
 * 工具类里面基本都是静态方法，而工具类通常不会有子类
 * 所以工具类一般提交一个final修饰符，防止它有子类
 * final类不能被继承，不能被覆盖，以及final类在执行速度方面比一般类快
 * 工具类一般不会实例化，所以其构造函数应该不允许使用者访问
 * 构造函数一般设置为私有
 *
 */
public final class CleanerUtils {

    /**
     * 私用构造函数
     * 不允许访问
     */
    private CleanerUtils(){}

    /**
     * 关闭数据库的结果集对象
     * 出现异常抛出数据库访问异常
     * @param rs 结果集对象
     * @throws SQLException 数据库访问异常
     */
    public static void close(ResultSet rs) throws SQLException {
        if (rs!=null){
            rs.close();
        }
    }

    /**
     * 关闭数据库的数据库语句预编译对象
     * 出现异常抛出数据库访问异常
     * @param statm 数据库语句预编译对象
     * @throws SQLException 数据库访问异常
     */
    public static void close(PreparedStatement statm) throws SQLException {
        if (statm!=null){
            statm.close();
        }
    }

    /**
     * 关闭数据库的连接对象
     * 出现异常抛出数据库访问异常
     * @param con 数据库连接对象
     * @throws SQLException 数据库访问异常
     */
    public static void close(Connection con) throws SQLException {
        if(con!=null){
            con.close();
        }
    }

    /**
     * 安静关闭数据库的结果集对象
     * 出现异常捕获后无操作
     * @param rs 结果集对象
     */
    public static void closeQuietly(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException e) {
            //quiet
        }
    }

    /**
     * 安静关闭数据库的数据库语句预编译对象
     * 出现异常捕获后无操作
     * @param statm 数据库语句预编译对象
     */
    public static void closeQuietly(PreparedStatement statm){
        try {
            statm.close();
        } catch (SQLException e) {
            //quiet
        }
    }

    /**
     * 安静关闭数据库的连接对象
     * 出现异常捕获后无操作
     * @param con 数据库连接对象
     */
    public static void closeQuietly(Connection con){
        try {
            con.close();
        } catch (SQLException e) {
            //quiet
        }
    }

    /**
     * 安静关闭数据库的：数据库语句预编译对象、数据库连接对象
     * 通常用于：增、删、改操作当中
     * @param statm 数据库语句预编译对象
     * @param con 数据库连接对象
     */
    public static void closeQuietly(PreparedStatement statm,Connection con){
        try {
            closeQuietly(statm);
        }finally {
            closeQuietly(con);
        }
    }

    /**
     * 安静关闭数据库的：结果集对象、数据库语句预编译对象、数据库连接对象
     * 通常用于查询操作当中
     * @param rs 结果集对象
     * @param statm 数据库语句预编译对象
     * @param con 数据库连接对象
     */
    public static void closeQuietly(ResultSet rs,PreparedStatement statm,Connection con){
        try {
            closeQuietly(rs);
        }finally {
            try {
                closeQuietly(statm);
            }finally {
                closeQuietly(con);
            }
        }
    }

}
