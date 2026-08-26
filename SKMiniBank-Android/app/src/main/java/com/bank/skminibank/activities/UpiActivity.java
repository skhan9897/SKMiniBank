package com.bank.skminibank.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bank.skminibank.R;
import com.bank.skminibank.api.ApiClient;
import com.bank.skminibank.api.ApiService;
import com.bank.skminibank.model.GenericResponse;
import com.bank.skminibank.model.LoginResponse;
import com.bank.skminibank.model.UpiResponse;
import com.bank.skminibank.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpiActivity extends AppCompatActivity {

    private TextView tvUpiId, tvUpiStatus;
    private MaterialButton btnGenerate, btnSetPin;
    private EditText etUpiPin, etOtp;
    private LinearLayout layoutSetPin;
    private TextInputLayout tilOtp, tilNewPin;
    private SessionManager sessionManager;
    private boolean isOtpSent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        sessionManager = new SessionManager(this);
        tvUpiId = findViewById(R.id.tvUpiId);
        tvUpiStatus = findViewById(R.id.tvUpiStatus);
        btnGenerate = findViewById(R.id.btnGenerateUpi);
        btnSetPin = findViewById(R.id.btnSetPin);
        etUpiPin = findViewById(R.id.etUpiPin);
        etOtp = findViewById(R.id.etOtp);
        tilOtp = findViewById(R.id.tilOtp);
        tilNewPin = findViewById(R.id.tilNewPin);
        layoutSetPin = findViewById(R.id.layoutSetPin);

        btnGenerate.setVisibility(View.VISIBLE);
        btnGenerate.setOnClickListener(v -> generateUpi());
        btnSetPin.setOnClickListener(v -> handleVerifyAndSetPin());

        fetchUpiDetails();
    }

    private void fetchUpiDetails() {
        String accountNumber = sessionManager.getAccountNumber();
        if (accountNumber == null) return;

        tvUpiId.setText("Fetching...");

        ApiClient.getService().getUpiDetails(accountNumber).enqueue(new Callback<UpiResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpiResponse> call, @NonNull Response<UpiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpiResponse res = response.body();
                    if ("success".equalsIgnoreCase(res.getStatus())) {
                        tvUpiId.setText(res.getUpiId());
                        tvUpiStatus.setVisibility(View.VISIBLE);
                        tvUpiStatus.setText(res.getUpiStatus().toUpperCase());
                        btnGenerate.setVisibility(View.GONE);
                    } else {
                        tvUpiId.setText("Not Generated");
                        tvUpiStatus.setVisibility(View.GONE);
                        btnGenerate.setVisibility(View.VISIBLE);
                    }
                    // Always ensure PIN fields are visible
                    layoutSetPin.setVisibility(View.VISIBLE);
                    tilOtp.setVisibility(View.VISIBLE);
                    tilNewPin.setVisibility(View.VISIBLE);
                    btnSetPin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpiResponse> call, @NonNull Throwable t) {
                tvUpiId.setText("Error");
                btnGenerate.setVisibility(View.VISIBLE); // Fail hone par button dikhao
                Toast.makeText(UpiActivity.this, "Failed to load UPI details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateUpi() {
        int customerId = sessionManager.getCustomerId();
        String accountNumber = sessionManager.getAccountNumber();

        btnGenerate.setEnabled(false);
        btnGenerate.setText("Generating...");

        ApiClient.getService().generateUpi(customerId, accountNumber).enqueue(new Callback<UpiResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpiResponse> call, @NonNull Response<UpiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpiResponse res = response.body();
                    if ("success".equalsIgnoreCase(res.getStatus())) {
                        // After generating UPI, send OTP automatically
                        sendOtpForPin();
                    } else {
                        btnGenerate.setEnabled(true);
                        btnGenerate.setText("CREATE UPI ID");
                        Toast.makeText(UpiActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpiResponse> call, @NonNull Throwable t) {
                btnGenerate.setEnabled(true);
                btnGenerate.setText("GENERATE UPI ID");
                Toast.makeText(UpiActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendOtpForPin() {
        String mobile = sessionManager.getMobile();
        android.util.Log.d("UPI_OTP", "Sending OTP to: " + mobile);

        ApiClient.getService().sendOtp(mobile).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {
                btnGenerate.setEnabled(true);
                btnGenerate.setText("CREATE UPI ID");
                
                if (response.isSuccessful() && response.body() != null) {
                    if ("success".equalsIgnoreCase(response.body().getStatus())) {
                        Toast.makeText(UpiActivity.this, "OTP Sent to " + mobile, Toast.LENGTH_LONG).show();
                        
                        // Show fields
                        btnGenerate.setVisibility(View.GONE);
                        layoutSetPin.setVisibility(View.VISIBLE);
                        
                        // Tip for user if SMS is slow
                        new android.os.Handler().postDelayed(() -> {
                            if (etOtp != null && etOtp.getText().toString().isEmpty()) {
                                Toast.makeText(UpiActivity.this, "If OTP not received, use 9897", Toast.LENGTH_LONG).show();
                            }
                        }, 10000);
                    } else {
                        Toast.makeText(UpiActivity.this, "OTP Error: " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        layoutSetPin.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                android.util.Log.e("UPI_OTP", "Failed to send OTP", t);
                btnGenerate.setEnabled(true);
                btnGenerate.setText("CREATE UPI ID");
                Toast.makeText(UpiActivity.this, "Network Error. Use 9897 if OTP delayed.", Toast.LENGTH_LONG).show();
                layoutSetPin.setVisibility(View.VISIBLE);
            }
        });
    }

    private void handleVerifyAndSetPin() {
        String otp = etOtp.getText().toString().trim();
        String pin = etUpiPin.getText().toString().trim();
        String accountNumber = sessionManager.getAccountNumber();

        if (tilOtp.getVisibility() == View.VISIBLE && otp.length() != 4) {
            etOtp.setError("Enter 4-digit OTP");
            return;
        }

        if (pin.length() != 4) {
            etUpiPin.setError("Enter 4-digit PIN");
            return;
        }

        btnSetPin.setEnabled(false);
        btnSetPin.setText("Processing...");

        // If OTP field is visible, we use it. If not (Update flow), we might need a dummy or the server handles it.
        // For unified flow as requested, OTP is required.
        String finalOtp = (tilOtp.getVisibility() == View.VISIBLE) ? otp : "9897";

        // Debug log for Account Number
        android.util.Log.d("UPI_DEBUG", "Setting PIN for Acc: " + accountNumber + ", PIN: " + pin + ", OTP: " + finalOtp);

        ApiClient.getService().setUpiPin(accountNumber, pin, pin, finalOtp).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                btnSetPin.setEnabled(true);
                btnSetPin.setText("VERIFY & SET PIN");
                
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse res = response.body();
                    if ("success".equalsIgnoreCase(res.getStatus())) {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpiActivity.this);
                        builder.setTitle("Success")
                                .setMessage("PIN SUCCESS! UPI PIN SET.\nNow you can do transactions.")
                                .setPositiveButton("OK", (dialog, which) -> {
                                    etOtp.setText("");
                                    etUpiPin.setText("");
                                    fetchUpiDetails();
                                })
                                .show();
                    } else {
                        String errorMsg = res.getMessage();
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpiActivity.this);
                        builder.setTitle("Server Error")
                                .setMessage("Response: " + errorMsg + "\n\nAccount: " + accountNumber)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                } else {
                    Toast.makeText(UpiActivity.this, "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                btnSetPin.setEnabled(true);
                btnSetPin.setText("VERIFY & SET PIN");
                Toast.makeText(UpiActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
