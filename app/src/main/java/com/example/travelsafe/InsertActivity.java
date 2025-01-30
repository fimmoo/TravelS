package com.example.travelsafe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class InsertActivity extends AppCompatActivity {

    private EditText title, description, rent;
    private Button insert_btn, view;
    private ProgressBar progressBar;
    private FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        FirebaseApp.initializeApp(this);

        title = findViewById(R.id.t1);
        description = findViewById(R.id.t2);
        rent = findViewById(R.id.rent);
        insert_btn = findViewById(R.id.btn_insert);
        progressBar = findViewById(R.id.progressBar);
        firestore = FirebaseFirestore.getInstance();
        insert_btn.setOnClickListener(v -> insertProduct());

    }



    private void insertProduct() {
        String roomtitle = title.getText().toString().trim();
        String roomdescription = description.getText().toString().trim();
        String roomRent = rent.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        if (roomtitle.isEmpty() || roomdescription.isEmpty() || roomRent.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(InsertActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = UUID.randomUUID().toString();
        saveProductDataToFirestore(id, roomtitle, roomdescription, roomRent);
    }

    private void saveProductDataToFirestore(String id, String roomtitle, String roomdescription, String roomRent) {
        Product product = new Product(id, roomtitle, roomdescription, roomRent);

        firestore.collection("RoomDetails")
                .document(id)
                .set(product)
                .addOnSuccessListener(aVoid -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(InsertActivity.this, "Room details added successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(InsertActivity.this, ViewFeedActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                    Toast.makeText(InsertActivity.this, "Failed to add room details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public static class Product {
        private String id;
        private String roomtitle;
        private String roomdescription;
        private String roomRent;

        public Product(String id, String roomtitle, String roomdescription, String roomRent) {
            this.id = id;
            this.roomtitle = roomtitle;
            this.roomdescription = roomdescription;
            this.roomRent = roomRent;
        }

        public String getId() {
            return id;
        }

        public String getRoomtitle() {
            return roomtitle;
        }

        public String getRoomdescription() {
            return roomdescription;
        }

        public String getRoomRent() {
            return roomRent;
        }
    }
}
