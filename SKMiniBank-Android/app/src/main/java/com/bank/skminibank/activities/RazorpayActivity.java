package com.bank.skminibank.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bank.skminibank.R;
import com.google.android.material.button.MaterialButton;

public class RazorpayActivity extends AppCompatActivity {

    private EditText etAmount;
    private MaterialButton btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);

        etAmount = findViewById(R.id.etWalletAmount);
        btnProceed = findViewById(R.id.btnProceedPayment);
        
        btnProceed.setOnClickListener(v -> {
            String amountStr = etAmount.getText().toString().trim();
            if (!amountStr.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount >= 10) {
                        simulatePayment(amount);
                    } else {
                        Toast.makeText(this, "Minimum amount is ₹10", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void simulatePayment(double amount) {
        // Step 1: Connecting
        btnProceed.setEnabled(false);
        btnProceed.setText("Connecting to Secure Server...");
        
        new Handler().postDelayed(() -> {
            // Step 2: Processing
            btnProceed.setText("Processing ₹" + (int)amount + "...");
            
            new Handler().postDelayed(() -> {
                // Step 3: Verifying
                btnProceed.setText("Verifying with Bank...");
                
                new Handler().postDelayed(() -> {
                    // Step 4: Final Success
                    btnProceed.setText("Payment Successful!");
                    
                    new Handler().postDelayed(() -> {
                        // Open Success Screen
                        Intent intent = new Intent(RazorpayActivity.this, PaymentSuccessActivity.class);
                        intent.putExtra("amount", amount);
                        intent.putExtra("transactionId", "SKMB" + System.currentTimeMillis());
                        intent.putExtra("status", "SUCCESS");
                        startActivity(intent);
                        finish();
                    }, 1000);

                }, 1500);
                
            }, 2000);
            
        }, 1500);
    }
}
