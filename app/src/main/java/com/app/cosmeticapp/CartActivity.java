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

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter adapter;
    ArrayList<Product> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart); // Make sure this layout contains cartRecyclerView

        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String json = prefs.getString("cart_products", null);

        if (json != null) {
            Type type = new TypeToken<ArrayList<Product>>() {}.getType();
            cartList = new Gson().fromJson(json, type);
        } else {
            cartList = new ArrayList<>();
        }

        if (cartList.isEmpty()) {
            Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
        }

        adapter = new CartAdapter(this, cartList); // Use CartAdapter instead of ProductAdapter
        recyclerView.setAdapter(adapter);
    }
}
