package com.app.cosmeticapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    ImageView exploreBtn, profileBtn, quizBtn, tipsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cosmetic App");

        // Initialize buttons
        exploreBtn = findViewById(R.id.exploreBtn);
        profileBtn = findViewById(R.id.profileBtn);
        quizBtn = findViewById(R.id.quizBtn);
        tipsBtn = findViewById(R.id.tipsBtn);

        // Set button click listeners
        exploreBtn.setOnClickListener(v -> startActivity(new Intent(this, ExploreActivity.class)));
        profileBtn.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        quizBtn.setOnClickListener(v -> startActivity(new Intent(this, QuizActivity.class)));
        tipsBtn.setOnClickListener(v -> startActivity(new Intent(this, TipsActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar_menu, menu); // Inflate your menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite) {
            startActivity(new Intent(this, FavoriteActivity.class));
            return true;
        } else if (id == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
