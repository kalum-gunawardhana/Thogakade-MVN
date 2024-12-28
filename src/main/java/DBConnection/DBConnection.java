package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection ob;
    private Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
    }

    public Connection getConnection(){
        return  connection;
    }

    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        if(ob==null){
            ob=new DBConnection();
        }
        return ob;
    }
}
