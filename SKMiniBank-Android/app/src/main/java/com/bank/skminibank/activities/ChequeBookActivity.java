package com.bank.skminibank.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bank.skminibank.R;
import com.bank.skminibank.api.ApiClient;
import com.bank.skminibank.model.GenericResponse;
import com.bank.skminibank.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChequeBookActivity extends AppCompatActivity {

    private RadioGroup rgChequeType;
    private MaterialButton btnApply;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque_book);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Cheque Book");
        }

        sessionManager = new SessionManager(this);
        rgChequeType = findViewById(R.id.rgChequeType);
        btnApply = findViewById(R.id.btnApplyCheque);

        btnApply.setOnClickListener(v -> submitRequest());
    }

    private void submitRequest() {
        int selectedId = rgChequeType.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select cheque book size", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(selectedId);
        String chequeType = rb.getText().toString();
        int customerId = sessionManager.getCustomerId();
        String accountNumber = sessionManager.getAccountNumber();

        btnApply.setEnabled(false);
        btnApply.setText("Submitting...");

        ApiClient.getService().applyChequeBook(customerId, accountNumber, chequeType).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {
                btnApply.setEnabled(true);
                btnApply.setText("SUBMIT REQUEST");

                if (response.isSuccessful() && response.body() != null) {
                    GenericResponse res = response.body();
                    if ("success".equalsIgnoreCase(res.getStatus())) {
                        Toast.makeText(ChequeBookActivity.this, "Request Submitted Successfully", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(ChequeBookActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                btnApply.setEnabled(true);
                btnApply.setText("SUBMIT REQUEST");
                Toast.makeText(ChequeBookActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
