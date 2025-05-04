package com.example.smams;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MedicalReportActivity extends AppCompatActivity {

    EditText etPatientName, etDoctorName, etDiagnosis, etDosage, etNextReviewDate,
            etAdditionalNotes, etReportDate;
    Button btnGenerateReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_report);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("             Medical Report");


        etPatientName = findViewById(R.id.PatientName);
        etDoctorName = findViewById(R.id.DoctorName);
        etDiagnosis = findViewById(R.id.Diagnosis);
        etDosage = findViewById(R.id.Dosage);
        etNextReviewDate = findViewById(R.id.NextReviewDate);
        etAdditionalNotes = findViewById(R.id.AdditionalNotes);
        etReportDate = findViewById(R.id.ReportDate);
        btnGenerateReport = findViewById(R.id.btnGenerateReport);

        // Auto-fill today's date
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        etReportDate.setText(currentDate);

        btnGenerateReport.setOnClickListener(v -> {
            String patientName = etPatientName.getText().toString().trim();
            String doctorName = etDoctorName.getText().toString().trim();
            String diagnosis = etDiagnosis.getText().toString().trim();
            String dosage = etDosage.getText().toString().trim();
            String nextReview = etNextReviewDate.getText().toString().trim();
            String notes = etAdditionalNotes.getText().toString().trim();
            String reportDate = etReportDate.getText().toString().trim();

            if (patientName.isEmpty() || doctorName.isEmpty()) {
                Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String emailBody = "Medical Report\n\n\n"
                    + "Patient Name: " + patientName + "\n\n"
                    + "Doctor Name: " + doctorName + "\n\n"
                    + "Date of Report: " + reportDate + "\n\n"
                    + "Diagnosis: " + diagnosis + "\n\n"
                    + "Prescribed Dosage: " + dosage + "\n\n"
                    + "Next Review Date: " + nextReview + "\n\n"
                    + "Additional Notes: " + notes;

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Medical Report for " + patientName);
            emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

            try {
                startActivity(Intent.createChooser(emailIntent, "Send Medical Report via Email"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT).show();
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