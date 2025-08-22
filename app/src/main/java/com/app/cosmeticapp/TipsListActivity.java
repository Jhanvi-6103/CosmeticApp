package com.app.cosmeticapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class TipsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TipListAdapter adapter;
    private ArrayList<TipItem> tipList;

    private DatabaseReference tipsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_list);

        recyclerView = findViewById(R.id.recyclerViewTipList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tipList = new ArrayList<>();
        adapter = new TipListAdapter(tipList, this);
        recyclerView.setAdapter(adapter);

        String category = getIntent().getStringExtra("category");

        if (category == null || category.isEmpty()) {
            Toast.makeText(this, "No category provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tipsRef = FirebaseDatabase.getInstance().getReference("BeautyTips").child(category);
        loadTips();
    }

    private void loadTips() {
        tipsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tipList.clear();
                for (DataSnapshot tipSnapshot : snapshot.getChildren()) {
                    TipItem tip = tipSnapshot.getValue(TipItem.class);
                    if (tip != null) {
                        tipList.add(tip);
                    }
                }

                if (tipList.isEmpty()) {
                    Toast.makeText(TipsListActivity.this, "No tips available", Toast.LENGTH_SHORT).show();
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TipsListActivity.this, "Failed to load tips: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
