package com.app.cosmeticapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private final ArrayList<Product> favoriteList;
    private final Context context;

    public FavoriteAdapter(Context context, ArrayList<Product> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;
        ImageButton deleteIcon;

        public ViewHolder(View view) {
            super(view);
            productImage = view.findViewById(R.id.productImage);
            productName = view.findViewById(R.id.productName);
            productPrice = view.findViewById(R.id.productPrice);
            deleteIcon = view.findViewById(R.id.deleteIcon);
        }
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, int position) {
        Product product = favoriteList.get(position);

        holder.productName.setText(product.name);
        holder.productPrice.setText("₹" + product.price);

        Glide.with(context)
                .load(product.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.productImage);

        holder.deleteIcon.setOnClickListener(v -> {
            // Remove item
            favoriteList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, favoriteList.size());

            // Save updated list
            saveFavoritesToPrefs();

            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
        });
    }

    private void saveFavoritesToPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("favorite_products", new Gson().toJson(favoriteList));
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }
}
