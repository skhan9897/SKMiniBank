package com.bank.dao;

import com.bank.model.OTP;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OTPDAO {

    private Connection con;

    public OTPDAO() {
        con = DBConnection.getConnection();
    }

    // Save OTP
    public boolean saveOTP(OTP otp) {

        boolean status = false;

        try {

            String sql = "INSERT INTO otp_verification(mobile,email,otp,otp_type,verified) VALUES(?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, otp.getMobile());
            ps.setString(2, otp.getEmail());
            ps.setString(3, otp.getOtp());
            ps.setString(4, otp.getOtpType());
            ps.setString(5, "NO");

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // Verify OTP
    public boolean verifyOTP(String email, String otp) {

    boolean status = false;

    try {

        String sql = "SELECT otp_id FROM otp_verification "
                   + "WHERE email=? AND otp=? AND verified='NO' "
                   + "ORDER BY otp_id DESC LIMIT 1";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, otp);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            int otpId = rs.getInt("otp_id");

            String updateSql = "UPDATE otp_verification "
                             + "SET verified='YES' "
                             + "WHERE otp_id=?";

            PreparedStatement update = con.prepareStatement(updateSql);
            update.setInt(1, otpId);

            if (update.executeUpdate() > 0) {
                status = true;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return status;
}
    // Mobile OTP Verify
    public boolean verifyMobileOTP(String mobile, String otp) {

        boolean status = false;

        try {

            String sql = "SELECT * FROM otp_verification WHERE mobile=? AND otp=? AND verified='NO' ORDER BY otp_id DESC LIMIT 1";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, mobile);
            ps.setString(2, otp);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                PreparedStatement update = con.prepareStatement(
                        "UPDATE otp_verification SET verified='YES' WHERE otp_id=?");

                update.setInt(1, rs.getInt("otp_id"));

                update.executeUpdate();

                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}