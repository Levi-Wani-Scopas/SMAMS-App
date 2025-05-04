package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PrivacyActivity extends AppCompatActivity {

    TextView privacyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("            Privacy Policy");

        privacyText = findViewById(R.id.privacy_text);
        privacyText.setText(getPrivacyPolicy());
    }

    private String getPrivacyPolicy() {
        return "Privacy Policy for SMAMS (Smart Medication Adherence Monitoring System)\n\n" +
                "1. **Information Collection**\n" +
                "   SMAMS collects user data such as name, email, medication schedules, and appointment records strictly for healthcare monitoring purposes.\n\n" +
                "2. **Data Use**\n" +
                "   All data collected is used to provide personalized medication reminders and enable doctors to track patient adherence.\n\n" +
                "3. **Data Sharing**\n" +
                "   Your data is only accessible to authorized users (your assigned doctor or admin). SMAMS does not share your information with third parties.\n\n" +
                "4. **Data Security**\n" +
                "   We store all data in Firebase with security rules in place. Access is restricted and monitored.\n\n" +
                "5. **User Control**\n" +
                "   You may request changes to your personal information or revoke your access through the admin panel.\n\n" +
                "6. **Academic Disclaimer**\n" +
                "   This app is part of a final year academic project and not intended for public or commercial use. However, we maintain a high standard of data respect and responsibility.\n\n" +
                "7. **Changes to Policy**\n" +
                "   This policy may be updated. Continued use of SMAMS implies acceptance of the latest version.\n\n" +
                "By using this app, you agree to the terms of this privacy policy.";
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