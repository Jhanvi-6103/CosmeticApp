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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final ArrayList<Product> cartList;
    private final Context context;

    public CartAdapter(Context context, ArrayList<Product> cartList) {
        this.context = context;
        this.cartList = cartList;
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
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {
        Product product = cartList.get(position);

        holder.productName.setText(product.name);
        holder.productPrice.setText("₹" + product.price);

        Glide.with(context)
                .load(product.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.productImage);

        holder.deleteIcon.setOnClickListener(v -> {
            cartList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartList.size());
            saveCartToPrefs();
            Toast.makeText(context, "Removed from cart", Toast.LENGTH_SHORT).show();
        });
    }

    private void saveCartToPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cart_products", new Gson().toJson(cartList));
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
