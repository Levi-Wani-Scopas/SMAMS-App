package com.example.smams;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AppointmentFormActivity extends AppCompatActivity {

    private TextView doctorNameTextView, selectedDateTextView, selectedTimeTextView;
    private TextInputEditText patientNameInput, phoneNumberInput, emailInput, reasonInput, nextOfKinNameInput, nextOfKinPhoneInput;
    private Button datePickerButton, timePickerButton, submitButton;

    private DatabaseReference databaseReference;
    private String selectedDoctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_form);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("            Appointment Form");
        }

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Appointments");

        // Get doctor's name from intent
        selectedDoctorName = getIntent().getStringExtra("doctor_name");

        // Initialize Views
        doctorNameTextView = findViewById(R.id.doctor_name_value);
        selectedDateTextView = findViewById(R.id.selected_date);
        selectedTimeTextView = findViewById(R.id.selected_time);
        patientNameInput = findViewById(R.id.patient_name_input);
        phoneNumberInput = findViewById(R.id.phone_number_input);
        emailInput = findViewById(R.id.email_input);
        reasonInput = findViewById(R.id.reason_input);
        nextOfKinNameInput = findViewById(R.id.next_of_kin_name_input);
        nextOfKinPhoneInput = findViewById(R.id.next_of_kin_phone_input);
        datePickerButton = findViewById(R.id.date_picker_button);
        timePickerButton = findViewById(R.id.time_picker_button);
        submitButton = findViewById(R.id.submit_appointment_button);

        // Set doctor's name
        if (selectedDoctorName != null) {
            doctorNameTextView.setText(selectedDoctorName);
        }

        // Date Picker
        datePickerButton.setOnClickListener(v -> showDatePicker());

        // Time Picker
        timePickerButton.setOnClickListener(v -> showTimePicker());

        // Submit Button Click
        submitButton.setOnClickListener(v -> submitAppointment());
    }

    // Show Date Picker Dialog
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            selectedDateTextView.setText(selectedDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    // Show Time Picker Dialog (12-hour format with AM/PM)
    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            String amPm;
            int displayHour;

            if (selectedHour >= 12) {
                amPm = "PM";
                displayHour = (selectedHour == 12) ? 12 : selectedHour - 12;
            } else {
                amPm = "AM";
                displayHour = (selectedHour == 0) ? 12 : selectedHour;
            }

            String selectedTime = String.format("%02d:%02d %s", displayHour, selectedMinute, amPm);
            selectedTimeTextView.setText(selectedTime);
        }, hour, minute, false); // `false` forces 12-hour format
        timePickerDialog.show();
    }
    // Submit Appointment to Firebase
    private void submitAppointment() {
        String patientName = patientNameInput.getText().toString().trim();
        String phoneNumber = phoneNumberInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String appointmentDate = selectedDateTextView.getText().toString();
        String appointmentTime = selectedTimeTextView.getText().toString();
        String reason = reasonInput.getText().toString().trim();
        String nextOfKinName = nextOfKinNameInput.getText().toString().trim();
        String nextOfKinPhone = nextOfKinPhoneInput.getText().toString().trim();

        if (patientName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || appointmentDate.equals("No date selected") ||
                appointmentTime.equals("No time selected") || reason.isEmpty() || nextOfKinName.isEmpty() || nextOfKinPhone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a unique ID for each appointment
        String appointmentId = databaseReference.push().getKey();

        // Store appointment data in a map
        Map<String, Object> appointmentData = new HashMap<>();
        appointmentData.put("doctorName", selectedDoctorName);
        appointmentData.put("patientName", patientName);
        appointmentData.put("phoneNumber", phoneNumber);
        appointmentData.put("email", email);
        appointmentData.put("appointmentDate", appointmentDate);
        appointmentData.put("appointmentTime", appointmentTime);
        appointmentData.put("reason", reason);
        appointmentData.put("nextOfKinName", nextOfKinName);
        appointmentData.put("nextOfKinPhone", nextOfKinPhone);

        // Save to Firebase
        if (appointmentId != null) {
            databaseReference.child(appointmentId).setValue(appointmentData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Appointment submitted successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity after submission
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to submit appointment!", Toast.LENGTH_SHORT).show());
        }
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
