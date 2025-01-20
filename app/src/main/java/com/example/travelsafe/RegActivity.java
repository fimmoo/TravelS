package com.example.travelsafe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelsafe.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, phoneEditText;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        // Initialize Firebase Authentication and Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize views
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        phoneEditText = findViewById(R.id.phone);
        registerButton = findViewById(R.id.register_button);

        // Set up click listener for the Register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();

                // Validate input
                if (!isValidName(name)) {
                    nameEditText.setError("Enter a valid name (minimum 3 characters)");
                    nameEditText.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Enter a valid email address");
                    emailEditText.requestFocus();
                    return;
                }

                if (!isValidPassword(password)) {
                    passwordEditText.setError("Password must be at least 6 characters with one uppercase, one lowercase, and one number");
                    passwordEditText.requestFocus();
                    return;
                }

                if (!isValidPhone(phone)) {
                    phoneEditText.setError("Enter a valid phone number");
                    phoneEditText.requestFocus();
                    return;
                }

                // Register user in Firebase
                registerUser(name, email, password, phone);
            }
        });
    }

    private boolean isValidName(String name) {
        return name.length() >= 3;
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{6,}$";
        return password.matches(passwordPattern);
    }

    private boolean isValidPhone(String phone) {
        String phonePattern = "^[+]?\\d{10,15}$"; // Supports international format
        return phone.matches(phonePattern);
    }

    private void registerUser(String name, String email, String password, String phone) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        if (user != null) {
                            // Send verification email
                            user.sendEmailVerification().addOnCompleteListener(emailTask -> {
                                if (emailTask.isSuccessful()) {
                                    saveUserToFirestore(name, email, phone);
                                    Toast.makeText(RegActivity.this, "Verification email sent. Please verify your email.", Toast.LENGTH_SHORT).show();

                                    // Redirect to Login Activity
                                    Intent intent = new Intent(RegActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(RegActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(RegActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserToFirestore(String name, String email, String phone) {
        // Create a user object
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("phone", phone);

        // Save user to Firestore
        firestore.collection("users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RegActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegActivity.this, "Failed to save user: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
