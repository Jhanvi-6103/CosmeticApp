package com.app.cosmeticapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class TipListAdapter extends RecyclerView.Adapter<TipListAdapter.ViewHolder> {

    private final ArrayList<TipItem> tipList;
    private final Context context;

    public TipListAdapter(ArrayList<TipItem> list, Context context) {
        this.tipList = list;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tipTitleText);
            description = view.findViewById(R.id.tipDescText);
            image = view.findViewById(R.id.tipImage);
        }
    }

    @NonNull
    @Override
    public TipListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipListAdapter.ViewHolder holder, int position) {
        TipItem tip = tipList.get(position);
        holder.title.setText(tip.getTitle());
        holder.description.setText(tip.getDescription());

        Glide.with(context)
                .load(tip.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return tipList.size();
    }
}
