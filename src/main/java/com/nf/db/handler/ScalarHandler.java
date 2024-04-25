package com.nf.db.handler;

import com.nf.db.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ScalarHandler类用于获取数据库一行一列的数据
 * @param <T> 泛型(返回类型)
 */
public class ScalarHandler <T> implements ResultSetHandler <T>{

    /**
     * 列索引
     */
    private final int COLUMNINDEX;

    /**
     * 列名
     */
    private final String COLUMNNAME;

    /**
     * 构造函数(无参)
     * 创建默认类
     */
    public ScalarHandler() {
        this(1,null);
    }

    /**
     * 构造函数
     * 给列索引(常量)赋值
     * @param COLUMNINDEX 列索引(常量)
     */
    public ScalarHandler(int COLUMNINDEX) {
        this(COLUMNINDEX,null);
    }

    /**
     * 构造函数
     * 给列名(常量)赋值
     * @param COLUMNNAME 列名(常量)
     */
    public ScalarHandler(String COLUMNNAME) {
        this(1, COLUMNNAME);
    }

    /**
     * 私有构造函数
     * 给当前类的常量赋值
     * @param COLUMNINDEX 列索引(常量)
     * @param COLUMNNAME 列名(常量)
     */
    private ScalarHandler(int COLUMNINDEX, String COLUMNNAME) {
        this.COLUMNINDEX = COLUMNINDEX;
        this.COLUMNNAME = COLUMNNAME;
    }


    /**
     * ScalarHandler的方法读取数据库一行一列数据
     * 根据用户给予的列索引或列名读取结果（默认为第1列）
     * 数据库查询结果读取后强制转换为泛型结果并返回
     * 异常则抛出数据库访问异常
     * @param rs 结果集对象
     * @return 读取数据库查询结果
     * @throws SQLException 数据库访问异常
     */
    @Override
    public T handler(ResultSet rs) throws SQLException {
        //是否从数据库中读取到一行结果，未读取到否则返回空值
        if(!rs.next()) return null;

        //判断列名是否为空
        //列名不为空则：返回使用列索引读取的一行一列数据
        if (this.COLUMNNAME ==null){
            return (T) rs.getObject(COLUMNINDEX);
        }

        //以上都不成立则：返回使用列名读取的一行一列数据（默认）
        return (T) rs.getObject(COLUMNNAME);
    }


//    //重写：结果与上一致
//    public T handler(ResultSet rs) throws SQLException{
//        //使用if嵌套格式
//        //是否从数据库中读取到一行结果，读取到则进入
//        if (rs.next()){
//            //判断列名是否为空
//            //列名为空则：返回使用列索引读取的一行一列数据
//            if (this.columnName==null){
//                return (T) rs.getObject(COLUMNINDEX);
//            }
//            //读取到一行结果则：返回使用列名读取的一行一列数据（默认）
//            return (T) rs.getObject(COLUMNNAME);
//        }
//        //以上都不成立则：返回空值（默认）
//        return null;
//    }


//    //重写：结果与上一致
//    public T handler(ResultSet rs) throws SQLException{
//        //使用三元运算符
//        //是否从数据库中读取到一行结果，未读取到否则返回空值
//        if(!rs.next()) return null;
//
//        //三元运算符，判断用户是否给予列名
//        //列名不为空则：返回使用列名读取的一行一列数据
//        //列名为空则：返回使用列索引读取的一行一列数据
//        //强制转换为泛型类的结果并返回
//        return (T)(COLUMNNAME!=null?rs.getObject(COLUMNNAME):rs.getObject(COLUMNINDEX));
//    }
}
