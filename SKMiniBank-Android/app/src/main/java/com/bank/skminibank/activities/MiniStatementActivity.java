package com.bank.skminibank.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bank.skminibank.R;
import com.bank.skminibank.adapters.TransactionAdapter;
import com.bank.skminibank.api.ApiClient;
import com.bank.skminibank.model.Transaction;
import com.bank.skminibank.model.TransactionResponse;
import com.bank.skminibank.utils.SessionManager;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiniStatementActivity extends AppCompatActivity {

    private RecyclerView rvMiniStatement;
    private TransactionAdapter adapter;
    private List<Transaction> transactionList = new ArrayList<>();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_statement);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sessionManager = new SessionManager(this);
        rvMiniStatement = findViewById(R.id.rvMiniStatement);
        rvMiniStatement.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(this, transactionList);
        rvMiniStatement.setAdapter(adapter);

        fetchMiniStatement();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void fetchMiniStatement() {
        String accountNumber = sessionManager.getAccountNumber();
        if (accountNumber != null) {
            ApiClient.getService().getTransactions(accountNumber).enqueue(new Callback<TransactionResponse>() {
                @Override
                public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        transactionList.clear();
                        // Show only the last 5 transactions
                        int count = 0;
                        for (int i = response.body().getTransactions().size() - 1; i >= 0 && count < 5; i--, count++) {
                            transactionList.add(response.body().getTransactions().get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<TransactionResponse> call, Throwable t) {
                    // Handle failure
                }
            });
        }
    }
}