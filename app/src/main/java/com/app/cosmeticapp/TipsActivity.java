package com.app.cosmeticapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TipsActivity extends AppCompatActivity implements TipCategoryAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private TipCategoryAdapter adapter;
    private ArrayList<TipCategory> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        recyclerView = findViewById(R.id.recyclerViewTips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryList = new ArrayList<>();
        populateCategories();

        adapter = new TipCategoryAdapter(categoryList, this);
        recyclerView.setAdapter(adapter);
    }

    private void populateCategories() {
        categoryList.add(new TipCategory("Daily Skin Care Routine", R.drawable.icon_skincare));
        categoryList.add(new TipCategory("Natural DIY Beauty Tips", R.drawable.icon_diy));
        categoryList.add(new TipCategory("Product Usage Tips", R.drawable.icon_product));
        categoryList.add(new TipCategory("Hygiene Tips", R.drawable.icon_hygiene));
        categoryList.add(new TipCategory("Makeup Tips", R.drawable.icon_makeup));
    }

    @Override
    public void onItemClick(TipCategory category) {
        Intent intent = new Intent(this, TipsListActivity.class);
        intent.putExtra("category", category.getTitle());  // ✅ Fixed key
        startActivity(intent);
    }
}
