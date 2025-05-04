package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DosageProgress extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textPatientName, textProgressPercent;
    private DatabaseReference dosageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosage_progress);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("             Dosage Progress");

        progressBar = findViewById(R.id.dosage_progress_bar);
        textPatientName = findViewById(R.id.text_patient_name);
        textProgressPercent = findViewById(R.id.text_progress_percent);

        String patientId = getIntent().getStringExtra("patientId");
        String patientName = getIntent().getStringExtra("patientName");

        textPatientName.setText("Patient: " + patientName);

        dosageRef = FirebaseDatabase.getInstance()
                .getReference("Patients")
                .child("User_ID")  // Use patientId instead of "User_ID"
                .child("Reminders");

        fetchProgressData();
    }

    private void fetchProgressData() {
        dosageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalDoses = 0;
                int takenDoses = 0;

                if (!snapshot.exists()) {
                    // If no reminders found for the patient
                    progressBar.setProgress(0);
                    textProgressPercent.setText("0%");
                    return;
                }

                for (DataSnapshot reminderSnapshot : snapshot.getChildren()) {
                    // Access each reminder
                    DataSnapshot dosageTracker = reminderSnapshot.child("dosageTracker");

                    if (!dosageTracker.exists()) {
                        // Skip if no dosageTracker exists for the reminder
                        continue;
                    }

                    for (DataSnapshot doseSnapshot : dosageTracker.getChildren()) {
                        String status = doseSnapshot.child("status").getValue(String.class);
                        if (status != null) {
                            totalDoses++;
                            if (status.equalsIgnoreCase("Taken")) {
                                takenDoses++;
                            }
                        }
                    }
                }

                if (totalDoses > 0) {
                    int progress = (int) ((double) takenDoses / totalDoses * 100);
                    progressBar.setProgress(progress);
                    textProgressPercent.setText(progress + "% Taken");
                } else {
                    progressBar.setProgress(0);
                    textProgressPercent.setText("No doses recorded");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                textProgressPercent.setText("Error loading data");
            }
        });
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