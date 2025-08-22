package com.app.cosmeticapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView productImage, favIcon, cartIcon;
    TextView productName, productPrice, productRating, productDescription, productHowToUse, productReviews;
    Button buyNowButton;

    ArrayList<Product> favoriteList;
    ArrayList<Product> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Initialize Views
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productRating = findViewById(R.id.productRating);
        productDescription = findViewById(R.id.productDescription);
        productHowToUse = findViewById(R.id.productHowToUse);
        productReviews = findViewById(R.id.productReviews);
        buyNowButton = findViewById(R.id.buyNowButton);
        favIcon = findViewById(R.id.favIcon);
        cartIcon = findViewById(R.id.cartIcon);

        // Get product from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            String json = intent.getStringExtra("product");
            Product product = new Gson().fromJson(json, Product.class);

            // Set product details
            productName.setText(product.name);
            productPrice.setText("₹" + product.price);
            productRating.setText("Rating: " + product.rating);
            productDescription.setText(product.description);
            productHowToUse.setText("Apply evenly on face or desired area, twice daily.");
            productReviews.setText("⭐⭐⭐⭐☆\nUsers love the smooth texture and fragrance!");

            Glide.with(this)
                    .load(product.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(productImage);

            // SharedPreferences & Type
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            Type listType = new TypeToken<ArrayList<Product>>() {}.getType();

            // Favorite setup
            String favJson = prefs.getString("favorite_products", null);
            favoriteList = favJson != null ? new Gson().fromJson(favJson, listType) : new ArrayList<>();
            boolean isFav = false;
            for (Product p : favoriteList) {
                if (p.id.equals(product.id)) {
                    isFav = true;
                    break;
                }
            }
            favIcon.setImageResource(isFav ? R.drawable.favv : R.drawable.favourite);

            favIcon.setOnClickListener(v -> {
                boolean found = false;
                for (Product p : favoriteList) {
                    if (p.id.equals(product.id)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    favoriteList.removeIf(p -> p.id.equals(product.id));
                    favIcon.setImageResource(R.drawable.favourite);
                    Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    favoriteList.add(product);
                    favIcon.setImageResource(R.drawable.favv);
                    Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                }

                prefs.edit().putString("favorite_products", new Gson().toJson(favoriteList)).apply();
            });

            // Cart setup
            String cartJson = prefs.getString("cart_products", null);
            cartList = cartJson != null ? new Gson().fromJson(cartJson, listType) : new ArrayList<>();
            boolean inCart = false;
            for (Product p : cartList) {
                if (p.id.equals(product.id)) {
                    inCart = true;
                    break;
                }
            }
            cartIcon.setImageResource(inCart ? R.drawable.cartt : R.drawable.cart);

            cartIcon.setOnClickListener(v -> {
                boolean exists = false;
                for (Product p : cartList) {
                    if (p.id.equals(product.id)) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    cartList.removeIf(p -> p.id.equals(product.id));
                    cartIcon.setImageResource(R.drawable.cart);
                    Toast.makeText(this, "Removed from Cart", Toast.LENGTH_SHORT).show();
                } else {
                    cartList.add(product);
                    cartIcon.setImageResource(R.drawable.cartt);
                    Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
                }

                prefs.edit().putString("cart_products", new Gson().toJson(cartList)).apply();
            });

            // Buy Now
            buyNowButton.setOnClickListener(v -> {
                Intent buyIntent = new Intent(Intent.ACTION_VIEW);
                buyIntent.setData(Uri.parse("https://www.yourwebsite.com/buy")); // Replace with actual product link
                startActivity(buyIntent);
            });
        }
    }
}
