package com.app.cosmeticapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // Splash delay
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 4000); // 8 seconds

        // Load the GIF
        ImageView gifLogo = findViewById(R.id.gifLogo);
        Glide.with(this)
                .asGif()
                .load(R.drawable.gifbeauty) // your gif in res/drawable/
                .into(gifLogo);


    }
}

