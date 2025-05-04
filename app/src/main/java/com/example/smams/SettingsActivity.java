package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    Switch notificationsSwitch;
    TextView languageOption, accessibilityOption, moreSettingsOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("                  Settings");

        notificationsSwitch = findViewById(R.id.switch_notifications);
        languageOption = findViewById(R.id.language_option);
        accessibilityOption = findViewById(R.id.accessibility_option);
        moreSettingsOption = findViewById(R.id.more_settings_option);

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, isChecked ? "Notifications Enabled" : "Notifications Disabled", Toast.LENGTH_SHORT).show();
        });

        languageOption.setOnClickListener(v ->
                Toast.makeText(this, "Language settings coming soon", Toast.LENGTH_SHORT).show());

        accessibilityOption.setOnClickListener(v ->
                Toast.makeText(this, "Accessibility settings coming soon", Toast.LENGTH_SHORT).show());

        moreSettingsOption.setOnClickListener(v ->
                Toast.makeText(this, "More settings coming soon", Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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