package com.nf.db.handler;

import com.nf.db.ResultSetHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * BeanHandler用于用户查询数据库一行的参数并按用户需求返回用户代入的泛型类对象
 * @param <T> 泛型类（返回类型）
 */
public class BeanHandler <T> implements ResultSetHandler <T> {
    /**
     * Class对象
     */
    private Class<? extends T> beanClass;

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
        T result = this.newInstance();

        fillBean(rs, result);

        return result;
    }

    /**
     * 根据反射实例化泛型对象
     * @return 泛型对象
     */
    private T newInstance(){
        //创建泛型类对象
        //根据传给的泛型类创建类
        T t = null;
        try {
            //反射实例化
            //调用反射对象空的默认构造函数
            t = beanClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            //这个catch是jdk新特性，等价于分别写两个catch
            throw new RuntimeException(e);
        }
        return t;
    }

    /**
     * 将查询的结果集读取并赋值给泛型对象
     * 未读取到结果则无操作直接返回
     * 异常则抛则抛出数据库访问异常
     * @param rs 结果集对象
     * @param result 泛型对象
     * @throws SQLException 数据库访问异常
     */
    private void fillBean(ResultSet rs, T result) throws SQLException {
        if (!rs.next()) return;

        //获取结果集对象的元数据
        ResultSetMetaData metaData = rs.getMetaData();

        //获取元数据的总列数
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            //根据列索引获取元数据的列名
            String columnName = metaData.getColumnName(i);
            //根据列索引获取结果集的数据
            Object value = rs.getObject(i);
            //查询字段并给字段写入值
            setFieldValue(columnName, result, value);
        }
    }

    /**
     * 根据反射查询字段并赋值
     * @param columnName 字段名
     * @param result 赋值对象
     * @param value 需要赋的值
     */
    private void setFieldValue(String columnName, T result, Object value){
        try {
            //根据反射以字段名查询字段
            Field declaredField = beanClass.getDeclaredField(columnName);
            //更改访问权限public
            declaredField.setAccessible(true);
            //写入值
            declaredField.set(result, value);
            //更改访问权限private
            declaredField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            //System.out.println("找不到字段，无事发生，不需要处理正常跳过");
            //这里没用'|'方法分隔Exception因为处理方式不同
            //所以不使用'|'
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
