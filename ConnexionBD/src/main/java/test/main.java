package test;
import utils.MyDB;

import java.sql.SQLException;
import java.util.List;

public class main {
    public static void main(String[] args) {
        MyDB db1 = MyDB.getInstance();
        MyDB db2 = MyDB.getInstance();


        System.out.println(db1.hashCode());
        System.out.println(db2.hashCode());}


}
