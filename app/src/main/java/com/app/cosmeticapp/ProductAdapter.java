package com.app.cosmeticapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final List<Product> productList;
    private final Context context;

    public ProductAdapter(Context ctx, List<Product> list) {
        this.context = ctx;
        this.productList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productCategory;

        public ViewHolder(View view) {
            super(view);
            productImage = view.findViewById(R.id.productImage);
            productName = view.findViewById(R.id.productName);
            productPrice = view.findViewById(R.id.productPrice);
            productCategory = view.findViewById(R.id.productCategory);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        // Bind data to views
        holder.productName.setText(product.name);
        holder.productPrice.setText("₹" + product.price);
        holder.productCategory.setText(product.category);

        // Load image with Glide
        Glide.with(context)
                .load(product.imageUrl)
                .placeholder(R.drawable.placeholder) // optional placeholder image
                .into(holder.productImage);

        // Open product detail on click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product", new Gson().toJson(product));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
