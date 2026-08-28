package com.bank.skminibank.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
    private List<Transaction> fullTransactionList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private SessionManager sessionManager;
    private MaterialButton btnDownloadPdf;
    private TextView tvTotalTransactions;
    private EditText etSearch;
    private com.google.android.material.chip.Chip chipToday;
    private AppDatabase db;
    private boolean showOnlyToday = false;
    private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

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
        etSearch = findViewById(R.id.etSearch);
        chipToday = findViewById(R.id.chipToday);

        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionsAdapter(transactionList);
        rvTransactions.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this::fetchTransactions);

        btnDownloadPdf.setOnClickListener(v -> downloadStatement());

        if (chipToday != null) {
            chipToday.setOnClickListener(v -> {
                showOnlyToday = !showOnlyToday;
                if (showOnlyToday) {
                    chipToday.setChipBackgroundColorResource(android.R.color.holo_green_dark);
                    filterTransactions(etSearch.getText().toString());
                } else {
                    chipToday.setChipBackgroundColorResource(R.color.premium_gold);
                    filterTransactions(etSearch.getText().toString());
                }
            });
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTransactions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

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
                fullTransactionList.clear();
                for (TransactionEntity e : entities) {
                    fullTransactionList.add(new Transaction(e.getTransactionId(), e.getType(), e.getAmount(), e.getDescription(), e.getDate(), e.getBalanceAfter()));
                }
                filterTransactions(etSearch.getText().toString());
            });
        }).start();
    }

    private void filterTransactions(String query) {
        transactionList.clear();
        String today = sdf.format(new java.util.Date());
        String lowerQuery = query.toLowerCase(Locale.getDefault());

        for (Transaction t : fullTransactionList) {
            boolean matchesSearch = query.isEmpty() ||
                    (t.getDescription() != null && t.getDescription().toLowerCase().contains(lowerQuery)) ||
                    (t.getTransactionId() != null && t.getTransactionId().toLowerCase().contains(lowerQuery)) ||
                    (t.getType() != null && t.getType().toLowerCase().contains(lowerQuery));

            boolean matchesToday = !showOnlyToday || (t.getDate() != null && t.getDate().contains(today));

            if (matchesSearch && matchesToday) {
                transactionList.add(t);
            }
        }

        adapter.notifyDataSetChanged();
        if (tvTotalTransactions != null) {
            tvTotalTransactions.setText("Total Transactions: " + transactionList.size());
        }
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

        String cleanAcc = accountNumber.replaceAll("\\s+", "");
        int customerId = sessionManager.getCustomerId();

        // Try with cleaned account number first
        executeFetch(cleanAcc, customerId, false);
    }

    private void executeFetch(String accNo, int customerId, boolean isRetry) {
        ApiClient.getService().getTransactions(accNo, customerId).enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {
                if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<Transaction> txns = response.body().getTransactions();
                    if (txns != null && !txns.isEmpty()) {
                        fullTransactionList.clear();
                        fullTransactionList.addAll(txns);
                        filterTransactions(etSearch.getText().toString());
                        saveTransactionsToLocal(txns);
                    } else if (!isRetry) {
                        // If nothing found, try with the RAW account number from session
                        String rawAcc = sessionManager.getAccountNumber();
                        if (rawAcc != null && !rawAcc.equals(accNo)) {
                            executeFetch(rawAcc, customerId, true);
                        } else {
                            loadTransactionsFromLocal();
                        }
                    } else {
                        loadTransactionsFromLocal();
                        if (response.body().getMessage() != null) {
                            Toast.makeText(TransactionsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    loadTransactionsFromLocal();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TransactionResponse> call, @NonNull Throwable t) {
                if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                loadTransactionsFromLocal();
                Toast.makeText(TransactionsActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
