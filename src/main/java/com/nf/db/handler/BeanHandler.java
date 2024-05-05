package com.nf.db.handler;

import com.nf.db.RowProcessor;
import com.nf.db.handler.abstractset.AbstractResultSetHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * BeanHandler用于用户查询数据库一行的参数并按用户需求返回用户代入的泛型类对象
 * @param <T> 泛型类（返回类型）
 */
public class BeanHandler<T> extends AbstractResultSetHandler<T> {

    public final Class<? extends T> beanClass;

    /**
     * 获取class对象
     * 使用BeanHandler时由于必须要代入参数所以没有默认构造函数
     * @param beanClass clss对象
     */
    public BeanHandler(Class<? extends T> beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * BeanHanadler的方法读取数据库一行的数据并返回用户代入的参数类型
     * 使用反射实例化用户代入的类型（泛型类）
     * 从读取的结果集中获取列名通过列名查询反射类型中字段
     * 读取结果集的参数并写入反射类型的字段中
     * 返回用户代入类型（泛型类）
     * 异常则抛出数据库访问异常
     * @param rs 结果集对象
     * @return 用户代入类型（泛型类）
     * @throws SQLException 数据库访问异常
     */
    @Override
    public T handler(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;

        T result = DEFAULT_ROWPROCESSOR.toBean(rs,beanClass);

        return result;
    }
}
