package com.nf.db;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class DefaultRowProcessor implements RowProcessor{
    private ServiceLoader<PropertyHandler> propertyHandlers = ServiceLoader.load(PropertyHandler.class);
    @Override
    public Object[] toArray(ResultSet rs) throws SQLException {
        //获取结果集的源数据内的列数
        int columnCount = rs.getMetaData().getColumnCount();
        //创建储存结果的数组，长度为获取的列数
        Object[] objs = new Object[columnCount];
        //循环遍历
        //读取结果集内的参数并储存到数组中
        for (int i = 0; i < columnCount; i++) {
            objs[i] = rs.getObject(i+1);
        }
        return objs;
    }

    @Override
    public Map<String, Object> toMap(ResultSet rs) throws SQLException {
        //创建Map类对象
        Map<String,Object> map = new HashMap<>();

        //获取查询的数据库的总列数
        int columnCount = rs.getMetaData().getColumnCount();

        //循环遍历列数
        for (int i = 1; i <= columnCount; i++) {
            //获取数据库
            String columnName = getColumnName(rs, i);
            //将查询的表的列名存入Map对象中，key=列名，value=查询结果
            map.put(columnName, rs.getObject(i));
        }
        return map;
    }

    @Override
    public <T> T toBean(ResultSet rs,Class<? extends T> beanCls) throws SQLException {
        T result = this.newInstanceT(beanCls);
        //获取结果集对象的元数据
        ResultSetMetaData metaData = rs.getMetaData();

        //获取元数据的总列数
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = getColumnName(rs,i);
            //根据列索引获取结果集的数据
            Object value = rs.getObject(i);
            //查询字段并给字段写入值
            setFieldValue(columnName, result, value);
        }
        return result;
    }

    @Override
    public <T> T toScalar(ResultSet rs,String columnName,int columnIndex) throws SQLException {
        //三元运算符，判断用户是否给予列名
        //列名不为空则：返回使用列名读取的一行一列数据
        //列名为空则：返回使用列索引读取的一行一列数据
        //强制转换为泛型类的结果并返回
        return (T)(columnName==null?rs.getObject(columnIndex):rs.getObject(columnName));
    }

    /**
     * 根据列索引获取结果集的元数据的列名
     * @param rs 结果集
     * @param i 索引
     * @return 数据的列名
     * @throws SQLException 数据库访问异常
     */
    private static String getColumnName(ResultSet rs, int i) throws SQLException {
        String columnName = rs.getMetaData().getColumnLabel(i);
        if (columnName==null || 0==columnName.length()){
            columnName = rs.getMetaData().getColumnName(i);
        }
        return columnName;
    }

    /**
     * 根据反射实例化泛型对象
     * @return 泛型对象
     */
    private static <T> T newInstanceT(Class<? extends T> beanCls) {
        T result;
        try {
            result = beanCls.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * 根据反射查询字段并赋值
     * @param columnName 字段名
     * @param result 赋值对象
     * @param value 需要赋的值
     */
    protected <T> void setFieldValue(String columnName, T result, Object value){
        try {
            //根据反射以字段名查询字段
            Field declaredField = result.getClass().getDeclaredField(columnName);
            //更改访问权限public
            declaredField.setAccessible(true);
            //写入值
            for (PropertyHandler propertyHandler : propertyHandlers) {
                //循环遍历，转换安全类型
                if (propertyHandler.match(declaredField.getClass(),value)) {
                    value = propertyHandler.apply(declaredField.getClass(), value);
                }
            }

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
