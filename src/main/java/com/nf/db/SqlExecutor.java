package com.nf.db;

import com.nf.db.handler.MapHandler;
import com.nf.db.handler.list.ArrayListHandler;
import com.nf.db.util.CleanerUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        this(null);
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
        Connection connection = getConnection();
        return update(connection,sql,params);
    }

    /**
     * 数据库操作（增、删、改）方法
     * 执行语句后返回执行影响的行数
     * @param connection 连接数据库对象
     * @param sql 数据库SQL可执行语句
     * @param params 参数
     * @return 执行影响行数
     */
    public int update(Connection connection,String sql,Object... params){

        int rows = 0;//返回参数：执行影响行数默认为0

        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(sql);

            fillStatement(stmt, params);

            rows = stmt.executeUpdate();
        }catch (SQLException e){
            throw new DaoException("数据库操作失败！",e);
        }finally {
            CleanerUtils.closeQuietly(stmt,connection);
        }

        return rows;
    }

    /**
     * 数据库查询方法
     * 执行语句后返回结果集，根据泛型处理并返回该类型的结果
     * 默认返回数组集合
     * @param sql 数据库SQL可执行语句
     * @param params 参数
     * @return 泛型结果
     */
    public List<Object[]> query(String sql, Object... params){
        ArrayListHandler handler = new ArrayListHandler();
        return query(this.getConnection(),sql,handler,params);
    }

    /**
     * 数据库查询方法
     * 执行语句后返回结果集，根据泛型处理并返回该类型的结果
     * @param sql 数据库SQL可执行语句
     * @param handler 结果集处理类
     * @param params 参数
     * @return 泛型结果
     * @param <T> 泛型
     */
    public <T> T query(String sql,ResultSetHandler<T> handler,Object... params){
       return query(this.getConnection(),sql,handler,params);
    }

    /**
     * 数据库查询方法
     * 执行语句后返回结果集，根据泛型处理并返回该类型的结果
     * @param connection 数据库连接对象
     * @param sql 数据库SQL可执行语句
     * @param handler 结果集处理类
     * @param params 参数
     * @return 泛型结果
     * @param <T> 泛型
     */
    public <T> T query(Connection connection,String sql,ResultSetHandler<T> handler,Object... params){
        T result = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            fillStatement(stmt,params);
            resultSet = stmt.executeQuery();
            result = handler.handler(resultSet);
        } catch (SQLException e) {
            throw new DaoException("数据库查询失败！",e);
        } finally {
            CleanerUtils.closeQuietly(resultSet,stmt,connection);
        }

        return result;
    }

    /**
     * 数据库添加方法
     * 执行数据库添加语句后
     * 查询添加后的自增长列的值
     * 根据泛型处理并返回该类型的结果
     * 默认数据库连接
     * 默认查询结果处理
     * @param sql 数据库SQL可执行语句
     * @param params 参数
     * @return 返回Map类型
     */
    public Map<String,Object> insert(String sql,Object... params){
        MapHandler handler = new MapHandler();
        return insert(this.getConnection(),sql,handler,params);
    }

    /**
     * 数据库添加方法
     * 执行数据库添加语句后
     * 查询添加后的自增长列的值
     * 根据泛型处理并返回该类型的结果
     * 默认数据库连接
     * @param sql 数据库SQL可执行语句
     * @param handler 结果集处理类
     * @param params 参数
     * @return 泛型结果
     * @param <T> 泛型，返回类型
     */
    public <T> T insert(String sql,ResultSetHandler<T> handler,Object... params){
        return insert(this.getConnection(),sql,handler,params);
    }

    /**
     * 数据库添加方法
     * 执行数据库添加语句后
     * 查询添加后的自增长列的值
     * 根据泛型处理并返回该类型的结果
     * @param connection 数据库连接对象
     * @param sql 数据库SQL可执行语句
     * @param handler 结果集处理类
     * @param params 参数
     * @return 泛型结果
     * @param <T> 泛型，返回类型
     */
    public <T> T insert(Connection connection,String sql,ResultSetHandler<T> handler,Object... params){
        T result = null;

        PreparedStatement statement = null;
        try {
            //获取自增长关键处理代码
            //Statement.RETURN_GENERATED_KEYS：jdbc自带的常量值
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            fillStatement(statement,params);
            statement.executeUpdate();

            //获取自增长关键处理代码
            try (ResultSet resultSet =  statement.getGeneratedKeys()){
                result = handler.handler(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CleanerUtils.closeQuietly(statement,connection);
        }

        return result;
    }


    /**
     * 获取连接池对象
     * @return 连接池对象
     */
    protected DataSource getDataSource(){
        return dataSource;
    }

    /**
     * 获取连接池的数据库连接对象参数
     * @return 返回数据库连接对象
     */
    protected Connection getConnection(){
        Connection connection = null;
        try {
            connection = getDataSource().getConnection();
        } catch (SQLException e) {
            throw new DaoException("从DataSource获取连接失败！",e);
        }
        return connection;
    }

    /**
     * 将参数安全代入数据库语句预编译对象中
     * 异常则抛出数据库访问异常
     *
     * @param stmt   数据库语句预编译对象
     * @param patams 需要安全代入的参数
     * @throws SQLException 数据库访问异常
     */
    private void fillStatement(PreparedStatement stmt, Object... patams) throws SQLException {
        for (int i = 0; i < patams.length; i++) {
            //设置参数时，总是从1开始的，所以参数位置需要加1
            stmt.setObject(i+1,patams[i]);
        }
    }
}
