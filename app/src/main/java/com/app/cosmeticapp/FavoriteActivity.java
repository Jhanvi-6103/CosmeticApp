package com.app.cosmeticapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FavoriteAdapter adapter;
    ArrayList<Product> favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.favoriteRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String json = prefs.getString("favorite_products", null);

        if (json != null) {
            Type type = new TypeToken<ArrayList<Product>>() {}.getType();
            favoriteList = new Gson().fromJson(json, type);
        } else {
            favoriteList = new ArrayList<>();
        }
        if (favoriteList.isEmpty()) {
            Toast.makeText(this, "Favorite List is empty!", Toast.LENGTH_SHORT).show();
        }

        adapter = new FavoriteAdapter(this, favoriteList);
        recyclerView.setAdapter(adapter);
    }
}
