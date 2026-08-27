package com.bank.skminibank.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bank.skminibank.R;
import com.bank.skminibank.adapters.TransactionsAdapter;
import com.bank.skminibank.api.ApiClient;
import com.bank.skminibank.database.AppDatabase;
import com.bank.skminibank.database.TransactionEntity;
import com.bank.skminibank.model.DashboardResponse;
import com.bank.skminibank.model.Transaction;
import com.bank.skminibank.model.TransactionResponse;
import com.bank.skminibank.utils.SessionManager;
import com.bank.skminibank.service.PaymentNotificationService;
import com.bank.skminibank.utils.PaymentVoiceUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcomeUser, tvBalanceAmount, tvAccNo;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SessionManager sessionManager;
    private BottomNavigationView bottomNavigationView;
    private ImageView btnToggleBalance;
    private RecyclerView rvRecentTransactions;
    private TransactionsAdapter transactionAdapter;
    private List<Transaction> transactionList = new ArrayList<>();
    private AppDatabase db;

    private double lastFetchedBalance = 0.0;
    private boolean isBalanceVisible = false;
    private TextToSpeech tts;
    private Handler pollingHandler = new Handler();
    private boolean isFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sessionManager = new SessionManager(this);
        db = AppDatabase.getInstance(this);
        
        try {
            Intent paymentService = new Intent(this, PaymentNotificationService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(this, paymentService);
            } else {
                startService(paymentService);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        swipeRefreshLayout = findViewById(R.id.swipeRefreshDashboard);
        bottomNavigationView = findViewById(R.id.bottomNav);
        rvRecentTransactions = findViewById(R.id.rvRecentTransactions);
        
        // Setup RecyclerView
        if (rvRecentTransactions != null) {
            rvRecentTransactions.setLayoutManager(new LinearLayoutManager(this));
            transactionAdapter = new TransactionsAdapter(transactionList);
            rvRecentTransactions.setAdapter(transactionAdapter);
        }

        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        tvBalanceAmount = findViewById(R.id.tvBalanceAmount);
        tvAccNo = findViewById(R.id.tvAccNo);
        btnToggleBalance = findViewById(R.id.btnToggleBalance);

        startWaveAnimation();
        setupTTS();
        setupServiceGrid();
        setupBottomNav();
        
        populateBasicInfo();
        loadRecentTransactionsFromLocal();
        refreshData();
        startPolling();
        
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
        
        findViewById(R.id.btnProfile).setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        findViewById(R.id.btnBalanceInquiry).setOnClickListener(v -> startPinActivity());

        View btnViewAll = findViewById(R.id.btnViewAll);
        if (btnViewAll != null) {
            btnViewAll.setOnClickListener(v -> startActivity(new Intent(this, TransactionsActivity.class)));
        }

        if (btnToggleBalance != null) {
            btnToggleBalance.setOnClickListener(v -> {
                isBalanceVisible = !isBalanceVisible;
                updateBalanceVisibility();
            });
        }
    }

    private void startWaveAnimation() {
        View root = findViewById(R.id.mainDashboardRoot);
        if (root != null && root.getBackground() instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) root.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();
        }
    }

    private void setupServiceGrid() {
        // Quick Actions
        setupServiceCard(R.id.cardFundTransfer, "Fund Transfer", android.R.drawable.ic_menu_send, v -> startActivity(new Intent(this, TransferActivity.class)));
        setupServiceCard(R.id.cardToMobile, "To Mobile & UPI", android.R.drawable.ic_menu_call, v -> startActivity(new Intent(this, SendMoneyActivity.class)));
        setupServiceCard(R.id.cardToWallet, "To Wallet", android.R.drawable.ic_menu_save, v -> startActivity(new Intent(this, RazorpayActivity.class)));
        
        // Main Services
        setupServiceCard(R.id.cardBalanceInq, "Balance Inquiry", android.R.drawable.ic_menu_info_details, v -> startPinActivity());
        setupServiceCard(R.id.cardWithdrawal, "Cash Withdrawal", android.R.drawable.ic_menu_myplaces, v -> {
            Intent intent = new Intent(this, WebPortalActivity.class);
            intent.putExtra("url", "https://skminibank.onrender.com/admin/withdraw.jsp");
            intent.putExtra("title", "Cash Withdrawal");
            startActivity(intent);
        });
        setupServiceCard(R.id.cardPassbook, "Passbook", android.R.drawable.ic_menu_agenda, v -> startActivity(new Intent(this, TransactionsActivity.class)));
        
        setupServiceCard(R.id.cardFixedDeposit, "Fixed Deposit", android.R.drawable.ic_menu_view, v -> {
            Intent intent = new Intent(this, WebPortalActivity.class);
            intent.putExtra("url", "https://skminibank.onrender.com/admin/fixed-deposit.jsp");
            intent.putExtra("title", "Fixed Deposit");
            startActivity(intent);
        });
        setupServiceCard(R.id.cardUpiPayment, "UPI Payment", android.R.drawable.ic_menu_camera, v -> startActivity(new Intent(this, UpiActivity.class)));
        setupServiceCard(R.id.cardScanPay, "Scan & Pay", android.R.drawable.ic_menu_camera, v -> startScanner());
        
        setupServiceCard(R.id.cardBeneficiary, "View Beneficiary", android.R.drawable.ic_menu_mylocation, v -> startActivity(new Intent(this, AddBankActivity.class)));
        setupServiceCard(R.id.cardAtm, "ATM Card", android.R.drawable.ic_lock_lock, v -> startActivity(new Intent(this, AtmApplyActivity.class)));
        setupServiceCard(R.id.cardMiniStatement, "Mini Statement", android.R.drawable.ic_menu_recent_history, v -> startActivity(new Intent(this, MiniStatementActivity.class)));

        setupServiceCard(R.id.cardRequests, "Requests", android.R.drawable.ic_menu_edit, v -> startActivity(new Intent(this, MyRequestsActivity.class)));
        setupServiceCard(R.id.cardOffers, "Others", android.R.drawable.ic_menu_gallery, v -> startActivity(new Intent(this, AllServicesActivity.class)));
        setupServiceCard(R.id.cardMore, "More", android.R.drawable.ic_menu_more, v -> startActivity(new Intent(this, AllServicesActivity.class)));
    }

    private void setupBottomNav() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_history) {
                startActivity(new Intent(this, TransactionsActivity.class));
            } else if (id == R.id.nav_home) {
                refreshData();
            } else if (id == R.id.nav_placeholder) { // Scan & Pay
                startScanner();
                return false;
            }
            return true;
        });
    }

    private void populateBasicInfo() {
        String name = sessionManager.getCustomerName();
        tvWelcomeUser.setText("Hello, " + (name != null ? name : "User"));
        
        String acc = sessionManager.getAccountNumber();
        tvAccNo.setText("Account No. " + (acc != null ? acc : "XXXX XXXX XXXX"));
        
        updateBalanceVisibility();
    }

    private void setupServiceCard(int id, String title, int icon, View.OnClickListener listener) {
        View v = findViewById(id);
        if (v != null) {
            TextView tv = v.findViewById(R.id.tvTitle);
            ImageView iv = v.findViewById(R.id.ivIcon);
            if (tv != null) tv.setText(title);
            if (iv != null) iv.setImageResource(icon);
            v.setClickable(true);
            v.setOnClickListener(listener);
        }
    }

    private void updateBalanceVisibility() {
        if (btnToggleBalance == null) return;
        if (isBalanceVisible) {
            btnToggleBalance.setImageResource(android.R.drawable.ic_menu_view);
            tvBalanceAmount.setText(String.format(Locale.getDefault(), "₹ %.2f", lastFetchedBalance));
        } else {
            btnToggleBalance.setImageResource(android.R.drawable.ic_secure);
            tvBalanceAmount.setText("₹ •••••");
        }
    }

    private void refreshData() {
        int customerId = sessionManager.getCustomerId();
        String accNo = sessionManager.getAccountNumber();
        if (customerId != -1) {
            fetchDashboardData(customerId);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
        
        if (accNo != null) {
            fetchRecentTransactions(accNo);
        }
    }

    private void fetchDashboardData(int customerId) {
        ApiClient.getService().getDashboardData(customerId).enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(@NonNull Call<DashboardResponse> call, @NonNull Response<DashboardResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    updateUI(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<DashboardResponse> call, @NonNull Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void fetchRecentTransactions(String accNo) {
        ApiClient.getService().getTransactions(accNo).enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Transaction> txns = response.body().getTransactions();
                    if (txns != null) {
                        saveTransactionsToLocal(txns);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TransactionResponse> call, @NonNull Throwable t) {
                loadRecentTransactionsFromLocal();
            }
        });
    }

    private void loadRecentTransactionsFromLocal() {
        String acc = sessionManager.getAccountNumber();
        if (acc == null) return;
        
        new Thread(() -> {
            List<TransactionEntity> entities = db.transactionDao().getAllTransactions(acc);
            runOnUiThread(() -> {
                transactionList.clear();
                // Get only last 5 from local
                for (int i = 0; i < Math.min(entities.size(), 5); i++) {
                    TransactionEntity e = entities.get(i);
                    transactionList.add(new Transaction(e.getTransactionId(), e.getType(), e.getAmount(), e.getDescription(), e.getDate(), e.getBalanceAfter()));
                }
                if (transactionAdapter != null) {
                    transactionAdapter.notifyDataSetChanged();
                }
            });
        }).start();
    }

    private void saveTransactionsToLocal(List<Transaction> txns) {
        String acc = sessionManager.getAccountNumber();
        if (acc == null) return;
        
        new Thread(() -> {
            for (Transaction t : txns) {
                String tid = t.getTransactionId();
                if (tid == null || tid.isEmpty() || tid.equals("null")) {
                    tid = "TXN_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
                }
                
                if (!db.transactionDao().isTransactionExists(acc, tid)) {
                    db.transactionDao().insertTransaction(new TransactionEntity(
                            acc, tid, t.getType(), t.getAmount(), t.getDescription(), t.getDate(), t.getBalanceAfter()
                    ));
                }
            }
            loadRecentTransactionsFromLocal();
        }).start();
    }

    private void updateUI(DashboardResponse data) {
        double newBalance = data.getBalance();
        if (!isFirstLoad && newBalance > lastFetchedBalance) {
            double diff = newBalance - lastFetchedBalance;
            PaymentVoiceUtil.speakPayment(this, diff, true);
        }
        
        lastFetchedBalance = newBalance;
        isFirstLoad = false;
        
        updateBalanceVisibility();
        
        if (data.getAccountNumber() != null) {
            sessionManager.setAccountNumber(data.getAccountNumber());
            tvAccNo.setText("Account No. " + data.getAccountNumber());
        }
        
        if (data.getCustomerName() != null) {
            tvWelcomeUser.setText("Hello, " + data.getCustomerName());
        }
    }

    private void startScanner() {
        // Assume VerticalCaptureActivity is used for scanning
        Intent intent = new Intent(this, VerticalCaptureActivity.class);
        startActivity(intent);
    }

    private void startPinActivity() {
        Intent intent = new Intent(this, UpiPinActivity.class);
        intent.putExtra("purpose", "balance");
        startActivity(intent);
    }

    private void showPlaceholder(String feature) {
        Toast.makeText(this, feature + " feature coming soon", Toast.LENGTH_SHORT).show();
    }

    private void setupTTS() {
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(new Locale("en", "IN"));
            }
        });
    }

    private void startPolling() {
        pollingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshData();
                pollingHandler.postDelayed(this, 10000); // Check every 10 seconds
            }
        }, 10000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        pollingHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
