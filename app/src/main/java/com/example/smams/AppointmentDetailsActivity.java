package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AppointmentDetailsActivity extends AppCompatActivity {

    private TextView appointmentGuidelinesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("      Appointment Guidelines");
        }

        // Initialize UI element
        appointmentGuidelinesText = findViewById(R.id.appointment_guidelines_text);

        // Set guidelines text
        String guidelines = "📌 **Appointment Guidelines**\n\n" +
                "1️⃣ **Check Your Email**\n" +
                "   - Appointment updates and confirmations will be sent to your registered email.\n" +
                "   - Ensure you check your email regularly for updates.\n\n" +
                "2️⃣ **Rules & Requirements**\n" +
                "   - Ensure that you have a stable internet connection for a smooth video conferencing.\n" +
                "   - Be available at least 30 minutes before your scheduled appointment.\n" +
                "   - Feel free to have a translator with you in case of any language barrier.\n\n" +
                "3️⃣ **Appointment Cancellation Policy**\n" +
                "   - Cancellations should be made at least **24 hours before** the appointment.\n" +
                "   - Late cancellations or no-shows may result in restrictions for future bookings.\n" +
                "   - Cancellation requests should be made via the app’s cancellation request feature.\n\n" +
                "4️⃣ **Rescheduling an Appointment**\n" +
                "   - If you need to reschedule, submit a request through the app.\n" +
                "   - You will receive a confirmation email once the rescheduling request is processed.\n\n" +
                "5️⃣ **Doctor’s Availability**\n" +
                "   - Some doctors may have specific consultation days.\n" +
                "   - If a doctor is unavailable, the system will notify you of alternative options.\n\n" +
                "6️⃣ **Emergency Cases**\n" +
                "   - This appointment system is for **scheduled consultations** only.\n" +
                "   - In case of a medical emergency, visit the nearest hospital or call emergency services.\n\n" +
                "📢 **For any issues regarding your appointment, please contact support through the app.**";

        appointmentGuidelinesText.setText(guidelines);
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
