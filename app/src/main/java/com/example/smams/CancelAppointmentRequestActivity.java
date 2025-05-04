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

import com.google.firebase.database.FirebaseDatabase;

public class CancelAppointmentRequestActivity extends AppCompatActivity {

    private TextView doctorNameText, appointmentDateText, appointmentTimeText, patientNameText, reasonText;
    private EditText reasonInput;
    private Button submitRequestButton;

    private String doctorName, appointmentDate, appointmentTime, patientName, reason;

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
        reasonText = findViewById(R.id.appointment_reason_text);
        appointmentDateText = findViewById(R.id.appointment_date_text);
        appointmentTimeText = findViewById(R.id.appointment_time_text);
        patientNameText = findViewById(R.id.patient_name_text);
        reasonInput = findViewById(R.id.reason_input);
        submitRequestButton = findViewById(R.id.submit_request_button);

        // Get data from intent
        doctorName = getIntent().getStringExtra("doctorName");
        patientName = getIntent().getStringExtra("patientName");
        reason = getIntent().getStringExtra("reason");
        appointmentDate = getIntent().getStringExtra("appointmentDate");
        appointmentTime = getIntent().getStringExtra("appointmentTime");

        // Set data in TextViews
        doctorNameText.setText(doctorName);
        patientNameText.setText(patientName);
        reasonText.setText(reason);
        appointmentDateText.setText(appointmentDate);
        appointmentTimeText.setText(appointmentTime);

        // Handle submit request button click
        submitRequestButton.setOnClickListener(v -> {
            String reason = reasonInput.getText().toString().trim();

            if (reason.isEmpty()) {
                Toast.makeText(this, "Please enter a reason", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create request object
            CancelRequest request = new CancelRequest(doctorName, appointmentDate, appointmentTime, reason, patientName);

            // Save to Firebase under "Admin/AppointmentCancelRequests"
            FirebaseDatabase.getInstance().getReference("Admin")
                    .child("AppointmentCancelRequests")
                    .push()
                    .setValue(request)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Cancellation request sent", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed to send request", Toast.LENGTH_SHORT).show()
                    );
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