package com.nf.db.handler;

import com.nf.db.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ArrayHandler类用于获取数据库一行数据
 */
public class ArrayHandler implements ResultSetHandler<Object[]> {

    /**
     * 静态的常量的无产数的数组
     * 当查询得不到结果时返回一个无参数组
     * 避免返回null，造成空引用异常
     * 静态字段只初始化一次，提高性能
     * 常量不会被改变，无需更改
     * 常量字段的命名通常采用全大写字母，并且单词之间采用下划线'_'分隔
     */
    private static final Object[] EMPTY_ARRAY = new Object[0];

    /**
     * ArrayHandler的方法读取数据库一行数据
     * 将查询到结果集读取储存到数组中并返回
     * 异常则抛出数据库访问异常
     * @param rs 结果集
     * @return 查询结果得到的数组对象
     * @throws SQLException 数据库访问异常
     */
    @Override
    public Object[] handler(ResultSet rs) throws SQLException {
        //是否从数据库读取到一行结果,未读取到则返回空的数组常量
        if (!rs.next()) return EMPTY_ARRAY;

        //获取结果集的源数据内的列数
        int columnCount = rs.getMetaData().getColumnCount();

        //创建储存结果的数组，长度为获取的列数
        Object[] result = new Object[columnCount];

        //循环遍历
        //读取结果集内的参数并储存到数组中
        for (int i = 0; i < columnCount; i++) {
            result[i] = rs.getObject(i+1);
        }

        //返回查询结果
        return result;
    }
}
