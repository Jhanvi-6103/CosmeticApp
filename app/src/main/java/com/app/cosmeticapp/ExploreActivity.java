package com.app.cosmeticapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Product> productList;
    ProductAdapter adapter;
    ProgressBar loadingProgressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        recyclerView = findViewById(R.id.recyclerView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Show loading
        loadingProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        FirebaseDatabase.getInstance().getReference("products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        productList.clear();
                        for (DataSnapshot productSnap : snapshot.getChildren()) {
                            Product product = productSnap.getValue(Product.class);
                            productList.add(product);
                        }

                        adapter.notifyDataSetChanged();

                        // Hide loading
                        loadingProgressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        loadingProgressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        Toast.makeText(ExploreActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
