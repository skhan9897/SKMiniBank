package com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/skminibank";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";   // अपना MySQL Password लिखें

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