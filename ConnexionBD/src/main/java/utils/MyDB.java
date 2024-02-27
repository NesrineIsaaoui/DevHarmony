package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {


    private final String URL = "jdbc:mysql://localhost:3306/esprit";
    private final String USER = "root";
    private final String PWD = "";
    private Connection connection;

    private static MyDB instance;

    public MyDB(){
        try {
            connection = DriverManager.getConnection(URL,USER,PWD);
            System.out.println("connected to BD");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static MyDB getInstance(){
        if (instance == null){
            instance = new MyDB();
        }return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}