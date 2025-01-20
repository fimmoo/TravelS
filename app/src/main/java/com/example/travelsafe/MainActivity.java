package com.example.travelsafe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.main);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);
        navigationView = findViewById(R.id.navigation_view);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });
        View headerView = navigationView.getHeaderView(0);
        TextView title = headerView.findViewById(R.id.t1);
        TextView website = headerView.findViewById(R.id.t2);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "Home Clicked!", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.nav_room) {
                    Toast.makeText(MainActivity.this, "Welcome!",Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.nav_login) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "You are already logged in!", Toast.LENGTH_SHORT).show();
                }

                if (itemId == R.id.nav_profile) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class); //profile e jabe
                    startActivity(intent);
                }

                if (itemId == R.id.nav_logout) {
                    Toast.makeText(MainActivity.this, "You are logged out!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.nav_share) {
                    Intent intent = new Intent(MainActivity.this, ShareActivity.class); //shareactivity
                    startActivity(intent);
                }

                if (itemId == R.id.nav_rate) {
                    Intent intent = new Intent(MainActivity.this, RateActivity.class); //rate page e nibe
                    startActivity(intent);
                }

                drawerLayout.close();
                return false;
            }
        });


    }
}

