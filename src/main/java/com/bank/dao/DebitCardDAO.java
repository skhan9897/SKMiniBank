package com.bank.dao;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Random;

import com.bank.model.DebitCard;
import com.bank.util.DBConnection;

public class DebitCardDAO {

    public boolean saveCard(DebitCard card) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO debit_card(customer_id,account_number,customer_name,card_type,card_number,expiry_date,cvv,status,dispatch_status,expected_delivery) VALUES(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, card.getCustomerId());
            ps.setString(2, card.getAccountNumber());
            ps.setString(3, card.getCustomerName());
            ps.setString(4, card.getCardType());
            ps.setString(5, card.getCardNumber());
            ps.setString(6, card.getExpiryDate());
            ps.setString(7, card.getCvv());
            ps.setString(8, card.getStatus());
            ps.setString(9, card.getDispatchStatus());
            ps.setString(10, card.getExpectedDelivery());

            status = ps.executeUpdate() > 0;

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
        
    }
    
    
  

    

public DebitCard getCardByCustomerId(int customerId) {

    DebitCard card = null;

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM debit_card WHERE customer_id=? ORDER BY card_id DESC LIMIT 1");

        ps.setInt(1, customerId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            card = new DebitCard();

            card.setCardId(rs.getInt("card_id"));
            card.setCustomerId(rs.getInt("customer_id"));
            card.setAccountNumber(rs.getString("account_number"));
            card.setCustomerName(rs.getString("customer_name"));
            card.setCardType(rs.getString("card_type"));
            card.setCardNumber(rs.getString("card_number"));
            card.setExpiryDate(rs.getString("expiry_date"));
            card.setCvv(rs.getString("cvv"));
            card.setStatus(rs.getString("status"));
            card.setDispatchStatus(rs.getString("dispatch_status"));
            card.setExpectedDelivery(rs.getString("expected_delivery"));
        }

        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return card;
}
public static String generateCardNumber() {

    Random r = new Random();

    return "5218"
            + (1000 + r.nextInt(9000))
            + (1000 + r.nextInt(9000))
            + (1000 + r.nextInt(9000));
}

public static String generateCVV() {

    Random r = new Random();

    return String.valueOf(100 + r.nextInt(900));
}

public static String generateExpiry() {

    LocalDate date = LocalDate.now().plusYears(5);

    return String.format("%02d/%02d",
            date.getMonthValue(),
            date.getYear() % 100);
}
}
