package com.example.smams;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationActivity extends AppCompatActivity implements NetworkChangeReceiver.NetworkChangeListener {

    private NetworkChangeReceiver networkChangeReceiver;

    ImageView imgPatient, imgDoctor, imgAdmin;
    TextView txtPatient, txtDoctor, txtAdmin, Link;

    private boolean isInternetConnected = false; // Tracks internet connection status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);

        networkChangeReceiver = new NetworkChangeReceiver(this);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);

        imgPatient = findViewById(R.id.ImagePatients);
        imgDoctor = findViewById(R.id.ImageDoctors);
        imgAdmin = findViewById(R.id.ImageAdmin);

        txtPatient = findViewById(R.id.Txtpatient);
        txtDoctor = findViewById(R.id.TxtDoctor);
        txtAdmin = findViewById(R.id.TxtAdmin);
        Link = findViewById(R.id.link);

        // Set click listener for the link
        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aardexgroup.com/medication-monitoring-system/"));
                startActivity(intent);
            }
        });

        // Set up click listeners for images and text
        View.OnClickListener patientListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected) {
                    startActivity(new Intent(AuthenticationActivity.this, PatientLoginActivity.class));
                } else {
                    showNoInternetSnackbar();
                }
            }
        };

        View.OnClickListener doctorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected) {
                    startActivity(new Intent(AuthenticationActivity.this, DoctorLoginActivity.class));
                } else {
                    showNoInternetSnackbar();
                }
            }
        };

        View.OnClickListener adminListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected) {
                    startActivity(new Intent(AuthenticationActivity.this, AdminLoginActivity.class));
                } else {
                    showNoInternetSnackbar();
                }
            }
        };

        // Apply click listeners to both images and text
        imgPatient.setOnClickListener(patientListener);
        txtPatient.setOnClickListener(patientListener);

        imgDoctor.setOnClickListener(doctorListener);
        txtDoctor.setOnClickListener(doctorListener);

        imgAdmin.setOnClickListener(adminListener);
        txtAdmin.setOnClickListener(adminListener);

        // Initial check for internet connection
        checkInternetConnection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        isInternetConnected = isConnected;

        if (!isConnected) {
            // Disable interactions if no internet
            showNoInternetSnackbar();
            showEnableInternetDialog();
        }
    }

    private void showNoInternetSnackbar() {
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, "No Internet Connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", v -> checkInternetConnection());
        snackbar.show();
    }

    private void showEnableInternetDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setMessage("Please enable WiFi or Mobile Data.")
                .setPositiveButton("Enable", (dialog, which) -> startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void checkInternetConnection() {
        if (!NetworkUtil.isInternetAvailable(this)) {
            isInternetConnected = false;
            showNoInternetSnackbar();
            showEnableInternetDialog();
        } else {
            isInternetConnected = true;
        }
    }

    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // User is already logged in, go to MainActivity
            startActivity(new Intent(AuthenticationActivity.this, PatientDashboard.class));
            finish(); // Prevent user from going back to login screen
        }
    }
}
