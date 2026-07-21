package com.bank.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Sajid Khan
 */
public class Fast2SMSOTPUtil {

    // Fast2SMS API Key
    private static final String API_KEY = "2a5U3CB3egJeushFYS4aGBbY9I10G4YkpX53dO899Ju1q6B5yt036SPtlqpp";

    // OTP Send API
    private static final String SEND_URL =
            "https://www.fast2sms.com/dev/otp/send";

    // OTP Verify API
    private static final String VERIFY_URL =
            "https://www.fast2sms.com/dev/otp/verify";

    // Send OTP

    /**
     *
     * @param mobile
     * @return
     */
    public static String sendOTP(String mobile) {

        try {

            URL url = new URL(SEND_URL);

            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Authorization", API_KEY);

            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);

            String json =
                    "{"
                    + "\"mobile\":\"" + mobile + "\","
                    + "\"otp_length\":\"6\","
                    + "\"otp_expiry\":\"5\""
                    + "}";

            OutputStream os = con.getOutputStream();

            os.write(json.getBytes());

            os.flush();

            os.close();

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(con.getInputStream()));

            String line;

            StringBuilder response =
                    new StringBuilder();

            while((line=br.readLine())!=null){

                response.append(line);

            }

            br.close();

            return response.toString();

        }catch(Exception e){

            e.printStackTrace();

            return e.getMessage();

        }

    }

    // Verify OTP

    /**
     *
     * @param mobile
     * @param otp
     * @return
     */
    public static String verifyOTP(String mobile,String otp){

        try{

            URL url=new URL(VERIFY_URL);

            HttpURLConnection con=
                    (HttpURLConnection)url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Authorization",API_KEY);

            con.setRequestProperty("Content-Type","application/json");

            con.setDoOutput(true);

            String json=
                    "{"
                    +"\"mobile\":\""+mobile+"\","
                    +"\"otp\":\""+otp+"\""
                    +"}";

            OutputStream os=con.getOutputStream();

            os.write(json.getBytes());

            os.flush();

            os.close();

            BufferedReader br=
                    new BufferedReader(
                            new InputStreamReader(con.getInputStream()));

            String line;

            StringBuilder response=
                    new StringBuilder();

            while((line=br.readLine())!=null){

                response.append(line);

            }

            br.close();

            return response.toString();

        }catch(Exception e){

            e.printStackTrace();

            return e.getMessage();

        }

    }

}