package com.bank.skminibank.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bank.skminibank.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View logoContainer = findViewById(R.id.logoContainer);
        View branding = findViewById(R.id.layoutBranding);
        View developer = findViewById(R.id.layoutDeveloper);
        View line = findViewById(R.id.lineUnderName);
        View root = findViewById(android.R.id.content);
        ImageView ivRotatingStar = findViewById(R.id.ivRotatingStar);
        ProgressBar progressBar = findViewById(R.id.splashProgress);

        // 1. Initial State
        logoContainer.setAlpha(0f);
        logoContainer.setScaleX(0.5f);
        logoContainer.setScaleY(0.5f);
        
        ivRotatingStar.setAlpha(0f);

        branding.setAlpha(0f);
        branding.setTranslationY(80f);
        
        line.setScaleX(0f);

        developer.setAlpha(0f);
        developer.setTranslationY(40f);

        // 2. ANIMATION SEQUENCE
        
        // Step A: Logo Container Zoom In
        logoContainer.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(1200)
                .setInterpolator(new AnticipateOvershootInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Step B: Continuous Spin of the Star around the logo
                        ivRotatingStar.setAlpha(0.6f);
                        Animation rotation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.spinner_rotation);
                        ivRotatingStar.startAnimation(rotation);

                        // Step C: Branding Slide & Line Expand
                        new Handler().postDelayed(() -> {
                            branding.animate()
                                    .alpha(1f)
                                    .translationY(0f)
                                    .setDuration(1000)
                                    .setInterpolator(new DecelerateInterpolator())
                                    .start();
                            
                            line.animate()
                                    .scaleX(1f)
                                    .setDuration(1200)
                                    .setInterpolator(new AccelerateDecelerateInterpolator())
                                    .start();

                            // Step D: Developer Info Fade Up
                            new Handler().postDelayed(() -> {
                                developer.animate()
                                        .alpha(1f)
                                        .translationY(0f)
                                        .setDuration(1000)
                                        .start();
                            }, 800);
                        }, 600);
                    }
                })
                .start();

        // 3. Progress Line Animation (3 Seconds)
        android.animation.ObjectAnimator progressAnimator = android.animation.ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        progressAnimator.setDuration(3000);
        progressAnimator.setInterpolator(new android.view.animation.LinearInterpolator());
        progressAnimator.start();

        // 4. FINAL HOLD AND TRANSITION (After 3 seconds)
        new Handler().postDelayed(() -> {
            if (isFinishing()) return;
            root.animate()
                .scaleX(1.5f)
                .scaleY(1.5f)
                .alpha(0f)
                .setDuration(500)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(() -> {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                })
                .start();
        }, 3500); // 3 seconds for progress + 0.5 for transition

    }
}