package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CookiesActivity extends AppCompatActivity {

    TextView cookiesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookies);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("           Cookies");

        cookiesText = findViewById(R.id.cookies_text);
        cookiesText.setText(getCookiesContent());

        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(v -> {
            Toast.makeText(CookiesActivity.this, "Cookies Policy Confirmed", Toast.LENGTH_SHORT).show();
            finish(); // Optional: close the activity after confirming
        });
    }

    private String getCookiesContent() {
        return "Cookies Policy for SMAMS\n\n" +
                "SMAMS (Smart Medication Adherence Monitoring System) uses cookies to enhance your experience within the application.\n\n" +
                "What are Cookies?\n" +
                "Cookies are small data files stored on your device to help improve app performance and personalize your experience.\n\n" +
                "How We Use Cookies:\n" +
                "- To maintain user login sessions.\n" +
                "- To remember your preferred settings.\n" +
                "- To analyze feature usage for improving the system.\n\n" +
                "Your Control:\n" +
                "You can clear app data from device settings to reset all stored information, including cookies.\n\n" +
                "Note:\n" +
                "These cookies do not collect sensitive personal data and are only used within the scope of this academic project.\n\n" +
                "By continuing to use SMAMS, you consent to the use of cookies under this policy.";
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