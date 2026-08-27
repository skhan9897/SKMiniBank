package com.bank.api;

import com.bank.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/setup-db")
public class DatabaseSetupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/plain");
        StringBuilder logs = new StringBuilder("Starting Database Setup...\n");

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement()) {
            
            // 1. Ensure users table exists
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "username VARCHAR(100), " +
                         "email VARCHAR(100), " +
                         "password VARCHAR(100), " +
                         "role VARCHAR(20), " +
                         "status VARCHAR(20))");
            logs.append("- Users table verified.\n");

            // 2. Add columns to service_request manually (Resilient way)
            String[][] srColumns = {
                {"remarks", "TEXT"},
                {"approved_by", "VARCHAR(100)"},
                {"approval_date", "TIMESTAMP NULL"},
                {"expected_delivery_date", "DATE NULL"},
                {"dispatched_date", "DATE NULL"},
                {"delivered_date", "DATE NULL"}
            };

            for (String[] col : srColumns) {
                if (!columnExists(con, "service_request", col[0])) {
                    stmt.execute("ALTER TABLE service_request ADD COLUMN " + col[0] + " " + col[1]);
                    logs.append("- Column '").append(col[0]).append("' added to service_request.\n");
                }
            }

            // 3. Add columns to customer manually
            String[][] custColumns = {
                {"photo", "VARCHAR(255) DEFAULT 'default_user.png'"},
                {"mobile_verified", "VARCHAR(10) DEFAULT 'NO'"},
                {"email_verified", "VARCHAR(10) DEFAULT 'NO'"},
                {"upi_id", "VARCHAR(100)"},
                {"upi_status", "VARCHAR(20) DEFAULT 'ACTIVE'"},
                {"transaction_pin", "VARCHAR(10)"}
            };

            for (String[] col : custColumns) {
                if (!columnExists(con, "customer", col[0])) {
                    stmt.execute("ALTER TABLE customer ADD COLUMN " + col[0] + " " + col[1]);
                    logs.append("- Column '").append(col[0]).append("' added to customer.\n");
                }
            }
            
            logs.append("\nDATABASE SETUP COMPLETED SUCCESSFULLY!");
            response.getWriter().write(logs.toString());

        } catch (Exception e) {
            response.getWriter().write("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean columnExists(Connection con, String tableName, String columnName) {
        try (ResultSet rs = con.getMetaData().getColumns(null, null, tableName, columnName)) {
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }
}
