package com.bank.skminibank.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    private static final String TAG = "LoginActivity";
    private EditText etMobile, etOtp, etPassword;
    private TextInputLayout tilOtp, tilPassword;
    private LinearLayout layoutFingerprint;
    private SessionManager sessionManager;
    private MaterialButton btnAction;
    
    private int loginStep = 1; // 1: Send OTP, 2: Verify OTP, 3: Password & Login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        etMobile = findViewById(R.id.etMobileNumber);
        etOtp = findViewById(R.id.etOtp);
        etPassword = findViewById(R.id.etPassword);
        tilOtp = findViewById(R.id.tilOtp);
        tilPassword = findViewById(R.id.tilPassword);
        btnAction = findViewById(R.id.btnLogin);
        layoutFingerprint = findViewById(R.id.layoutFingerprint);

        startWaveAnimation();

        // Sub login flow: If user already performed OTP once, skip it
        if (sessionManager.isLoggedInOnce()) {
            loginStep = 3; // Direct to password
            tilPassword.setVisibility(View.VISIBLE);
            btnAction.setText("LOGIN");
        }

        // Pre-fill if available
        if (sessionManager.getMobile() != null) {
            etMobile.setText(sessionManager.getMobile());
        }

        btnAction.setOnClickListener(v -> handleLoginFlow());

        findViewById(R.id.btnForgotPassword).setOnClickListener(v -> {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        });

        findViewById<View>(R.id.btnOpenAccount).setOnClickListener(v -> {
            startActivity(new Intent(this, InstantAccountActivity.class));
        });

        setupBiometrics();
    }

    private void startWaveAnimation() {
        View root = findViewById(R.id.loginRoot);
        if (root != null && root.getBackground() instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) root.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();
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
            // Move to password step
            loginStep = 3;
            tilPassword.setVisibility(View.VISIBLE);
            btnAction.setText("LOGIN");
        } else if (loginStep == 3) {
            String pass = etPassword.getText().toString().trim();
            String otp = etOtp.getText().toString().trim();
            
            // If skip OTP was used, use a dummy or skip server side check
            if (sessionManager.isLoggedInOnce()) {
                otp = "9897"; // Bypassing for returning users
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
                    if ("success".equalsIgnoreCase(response.body().getStatus())) {
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
                    if ("success".equalsIgnoreCase(res.getStatus())) {
                        sessionManager.createLoginSession(
                                res.getCustomerId(),
                                res.getCustomerName(),
                                res.getAccountNumber(),
                                password,
                                mobile,
                                res.getEmail()
                        );
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String errorMsg = "Server Error: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            errorMsg += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {}
                    Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
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

    private void setupBiometrics() {
        BiometricManager biometricManager = BiometricManager.from(this);
        int canAuth = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.BIOMETRIC_WEAK);

        if (canAuth == BiometricManager.BIOMETRIC_SUCCESS && sessionManager.getMobile() != null && sessionManager.getPassword() != null) {
            layoutFingerprint.setVisibility(View.VISIBLE);
            layoutFingerprint.setOnClickListener(v -> showBiometricPrompt());
        } else {
            layoutFingerprint.setVisibility(View.GONE);
        }
    }

    private void showBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // For biometric, we bypass the OTP step if credentials are saved
                loginUser(sessionManager.getMobile(), sessionManager.getPassword(), "9897"); // Mock OTP for biometric
            }
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Secure Login")
                .setSubtitle("Login with Fingerprint")
                .setNegativeButtonText("Use Mobile & OTP")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}