package com.example.travelsafe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity2 extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private Button btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn = findViewById(R.id.btn);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // jodi already user login kore thake
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {

            // repeatedly login korte hobe na
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
