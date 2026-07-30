package com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://bgu4lq3qy1n3jxgyg6nv-mysql.services.clever-cloud.com:3306/bgu4lq3qy1n3jxgyg6nv?useSSL=true&requireSSL=true&serverTimezone=UTC";

    private static final String USERNAME = "usxiqkn8avl74kyr";
    private static final String PASSWORD = "Gvx0N5WR2lcNVhTLuSbY";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }
    }
}
