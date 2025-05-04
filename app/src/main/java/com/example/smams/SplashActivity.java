package com.example.smams;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler();
    ImageView splash1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        splash1 = findViewById(R.id.splashPic);

        handler.postDelayed(() -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                // User is signed in, determine role
                SharedPreferences prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
                String userType = prefs.getString("userType", "");

                switch (userType) {
                    case "patient":
                        startActivity(new Intent(SplashActivity.this, PatientDashboard.class));
                        break;
                    case "doctor":
                        startActivity(new Intent(SplashActivity.this, DoctorDashboard.class));
                        break;
                    case "admin":
                        startActivity(new Intent(SplashActivity.this, AdminDashboard.class));
                        break;
                    default:
                        startActivity(new Intent(SplashActivity.this, AuthenticationActivity.class));
                        break;
                }
            } else {
                // No user signed in
                startActivity(new Intent(SplashActivity.this, AuthenticationActivity.class));
            }

            finish();
        }, 5000);
    }
}