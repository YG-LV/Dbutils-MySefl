package com.nf.db.handler.list;

import com.nf.db.ResultSetHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanListHandler <T> implements ResultSetHandler <List<T>> {

    private Class<? extends T> beanClass;

    public BeanListHandler(Class<? extends T> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public List<T> handler(ResultSet rs) throws SQLException {
        List<T> result = new ArrayList<>();

        while (rs.next()){
            T t = this.newInstance();
            this.fillBean(rs,t);
            result.add(t);
        }

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
