package com.bank.skminibank.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bank.skminibank.R;
import com.bank.skminibank.adapters.TransactionsAdapter;
import com.bank.skminibank.api.ApiClient;
import com.bank.skminibank.api.ApiService;
import com.bank.skminibank.model.Transaction;
import com.bank.skminibank.model.TransactionResponse;
import com.bank.skminibank.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import android.content.Intent;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsActivity extends AppCompatActivity {

    private RecyclerView rvTransactions;
    private TransactionsAdapter adapter;
    private List<Transaction> transactionList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private SessionManager sessionManager;
    private MaterialButton btnDownloadPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Passbook");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        sessionManager = new SessionManager(this);
        rvTransactions = findViewById(R.id.rvTransactions);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        btnDownloadPdf = findViewById(R.id.btnDownloadPdf);

        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionsAdapter(transactionList);
        rvTransactions.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this::fetchTransactions);

        btnDownloadPdf.setOnClickListener(v -> downloadStatement());

        fetchTransactions();
    }

    private void downloadStatement() {
        String accountNumber = sessionManager.getAccountNumber();
        if (accountNumber == null) return;

        // Base URL from ApiClient is internal, let's construct the full link
        // We'll use the same BASE_URL used for Retrofit
        String url = "https://skminibank-1.onrender.com/TransactionPDFServlet?accountNumber=" + accountNumber;
        
        Toast.makeText(this, "Opening Statement PDF...", Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void fetchTransactions() {
        swipeRefresh.setRefreshing(true);
        String accountNumber = sessionManager.getAccountNumber();

        if (accountNumber == null) {
            swipeRefresh.setRefreshing(false);
            return;
        }

        ApiClient.getService().getTransactions(accountNumber).enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    TransactionResponse res = response.body();
                    String status = res.getStatus();
                    if (status != null && (status.equalsIgnoreCase("success") || status.equalsIgnoreCase("ok") || status.equalsIgnoreCase("true"))) {
                        if (res.getTransactions() != null && !res.getTransactions().isEmpty()) {
                            transactionList.clear();
                            transactionList.addAll(res.getTransactions());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(TransactionsActivity.this, "No transactions found", Toast.LENGTH_SHORT).show();
                        }
                    } else if (res.getTransactions() != null && !res.getTransactions().isEmpty()) {
                        // Even if status is missing, if we have data, show it
                        transactionList.clear();
                        transactionList.addAll(res.getTransactions());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(TransactionsActivity.this, "No transactions found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TransactionsActivity.this, "Server Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TransactionResponse> call, @NonNull Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(TransactionsActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
