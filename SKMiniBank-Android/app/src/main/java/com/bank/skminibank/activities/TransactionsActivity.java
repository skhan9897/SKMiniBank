package com.bank.skminibank.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
import com.bank.skminibank.database.AppDatabase;
import com.bank.skminibank.database.TransactionEntity;
import com.bank.skminibank.model.Transaction;
import com.bank.skminibank.model.TransactionResponse;
import com.bank.skminibank.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

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
    private TextView tvTotalTransactions;
    private AppDatabase db;

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
        db = AppDatabase.getInstance(this);
        rvTransactions = findViewById(R.id.rvTransactions);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        btnDownloadPdf = findViewById(R.id.btnDownloadPdf);
        tvTotalTransactions = findViewById(R.id.tvTotalTransactions);

        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionsAdapter(transactionList);
        rvTransactions.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this::fetchTransactions);

        btnDownloadPdf.setOnClickListener(v -> downloadStatement());

        loadTransactionsFromLocal();
        fetchTransactions();
        startWaveAnimation();
    }

    private void loadTransactionsFromLocal() {
        String acc = sessionManager.getAccountNumber();
        if (acc == null) return;
        String cleanAcc = acc.replaceAll("\\s+", "");
        
        new Thread(() -> {
            List<TransactionEntity> entities = db.transactionDao().getAllTransactions(cleanAcc);
            runOnUiThread(() -> {
                transactionList.clear();
                for (TransactionEntity e : entities) {
                    transactionList.add(new Transaction(e.getTransactionId(), e.getType(), e.getAmount(), e.getDescription(), e.getDate(), e.getBalanceAfter()));
                }
                adapter.notifyDataSetChanged();
                if (tvTotalTransactions != null) {
                    tvTotalTransactions.setText("Total Transactions: " + transactionList.size());
                }
            });
        }).start();
    }

    private void saveTransactionsToLocal(List<Transaction> txns) {
        String acc = sessionManager.getAccountNumber();
        if (acc == null) return;
        String cleanAcc = acc.replaceAll("\\s+", "");
        
        new Thread(() -> {
            for (Transaction t : txns) {
                String tid = t.getTransactionId();
                if (tid == null || tid.isEmpty() || tid.equals("null")) {
                    tid = "TXN_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
                }
                
                if (!db.transactionDao().isTransactionExists(cleanAcc, tid)) {
                    db.transactionDao().insertTransaction(new TransactionEntity(
                            cleanAcc, tid, t.getType(), t.getAmount(), t.getDescription(), t.getDate(), t.getBalanceAfter()
                    ));
                }
            }
            loadTransactionsFromLocal();
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchTransactions();
    }

    private void startWaveAnimation() {
        View root = findViewById(R.id.transactionsRoot);
        if (root != null && root.getBackground() instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) root.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();
        }
    }

    private void downloadStatement() {
        String accountNumber = sessionManager.getAccountNumber();
        if (accountNumber == null) return;

        String url = "https://skminibank-1.onrender.com/TransactionPDFServlet?accountNumber=" + accountNumber;
        
        Toast.makeText(this, "Opening Statement PDF...", Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void fetchTransactions() {
        if (swipeRefresh != null) swipeRefresh.setRefreshing(true);
        String accountNumber = sessionManager.getAccountNumber();

        if (accountNumber == null) {
            if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
            return;
        }

        // Clean account number (remove any spaces)
        String cleanAcc = accountNumber.replaceAll("\\s+", "");
        int customerId = sessionManager.getCustomerId();

        ApiClient.getService().getTransactions(cleanAcc, customerId).enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {
                if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<Transaction> txns = response.body().getTransactions();
                    if (txns != null && !txns.isEmpty()) {
                        saveTransactionsToLocal(txns);
                    } else {
                        // If API returns empty, but local has data, just keep local
                        loadTransactionsFromLocal();
                    }
                } else {
                    loadTransactionsFromLocal();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TransactionResponse> call, @NonNull Throwable t) {
                if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                loadTransactionsFromLocal();
                Toast.makeText(TransactionsActivity.this, "Server Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
