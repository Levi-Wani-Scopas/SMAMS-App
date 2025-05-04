package com.example.smams;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class DoctorDashboard extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_dashboard);

        // Save user session
        SharedPreferences prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        prefs.edit().putString("userType", "doctor").apply();


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.doctor_toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.doctor_bottom_navigation);




        // Setup Navigation Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // Set Default Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new DoctorDashboardFragment()).commit();
        }


        // Handle Bottom Navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_dashboard) {
                    selectedFragment = new DoctorDashboardFragment();
                } else if (item.getItemId() == R.id.nav_doctors) {
                    selectedFragment = new DoctorPatientFragment();
                } else if (item.getItemId() == R.id.nav_schedule) {
                    selectedFragment = new DoctorScheduleFragment();
                } else if (item.getItemId() == R.id.nav_account) {
                    selectedFragment = new DoctorAccountFragment();
                }


                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
                }
                return true;
            }
        });

        // Handle Navigation Item Clicks
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_settings) {
                    startActivity(new Intent(DoctorDashboard.this, SettingsActivity.class));
                } else if (id == R.id.nav_terms_and_conditions) {
                    startActivity(new Intent(DoctorDashboard.this, TermsAndConditionsActivity.class));
                } else if (id == R.id.nav_privacy) {
                    startActivity(new Intent(DoctorDashboard.this, PrivacyActivity.class));
                } else if (id == R.id.nav_cookies) {
                    startActivity(new Intent(DoctorDashboard.this, CookiesActivity.class));
                } else if (id == R.id.nav_help) {
                    startActivity(new Intent(DoctorDashboard.this, HelpActivity.class));
                }else if (id == R.id.nav_aboutUs) {
                    startActivity(new Intent(DoctorDashboard.this, AboutUsActivity.class));
                } else if (id == R.id.nav_SMAMS) {
                    Toast.makeText(DoctorDashboard.this, "Smart Medication Adherence Monitoring System", Toast.LENGTH_SHORT).show();
                }

                // Close the drawer after selecting an item
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // Close the drawer if it's open when the back button is pressed
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}