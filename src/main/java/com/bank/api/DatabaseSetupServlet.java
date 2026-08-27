package com.bank.api;

import com.bank.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
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
        
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement()) {
            
            // Ensure service_request table has all necessary columns
            String[] queries = {
                "ALTER TABLE service_request ADD COLUMN IF NOT EXISTS remarks TEXT",
                "ALTER TABLE service_request ADD COLUMN IF NOT EXISTS approved_by VARCHAR(100)",
                "ALTER TABLE service_request ADD COLUMN IF NOT EXISTS approval_date TIMESTAMP NULL",
                "ALTER TABLE service_request ADD COLUMN IF NOT EXISTS expected_delivery_date DATE NULL",
                "ALTER TABLE service_request ADD COLUMN IF NOT EXISTS dispatched_date DATE NULL",
                "ALTER TABLE service_request ADD COLUMN IF NOT EXISTS delivered_date DATE NULL",
                "ALTER TABLE service_request MODIFY COLUMN request_details TEXT"
            };
            
            for (String query : queries) {
                try {
                    stmt.execute(query);
                } catch (Exception e) {
                    // Ignore errors if column already exists or other minor issues
                }
            }
            
            response.getWriter().write("Database setup completed successfully.");
        } catch (Exception e) {
            response.getWriter().write("Error setting up database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
