package com.app.cosmeticapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TipCategoryAdapter extends RecyclerView.Adapter<TipCategoryAdapter.ViewHolder> {

    private final ArrayList<TipCategory> categoryList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TipCategory category);
    }

    public TipCategoryAdapter(ArrayList<TipCategory> list, OnItemClickListener listener) {
        this.categoryList = list;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.tipIcon);
            title = itemView.findViewById(R.id.tipTitle);
        }

        public void bind(final TipCategory item, final OnItemClickListener listener) {
            title.setText(item.getTitle());
            icon.setImageResource(item.getIconResId());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

    @NonNull
    @Override
    public TipCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tip_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipCategoryAdapter.ViewHolder holder, int position) {
        holder.bind(categoryList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
