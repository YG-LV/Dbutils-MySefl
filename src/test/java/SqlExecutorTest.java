import com.mysql.cj.result.Row;
import com.nf.db.RowProcessor;
import com.nf.db.SqlExecutor;
import com.nf.db.handler.ArrayHandler;
import com.nf.db.handler.BeanHandler;
import com.nf.db.handler.MapHandler;
import com.nf.db.handler.ScalarHandler;
import com.nf.db.handler.abstractset.AbstractMapHandler;
import com.nf.db.handler.list.ArrayListHandler;
import com.nf.db.handler.list.BeanListHandler;
import com.nf.db.handler.list.ColumnListHandler;
import com.nf.db.handler.list.MapListHandler;
import com.nf.db.handler.map.ArrayMapHandler;
import com.nf.db.handler.map.BeanMapHandler;
import com.nf.db.handler.map.MapMapHandler;
import com.nf.db.handler.map.ScalarMapHandler;
import org.junit.Test;

import java.sql.*;
import java.util.*;

public class SqlExecutorTest {
    private static SqlExecutor sqlExecutor = new SqlExecutor();
    @Test
    public void testSPI(){

    }
    @Test
    public void testRowHandler() throws SQLException {
        String sql = "select * from teacher limit 1;";

//        testScalarHandler(sql);
//
//        testMapHandler(sql);

        testBeanHandler(sql);

//        testArrayHandler(sql);
    }

    @Test
    public void testListHandler() throws SQLException {
        String sql = "select * from teacher;";

        testBeanListHandler(sql);

        testColumnListHandler(sql);

        testMapListHandler(sql);

        testArrayListHandler(sql);
    }

    @Test
    public void testMapHandler() throws SQLException {
        String sql = "select * from teacher;";
        //testScalarMapHandler(sql);
        testBeanMapHandler(sql);
    }
    public void testBeanMapHandler(String sql) throws SQLException{
        BeanMapHandler<Object,Teacher> handler = new BeanMapHandler<>(Teacher.class);
        Map<Object, Teacher> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach((k,v)->{
            System.out.println("k = " + k);
            System.out.println("k.getClass() = " + k.getClass());
            System.out.println("v.toString() = " + v.toString());
        });
    }
    private void testArrayMapHandler(String sql) throws SQLException {
        ArrayMapHandler<Object> handler = new ArrayMapHandler(1);
        Map<Object, Object[]> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach((k,v)->{
            System.out.println("k = " + k);
            System.out.println("k.getClass() = " + k.getClass());
            soutvArray(v);
        });
    }

    private void testScalarMapHandler(String sql) throws SQLException{
        ScalarMapHandler<Object,Object> handler = new ScalarMapHandler(3,3);
        Map<Object, Object> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach((k,v)->{
            String exports =
                    String.format("key.Class = %s , key.toString = %s \n" +
                                    "\t\t value.Class = %s , value.toString  = %s \n"
                            ,k.getClass(),k.toString(),v.getClass(),v.toString());
            System.out.println("exports = " + exports);
        });
    }

    private Connection getConnecton() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/thdb";
        String username = "root";
        String password = "123";
        return DriverManager.getConnection(url,username,password);
    }

    private static void soutvArray(Object[] obj) {
        for (Object object : obj) {
            System.out.println("object = " + object);
        }
    }

    private static void soutvMap(Map<String, Object> maps) {
        for (Map.Entry<String, Object> stringObjectEntry : maps.entrySet()) {
            System.out.println("stringObjectEntry.getKey() = " + stringObjectEntry.getKey());
            System.out.println("stringObjectEntry.getValue() = " + stringObjectEntry.getValue());
        }
    }

    private void testMapHandler(String sql) throws SQLException {
        MapHandler handler = new MapHandler();
        Map<String, Object> query = sqlExecutor.query(getConnecton(), sql, handler);
        System.out.println("query.toString() = " + query.toString());
        System.out.println("query.toString() = " + query.getClass());
    }

    private void testArrayHandler(String sql) throws SQLException {
        ArrayHandler handler = new ArrayHandler();
        Object[] query = sqlExecutor.query(getConnecton(), sql, handler);
        soutvArray(query);
    }

    private void testBeanHandler(String sql) throws SQLException {
        BeanHandler<Teacher> handler = new BeanHandler<>(Teacher.class);
        Teacher query = sqlExecutor.query(getConnecton(), sql, handler);
        System.out.println("query.toString() = " + query.toString());
        System.out.println("query.toString() = " + query.getClass());
    }

    private void testScalarHandler(String sql) throws SQLException {
        ScalarHandler handler = new ScalarHandler<>();
        Object query = sqlExecutor.query(getConnecton(), sql, handler);
        System.out.println("query.toString() = " + query.toString());
        System.out.println("query.toString() = " + query.getClass());
    }

    private void testArrayListHandler(String sql) throws SQLException {
        ArrayListHandler handler = new ArrayListHandler();
        List<Object[]> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach(SqlExecutorTest::soutvArray);
    }

    private void testMapListHandler(String sql) throws SQLException {
        MapListHandler handler = new MapListHandler();
        List<Map<String,Object>> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach(System.out::println);
    }

    private void testColumnListHandler(String sql) throws SQLException {
        ColumnListHandler<Object> handler = new ColumnListHandler<>(2);
        List<Object> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach(System.out::println);
    }

    private void testBeanListHandler(String sql) throws SQLException {
        BeanListHandler<Teacher> handler = new BeanListHandler<>(Teacher.class);
        List<Teacher> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach(System.out::println);
    }
}
