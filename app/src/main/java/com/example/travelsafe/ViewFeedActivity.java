package com.example.travelsafe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private List<Items> items;
    private RoomAdapter adapter;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed);

        recyclerView = findViewById(R.id.recyclerView);
        firestore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        items = new ArrayList<>();
        adapter = new RoomAdapter(this, items);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);

        fetchProductsFromFirestore();
    }

    private void fetchProductsFromFirestore() {
        progressBar.setVisibility(View.VISIBLE);

        firestore.collection("RoomDetails")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    items.clear();
                    progressBar.setVisibility(View.GONE);

                    for (QueryDocumentSnapshot document : querySnapshot) {
                        try {
                            String iid = document.getId();
                            String title = document.getString("roomtitle");
                            String description = document.getString("roomdescription");
                            String rent = document.getString("roomRent");

                            if (iid != null && title != null && description != null && rent != null) {
                                items.add(new Items(iid, title, description, rent));
                            } else {
                                Log.e("ViewFeedActivity", "Null data in document: " + document.getId());
                            }
                        } catch (Exception e) {
                            Log.e("ViewFeedActivity", "Error parsing document", e);
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Log.e("ViewFeedActivity", "Error fetching data", e);
                    Toast.makeText(ViewFeedActivity.this, "Failed to fetch data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
