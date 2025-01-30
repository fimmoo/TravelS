package com.example.travelsafe;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<Items> items;

    public RoomAdapter(Context context, List<Items> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.homedesign, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set item details
        holder.titleid.setText(items.get(position).getTitleroom());
        holder.desid.setText(items.get(position).getDescroom());
        holder.rentid.setText(items.get(position).getRentroom());

        // Handle order button click to navigate to OrderActivity
        holder.book_btn.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookActivity.class);
            intent.putExtra("Room Name", items.get(position).getTitleroom());
            intent.putExtra("Room Description", items.get(position).getDescroom());
            intent.putExtra("Room Rent", items.get(position).getRentroom());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}