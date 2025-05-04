package com.example.smams;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ConfirmAppointmentActivity extends AppCompatActivity {

    private TextView textViewAppointmentDetails;
    private Button buttonSendConfirmation;

    private String patientName, doctorName, email, appointmentDate, appointmentTime, reason, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_appointment);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("             Confirmation");


        textViewAppointmentDetails = findViewById(R.id.text_appointment_detail);
        buttonSendConfirmation = findViewById(R.id.button_confirmation);

        // Get appointment details from Intent
        patientName = getIntent().getStringExtra("patientName");
        doctorName = getIntent().getStringExtra("doctorName");
        email = getIntent().getStringExtra("email");
        appointmentDate = getIntent().getStringExtra("appointmentDate");
        appointmentTime = getIntent().getStringExtra("appointmentTime");
        reason = getIntent().getStringExtra("reason");
        status = getIntent().getStringExtra("status");

        // Display the appointment info
        String details = "Patient: " + patientName + "\n"
                + "Doctor: " + doctorName + "\n"
                + "Date: " + appointmentDate + "\n"
                + "Email: " + email + "\n"
                + "Time: " + appointmentTime + "\n"
                + "Reason: " + reason + "\n"
                + "Status: " + status;

        textViewAppointmentDetails.setText(details);

        buttonSendConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String defaultMessage = "Your appointment has been confirmed.\n\n"
                        + "Doctor: " + doctorName + "\n"
                        + "Date: " + appointmentDate + "\n"
                        + "Time: " + appointmentTime + "\n"
                        + "Reason: " + reason + "\n"
                        + "Status: " + status + "\n\n"
                        + "Thank you.";

                // Use dynamic email from intent, not hardcoded one
                sendEmail(email, "Appointment Confirmation", defaultMessage);
            }
        });
    }

    private void sendEmail(String email, String subject, String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email)); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            Toast.makeText(this, "Opening email client...", Toast.LENGTH_SHORT).show();
            finish(); // Close this activity after sending email
        } catch (Exception e) {
            Toast.makeText(this, "No email client found!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Go back when toolbar back button is pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
