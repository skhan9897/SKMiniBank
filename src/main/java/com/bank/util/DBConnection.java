package com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://bgu4lq3qy1n3jxgyg6nv-mysql.services.clever-cloud.com:3306/bgu4lq3qy1n3jxgyg6nv?useSSL=true&requireSSL=true&serverTimezone=UTC";

    private static final String USERNAME = "usxiqkn8avl74kyr";
    private static final String PASSWORD = "Gvx0N5WR2lcNVhTLuSbY";

    private static Connection con;

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Database Connected Successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}