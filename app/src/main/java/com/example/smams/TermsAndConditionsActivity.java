package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TermsAndConditionsActivity extends AppCompatActivity {

    TextView termsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("           Terms and Conditions");

        termsText = findViewById(R.id.terms_text);
        termsText.setText(getTermsContent());

        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(v -> {
            Toast.makeText(TermsAndConditionsActivity.this, "Terms and Conditions Confirmed", Toast.LENGTH_SHORT).show();
            finish(); // Optional: close the activity after confirming
        });
    }

    private String getTermsContent() {
        return "Welcome to SMAMS (Smart Medication Adherence Monitoring System). By using this application, you agree to the following terms and conditions:\n\n" +
                "1. **Purpose**\n" +
                "   SMAMS is designed to help patients manage medication schedules and enable healthcare providers to monitor patient adherence.\n\n" +
                "2. **User Responsibility**\n" +
                "   Users must ensure that the information they provide is accurate. The app is a supportive tool and does not replace professional medical advice.\n\n" +
                "3. **Privacy and Data**\n" +
                "   All personal and medical data collected in this app is stored securely and is accessible only to authorized users (patients, doctors, and admin).\n\n" +
                "4. **Usage**\n" +
                "   The app should be used for health-related purposes only. Any misuse or unauthorized access is strictly prohibited.\n\n" +
                "5. **Notifications**\n" +
                "   The app provides medication reminders via notifications. Users are responsible for ensuring these are enabled on their device.\n\n" +
                "6. **Limitation of Liability**\n" +
                "   This app is a prototype for academic use. The developers are not liable for any health consequences resulting from its use.\n\n" +
                "7. **Updates and Changes**\n" +
                "   These terms may be updated. Continued use of the app means acceptance of any revised terms.\n\n" +
                "By using SMAMS, you confirm that you understand and agree to these terms.";
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