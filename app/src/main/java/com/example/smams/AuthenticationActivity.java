package com.example.smams;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AuthenticationActivity extends AppCompatActivity {

    ImageView imgPatient,imgDoctor, imgAdmin;
    TextView txtPatient, txtDoctor, txtAdmin, Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);


        imgPatient = findViewById(R.id.ImagePatients);
        imgDoctor = findViewById(R.id.ImageDoctors);
        imgAdmin = findViewById(R.id.ImageAdmin);

        txtPatient = findViewById(R.id.Txtpatient);
        txtDoctor = findViewById(R.id.TxtDoctor);
        txtAdmin = findViewById(R.id.TxtAdmin);
        Link = findViewById(R.id.link);

        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open website link
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aardexgroup.com/medication-monitoring-system/"));
                startActivity(intent);
            }
        });

        // Set click listeners for images and text
        View.OnClickListener patientListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthenticationActivity.this, PatientLoginActivity.class));
            }
        };

        View.OnClickListener doctorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthenticationActivity.this, DoctorLoginActivity.class));
            }
        };

        View.OnClickListener adminListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthenticationActivity.this, AdminLoginActivity.class));
            }
        };

        // Apply click listeners to both images and text
        imgPatient.setOnClickListener(patientListener);
        txtPatient.setOnClickListener(patientListener);

        imgDoctor.setOnClickListener(doctorListener);
        txtDoctor.setOnClickListener(doctorListener);

        imgAdmin.setOnClickListener(adminListener);
        txtAdmin.setOnClickListener(adminListener);
    }
}