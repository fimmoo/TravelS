package com.example.travelsafe;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText dateformatID;
    private EditText dateformatID2;
    private EditText reservationName; // Change here to use the correct variable name
    private int year, month, day;
    private Button book_now;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;

    private String selectedRoomType = "";
    private String checkinDate = "";
    private String checkoutDate = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        firebaseAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinner = findViewById(R.id.spinner);
        String[] items = new String[]{"Select a Room", "Single Bedroom", "Double Bedroom", "Deluxe Double Bedroom"};

        spinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRoomType = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Initialize reservationName EditText
        reservationName = findViewById(R.id.Reservationname);

        dateformatID = findViewById(R.id.dateformatID);
        Calendar calendar = Calendar.getInstance();

        dateformatID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);

                        checkinDate = SimpleDateFormat.getDateInstance().format(calendar.getTime());
                        dateformatID.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        dateformatID2 = findViewById(R.id.dateformatID2);
        Calendar calendar1 = Calendar.getInstance();
        dateformatID2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar1.get(Calendar.YEAR);
                month = calendar1.get(Calendar.MONTH);
                day = calendar1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        calendar1.set(Calendar.YEAR, i);
                        calendar1.set(Calendar.MONTH, i1);
                        calendar1.set(Calendar.DAY_OF_MONTH, i2);

                        checkoutDate = SimpleDateFormat.getDateInstance().format(calendar1.getTime());
                        dateformatID2.setText(SimpleDateFormat.getDateInstance().format(calendar1.getTime()));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        book_now = findViewById(R.id.book_now);
        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    saveOrderToFirestore(selectedRoomType, checkinDate, checkoutDate, reservationName.getText().toString()); // Pass the reservation name

                    Intent intent = new Intent(BookActivity.this, PaymentActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInputs() {
        if (selectedRoomType.equals("Select a Room")) {
            Toast.makeText(this, "Please select a valid room type.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (checkinDate.isEmpty()) {
            Toast.makeText(this, "Please select a check-in date.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (checkoutDate.isEmpty()) {
            Toast.makeText(this, "Please select a check-out date.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (reservationName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveOrderToFirestore(String roomType, String checkindate, String checkoutdate, String reservationName) {
        Map<String, Object> order = new HashMap<>();
        order.put("roomtype", roomType);
        order.put("checkindate", checkindate);
        order.put("checkoutdate", checkoutdate);
        order.put("reservationName", reservationName);

        String userName = firebaseAuth.getCurrentUser().getDisplayName();

        db.collection("bookinginfo")
                .document(firebaseAuth.getCurrentUser().getUid())
                .set(order)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(BookActivity.this, "Booking is done successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(BookActivity.this, "Failed to Book: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
