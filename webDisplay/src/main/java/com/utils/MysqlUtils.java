package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MysqlUtils {
    public static String driver ="com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://192.168.211.3:3306" +
            "/mydatabase?useUnicode=true&characterEncoding=utf-8&useSSL=true";
    public static String user ="root";
    public static String password ="root";

    public static PreparedStatement getPreparedStatement(String sql){
        PreparedStatement ps = null;
        try {
            Class.forName(driver);
            Connection  conn = DriverManager.getConnection(url,user,password);
            ps= conn.prepareStatement(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static void closeResource(PreparedStatement ps){
        try {
            //could get a connection through PreparedStatement
            Connection conn = ps.getConnection();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
