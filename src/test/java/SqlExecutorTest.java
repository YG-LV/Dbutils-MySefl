import com.nf.db.SqlExecutor;
import com.nf.db.handler.ArrayHandler;
import com.nf.db.handler.BeanHandler;
import com.nf.db.handler.MapHandler;
import com.nf.db.handler.ScalarHandler;
import com.nf.db.handler.list.ArrayListHandler;
import com.nf.db.handler.list.BeanListHandler;
import com.nf.db.handler.list.ColumnListHandler;
import com.nf.db.handler.list.MapListHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlExecutorTest {
    private static SqlExecutor sqlExecutor = new SqlExecutor();
    @Test
    public void testScalaHandler() throws SQLException {
        ScalarHandler handler = new ScalarHandler<>();
        String sql = "select * from teacher limit 1;";
        Object query = sqlExecutor.query(getConnecton(), sql, handler);
        System.out.println("query = " + query);
        System.out.println("query.getClass() = " + query.getClass());
    }

    @Test
    public void testArrayHandler() throws SQLException {
        ArrayHandler handler = new ArrayHandler();
        String sql = "select * from teacher limit 1;";
        Object[] query = sqlExecutor.query(getConnecton(), sql, handler);
        for (Object object : query) {
            System.out.println("object = " + object);
        }
    }

    @Test
    public void testMapHandler() throws SQLException {
        MapHandler handler = new MapHandler();
        String sql = "select * from teacher limit 1;";
        Map<String, Object> query = sqlExecutor.query(getConnecton(), sql, handler);
        for (Map.Entry<String, Object> stringObjectEntry : query.entrySet()) {
            System.out.println("stringObjectEntry.getKey() = " + stringObjectEntry.getKey());
            System.out.println("stringObjectEntry.getValue() = " + stringObjectEntry.getValue());
        }
    }

    @Test
    public void testBeanHandler() throws SQLException {
        BeanHandler<Teacher> handler = new BeanHandler<>(Teacher.class);
        String sql = "select * from teacher where id = 1 limit 1;";

        Teacher query = sqlExecutor.query(getConnecton(), sql, handler);
        System.out.println("query = " + query.toString());
    }

    @Test
    public void testArrayListHandler() throws SQLException {
        ArrayListHandler handler = new ArrayListHandler();
        String sql = "select * from teacher;";
        List<Object[]> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach(objects->{
           StringBuilder stringBuilder = new StringBuilder("");
            for (Object object : objects) {
                stringBuilder.append(object+",");
            }
            stringBuilder.setLength(stringBuilder.length()-1);
            System.out.println("stringBuilder.toString() = " + stringBuilder.toString());
        });
    }

    @Test
    public void testBeanListHandler() throws SQLException {
        BeanListHandler<Teacher> handler = new BeanListHandler<>(Teacher.class);
        String sql = "select * from teacher;";
        List<Teacher> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach(System.out::println);
    }

    @Test
    public void testColumnListHandler() throws SQLException {
        ColumnListHandler<Object> handler = new ColumnListHandler<>(2);
        String sql = "select * from teacher;";
        List<Object> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach(System.out::println);
    }

    @Test
    public void testMapListHandler() throws SQLException {
        MapListHandler handler = new MapListHandler();
        String sql = "select * from teacher;";
        List<Map<String,Object>> query = sqlExecutor.query(getConnecton(), sql, handler);
        query.forEach(System.out::println);
    }

    private Connection getConnecton() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/thdb";
        String username = "root";
        String password = "123";
        return DriverManager.getConnection(url,username,password);
    }
}
