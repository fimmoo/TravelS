package com.example.travelsafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class BookingHistoryActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView rname, t1, t2, t3;
    private Button fetchButton;
    private Button Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        db = FirebaseFirestore.getInstance();


        rname = findViewById(R.id.rname);
        t1 = findViewById(R.id.date1);
        t2 = findViewById(R.id.date2);
        t3 = findViewById(R.id.reservename);
        fetchButton = findViewById(R.id.fetch);
        Home = findViewById(R.id.Home);

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingHistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchOrderHistory();
            }
        });
    }

    private void fetchOrderHistory() {
        db.collection("bookinginfo")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                            String roomName = document.getString("roomtype");
                            String checkin = document.getString("checkindate");
                            String checkout = document.getString("checkoutdate");
                            String reservename = document.getString("reservationName");

                            if (roomName != null && checkin != null && checkout != null && reservename != null) {
                                rname.setText("Room Name: " + roomName);
                                t1.setText("Check-In-Date: " + checkin);
                                t2.setText("Check-Out-Date: " + checkout);
                                t3.setText("Reservation: " + reservename);
                            } else {
                                Toast.makeText(BookingHistoryActivity.this, "Some fields are null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(BookingHistoryActivity.this, "No Booking History Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(BookingHistoryActivity.this, "Error Fetching Data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
