package common;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class DBUtil {
    private static final String URL=
            "jdbc:mysql://127.0.0.1:3306/java_oj?characterEncoding=utf8&userSSL=true";
    private static final String USERNAME="root";
    private static final String PASSWORD="";
    private static volatile DataSource DATASOURCE;
    public DBUtil(){};
    public static DataSource getDataSource(){
        if(DATASOURCE==null){
            synchronized (DBUtil.class){
                if(DATASOURCE==null){
                    DATASOURCE=new MysqlDataSource();
                    ((MysqlDataSource)DATASOURCE).setUrl(URL);
                    ((MysqlDataSource)DATASOURCE).setUser(USERNAME);
                    ((MysqlDataSource)DATASOURCE).setPassword(PASSWORD);
                }
            }
        }
        return DATASOURCE;
    }
    public static Connection getConnection(){
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败",e);
        }
    }
    public static void close(Connection connection,
                             PreparedStatement statement, ResultSet resultSet){
        try {
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
