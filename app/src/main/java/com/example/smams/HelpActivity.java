package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HelpActivity extends AppCompatActivity {

    TextView helpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("             Help and Support");

        helpText = findViewById(R.id.help_text);
        helpText.setText(getHelpContent());
    }

    private String getHelpContent() {
        return "Need help with SMAMS? Here's how to get started:\n\n" +
                "1. **Dashboard Navigation**\n" +
                "   - Patients can access reminders, dosage trackers, schedules, and doctors from their dashboard.\n" +
                "   - Doctors can track patients, view appointments, and generate medical reports.\n\n" +
                "2. **Setting Reminders**\n" +
                "   - Go to 'Add Reminder' to input your medication schedule.\n" +
                "   - Notifications will alert you at the right time.\n\n" +
                "3. **Tracking Dosage**\n" +
                "   - After taking or missing a dose, use the action buttons to update your tracker.\n\n" +
                "4. **Booking Appointments**\n" +
                "   - Select a doctor and fill in the appointment form with required details.\n\n" +
                "5. **For Admins**\n" +
                "   - The admin panel lets you view and manage users, appointments, and reset requests.\n\n" +
                "6. **Still Stuck?**\n" +
                "   - Contact your assigned doctor or report issues via email.\n\n" +
                "Remember: This app is part of a final year project. For emergencies, consult real medical services.";
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