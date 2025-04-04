package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CancelAppointmentRequestActivity extends AppCompatActivity {

    private TextView doctorNameText, appointmentDateText, appointmentTimeText;
    private EditText reasonInput;
    private Button submitRequestButton;

    private String doctorName, appointmentDate, appointmentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment_request);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("      Appointment Cancellation");
        }

        // Initialize UI elements
        doctorNameText = findViewById(R.id.doctor_name_text);
        appointmentDateText = findViewById(R.id.appointment_date_text);
        appointmentTimeText = findViewById(R.id.appointment_time_text);
        reasonInput = findViewById(R.id.reason_input);
        submitRequestButton = findViewById(R.id.submit_request_button);

        // Get data from intent
        doctorName = getIntent().getStringExtra("doctorName");
        appointmentDate = getIntent().getStringExtra("appointmentDate");
        appointmentTime = getIntent().getStringExtra("appointmentTime");

        // Set data in TextViews
        doctorNameText.setText(doctorName);
        appointmentDateText.setText(appointmentDate);
        appointmentTimeText.setText(appointmentTime);

        // Handle submit request button click
        submitRequestButton.setOnClickListener(v -> {
            Toast.makeText(this, "Cancellation request sent", Toast.LENGTH_SHORT).show();
            finish(); // Close activity
        });
    }

    // Handle toolbar back button click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
