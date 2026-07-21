package com.bank.dao;

import com.bank.model.KYC;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class KYCDAO {

    public boolean verifyKYC(KYC kyc) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            // Check if KYC already exists
            String checkSql = "SELECT customer_id FROM kyc WHERE customer_id=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, kyc.getCustomerId());

            if (checkPs.executeQuery().next()) {

                String updateSql =
                    "UPDATE kyc SET kyc_status=?, verification_date=? WHERE customer_id=?";

                PreparedStatement ps = con.prepareStatement(updateSql);

                ps.setString(1, kyc.getKycStatus());
                ps.setString(2, kyc.getVerificationDate());
                ps.setInt(3, kyc.getCustomerId());

                status = ps.executeUpdate() > 0;

            } else {

                String insertSql =
                    "INSERT INTO kyc(customer_id,kyc_status,verification_date) VALUES(?,?,?)";

                PreparedStatement ps = con.prepareStatement(insertSql);

                ps.setInt(1, kyc.getCustomerId());
                ps.setString(2, kyc.getKycStatus());
                ps.setString(3, kyc.getVerificationDate());

                status = ps.executeUpdate() > 0;
            }

            // Update customer table
            PreparedStatement ps2 = con.prepareStatement(
                "UPDATE customer SET kyc_status=? WHERE customer_id=?");

            ps2.setString(1, kyc.getKycStatus());
            ps2.setInt(2, kyc.getCustomerId());

            ps2.executeUpdate();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
    
    
}