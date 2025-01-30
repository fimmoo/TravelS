package com.example.travelsafe;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView titleid, desid,rentid;
    Button book_btn;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        titleid = itemView.findViewById(R.id.titleid);
        desid = itemView.findViewById(R.id.desid);
        rentid = itemView.findViewById(R.id.rentid);
        book_btn = itemView.findViewById(R.id.book_btn);

    }
}