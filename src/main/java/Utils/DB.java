package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static DB instance ;
    private Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/test";
    private static String login = "root";
    private static String pwd = "";
    private DB(){
        try {
            connection = DriverManager.getConnection(url, login, pwd);
            System.out.println("connexion établie");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public static DB getInstance(){
        if(instance==null){
            instance=new DB();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
