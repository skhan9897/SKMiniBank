package com.bank.skminibank.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.bank.skminibank.R;
import com.bank.skminibank.api.ApiClient;
import com.bank.skminibank.api.ApiService;
import com.bank.skminibank.model.GenericResponse;
import com.bank.skminibank.model.LoginResponse;
import com.bank.skminibank.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etMobile, etOtp, etPassword;
    private TextInputLayout tilOtp, tilPassword;
    private LinearLayout layoutLanding, layoutLoginForm;
    private SessionManager sessionManager;
    private MaterialButton btnAction, btnLandingOpenAccount, btnShowLogin;
    private View btnBiometricLogin;
    private TextView tvBiometricLabel;
    
    private int loginStep = 1; // 1: Send OTP, 2: Verify OTP, 3: Password & Login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        layoutLanding = findViewById(R.id.layoutLanding);
        layoutLoginForm = findViewById(R.id.layoutLoginForm);

        btnLandingOpenAccount = findViewById(R.id.btnLandingOpenAccount);
        btnShowLogin = findViewById(R.id.btnShowLogin);
        btnBiometricLogin = findViewById(R.id.btnBiometricLogin);
        tvBiometricLabel = findViewById(R.id.tvBiometricLabel);

        etMobile = findViewById(R.id.etMobileNumber);
        etOtp = findViewById(R.id.etOtp);
        etPassword = findViewById(R.id.etPassword);
        tilOtp = findViewById(R.id.tilOtp);
        tilPassword = findViewById(R.id.tilPassword);
        btnAction = findViewById(R.id.btnLogin);

        // 1. Logic for First Time Install / New User
        if (!sessionManager.isLoggedInOnce()) {
            layoutLanding.setVisibility(View.VISIBLE);
            layoutLoginForm.setVisibility(View.GONE);
            if (btnBiometricLogin != null) btnBiometricLogin.setVisibility(View.GONE);
        } else {
            layoutLanding.setVisibility(View.GONE);
            layoutLoginForm.setVisibility(View.VISIBLE);
            loginStep = 3;
            tilPassword.setVisibility(View.VISIBLE);
            btnAction.setText("LOGIN");
            
            // Show Biometric Button for returning users
            if (btnBiometricLogin != null) {
                btnBiometricLogin.setVisibility(View.VISIBLE);
                updateBiometricUI();
                
                btnBiometricLogin.setOnClickListener(v -> {
                    if (sessionManager.isBiometricEnabled()) {
                        showBiometricPrompt();
                    } else {
                        Toast.makeText(this, "Please login with password once to enable Fingerprint", Toast.LENGTH_LONG).show();
                    }
                });
            }

            // Auto-prompt if enabled
            if (sessionManager.isBiometricEnabled()) {
                new android.os.Handler().postDelayed(this::showBiometricPrompt, 500);
            }
        }

        if (sessionManager.getMobile() != null) {
            etMobile.setText(sessionManager.getMobile());
        }

        btnLandingOpenAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, OpenAccountActivity.class));
        });

        btnShowLogin.setOnClickListener(v -> {
            layoutLanding.setVisibility(View.GONE);
            layoutLoginForm.setVisibility(View.VISIBLE);
        });

        btnAction.setOnClickListener(v -> handleLoginFlow());

        findViewById(R.id.btnForgotPassword).setOnClickListener(v -> {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        });

        findViewById(R.id.btnOpenAccount).setOnClickListener(v -> {
            layoutLoginForm.setVisibility(View.GONE);
            layoutLanding.setVisibility(View.VISIBLE);
        });
    }

    private void showBiometricPrompt() {
        BiometricManager biometricManager = BiometricManager.from(this);
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) != BiometricManager.BIOMETRIC_SUCCESS) {
            return;
        }

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                String mobile = sessionManager.getMobile();
                String pass = sessionManager.getPassword();
                if (mobile != null && pass != null) {
                    loginUser(mobile, pass, "9897"); // Default OTP for biometric
                }
            }

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode != BiometricPrompt.ERROR_USER_CANCELED && errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    Toast.makeText(LoginActivity.this, "Biometric Error: " + errString, Toast.LENGTH_SHORT).show();
                }
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login")
                .setSubtitle("Use your fingerprint to login safely")
                .setNegativeButtonText("Use Password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void showBiometricSetupDialog() {
        BiometricManager biometricManager = BiometricManager.from(this);
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) != BiometricManager.BIOMETRIC_SUCCESS) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
            return;
        }

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Enable Fingerprint Login")
                .setMessage("Would you like to use your fingerprint for future logins? It's faster and more secure.")
                .setPositiveButton("Yes, Enable", (dialog, which) -> {
                    setupFingerprintForLogin();
                })
                .setNegativeButton("No Thanks", (dialog, which) -> {
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    private void setupFingerprintForLogin() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                sessionManager.setBiometricEnabled(true);
                Toast.makeText(LoginActivity.this, "Fingerprint Login Enabled!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // Even if setup fails, go to dashboard
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Confirm Fingerprint")
                .setSubtitle("Confirm your identity to enable biometric login")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void updateBiometricUI() {
        if (tvBiometricLabel == null) return;
        if (sessionManager.isBiometricEnabled()) {
            tvBiometricLabel.setText("Login with Fingerprint");
        } else {
            tvBiometricLabel.setText("Enable Fingerprint Login");
        }
    }

    private void handleLoginFlow() {
        String mobile = etMobile.getText().toString().trim();
        
        if (loginStep == 1) {
            if (mobile.length() != 10) {
                etMobile.setError("Enter valid mobile");
                return;
            }
            sendOtpRequest(mobile);
        } else if (loginStep == 2) {
            String otp = etOtp.getText().toString().trim();
            if (otp.length() != 4) {
                etOtp.setError("Enter 4-digit OTP");
                return;
            }
            loginStep = 3;
            tilPassword.setVisibility(View.VISIBLE);
            btnAction.setText("LOGIN");
        } else if (loginStep == 3) {
            String pass = etPassword.getText().toString().trim();
            String otp = etOtp.getText().toString().trim();
            
            if (sessionManager.isLoggedInOnce()) {
                otp = "9897";
            }

            if (pass.isEmpty()) {
                etPassword.setError("Enter Password");
                return;
            }
            loginUser(mobile, pass, otp);
        }
    }

    private void sendOtpRequest(String mobile) {
        btnAction.setEnabled(false);
        btnAction.setText("Sending OTP...");

        ApiClient.getService().sendOtp(mobile).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {
                btnAction.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        loginStep = 2;
                        tilOtp.setVisibility(View.VISIBLE);
                        btnAction.setText("VERIFY OTP");
                        Toast.makeText(LoginActivity.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        btnAction.setText("SEND OTP");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                btnAction.setEnabled(true);
                btnAction.setText("SEND OTP");
                Toast.makeText(LoginActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(String mobile, String password, String otp) {
        btnAction.setEnabled(false);
        btnAction.setText("Logging in...");

        ApiClient.getService().login(mobile, password, otp).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                btnAction.setEnabled(true);
                btnAction.setText("VERIFY & LOGIN");

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse res = response.body();
                    if (res.isSuccess()) {
                        sessionManager.createLoginSession(
                                res.getCustomerId(),
                                res.getCustomerName(),
                                res.getAccountNumber(),
                                password,
                                mobile,
                                res.getEmail(),
                                res.getKycStatus()
                        );
                        
                        if (!sessionManager.isBiometricEnabled()) {
                            showBiometricSetupDialog();
                        } else {
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                btnAction.setEnabled(true);
                btnAction.setText("VERIFY & LOGIN");
                Toast.makeText(LoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
