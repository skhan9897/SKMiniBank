package com.bank.skminibank.activities;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
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
import com.bank.skminibank.model.Transaction;
import com.bank.skminibank.model.TransactionResponse;
import com.bank.skminibank.utils.SessionManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiniStatementActivity extends AppCompatActivity {

    private RecyclerView rvMiniStatement;
    private TransactionsAdapter adapter;
    private List<Transaction> transactionList = new ArrayList<>();
    private SessionManager sessionManager;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_statement);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Mini Statement");
        }

        sessionManager = new SessionManager(this);
        rvMiniStatement = findViewById(R.id.rvMiniStatement);
        swipeRefresh = findViewById(R.id.swipeRefreshMini);
        
        rvMiniStatement.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionsAdapter(transactionList);
        rvMiniStatement.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this::fetchMiniStatement);

        fetchMiniStatement();
        startWaveAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMiniStatement();
    }

    private void startWaveAnimation() {
        View root = findViewById(R.id.miniStatementRoot);
        if (root != null && root.getBackground() instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) root.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void fetchMiniStatement() {
        swipeRefresh.setRefreshing(true);
        String accountNumber = sessionManager.getAccountNumber();
        if (accountNumber != null) {
            String cleanAcc = accountNumber.replaceAll("\\s+", "");
            ApiClient.getService().getTransactions(cleanAcc).enqueue(new Callback<TransactionResponse>() {
                @Override
                public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {
                    swipeRefresh.setRefreshing(false);
                    if (response.isSuccessful() && response.body() != null) {
                        List<Transaction> allTransactions = response.body().getTransactions();
                        if (allTransactions != null && !allTransactions.isEmpty()) {
                            transactionList.clear();
                            // Show all transactions till today as requested
                            List<Transaction> reversedList = new ArrayList<>(allTransactions);
                            Collections.reverse(reversedList);
                            transactionList.addAll(reversedList);
                            adapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(MiniStatementActivity.this, "No transactions found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TransactionResponse> call, @NonNull Throwable t) {
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(MiniStatementActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            swipeRefresh.setRefreshing(false);
        }
    }
}