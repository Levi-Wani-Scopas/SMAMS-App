package com.example.smams;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddReminderActivity extends AppCompatActivity {

    private EditText etMedicineName, etDosage;
    private Button btnStartDate, btnEndDate, btnAddTime, btnSaveReminder;
    private RecyclerView rvTimes;
    private Spinner spinnerRepeat;
    private String startDate, endDate;
    private List<String> reminderTimes = new ArrayList<>();
    private ReminderTimeAdapter adapter;
    private DatabaseReference databaseReference;
    private ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        // Initialize views
        etMedicineName = findViewById(R.id.et_medicine_name);
        etDosage = findViewById(R.id.et_dosage);
        btnStartDate = findViewById(R.id.btn_start_date);
        btnEndDate = findViewById(R.id.btn_end_date);
        btnAddTime = findViewById(R.id.btn_add_time);
        btnSaveReminder = findViewById(R.id.btn_save_reminder);
        rvTimes = findViewById(R.id.rv_times);
        spinnerRepeat = findViewById(R.id.spinner_repeat);
        backImg = findViewById(R.id.btn_exit);

        backImg.setOnClickListener(view -> startActivity(new Intent(AddReminderActivity.this, PatientDashboard.class)));

        // Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Patients")
                .child("User_ID") // Replace with the actual user ID
                .child("Reminders");

        // Set up the Repeat Options Spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.repeat_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRepeat.setAdapter(spinnerAdapter);

        // Initialize RecyclerView
        adapter = new ReminderTimeAdapter(reminderTimes, position -> {
            reminderTimes.remove(position);
            adapter.notifyDataSetChanged();
        });
        rvTimes.setLayoutManager(new LinearLayoutManager(this));
        rvTimes.setAdapter(adapter);

        // Set up Start Date Picker
        btnStartDate.setOnClickListener(v -> showDatePicker(date -> {
            startDate = date;
            btnStartDate.setText(date);
        }));

        // Set up End Date Picker
        btnEndDate.setOnClickListener(v -> showDatePicker(date -> {
            endDate = date;
            btnEndDate.setText(date);
        }));

        // Set up Add Time Button
        btnAddTime.setOnClickListener(v -> showTimePicker(time -> {
            reminderTimes.add(time);
            adapter.notifyDataSetChanged();
        }));

        // Save Reminder
        btnSaveReminder.setOnClickListener(v -> saveReminder());
    }

    private void showDatePicker(OnDateSelectedListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    listener.onDateSelected(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker(OnTimeSelectedListener listener) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minuteOfHour) -> {
                    String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfHour);
                    listener.onTimeSelected(time);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void saveReminder() {
        String medicineName = etMedicineName.getText().toString().trim();
        String dosage = etDosage.getText().toString().trim();
        String repeatOption = spinnerRepeat.getSelectedItem().toString();

        if (medicineName.isEmpty() || dosage.isEmpty() || startDate == null || endDate == null) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (reminderTimes.isEmpty()) {
            Toast.makeText(this, "Please add at least one time for the reminder", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to Firebase
        String reminderId = databaseReference.push().getKey();
        Reminder reminder = new Reminder(reminderId, medicineName, dosage, startDate, endDate, repeatOption, reminderTimes);
        databaseReference.child(reminderId).setValue(reminder)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Reminder Saved Successfully", Toast.LENGTH_SHORT).show();

                    // Schedule Notifications
                    scheduleNotifications(medicineName, dosage, reminderTimes);

                    finish(); // Close the activity after saving
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to save reminder", Toast.LENGTH_SHORT).show();
                });
    }

    private void scheduleNotifications(String medicineName, String dosage, List<String> times) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        for (String time : times) {
            try {
                Calendar calendar = Calendar.getInstance();
                Date reminderTime = timeFormat.parse(time);

                if (reminderTime != null) {
                    calendar.set(Calendar.HOUR_OF_DAY, reminderTime.getHours());
                    calendar.set(Calendar.MINUTE, reminderTime.getMinutes());

                    Data data = new Data.Builder()
                            .putString("medicineName", medicineName)
                            .putString("dosage", dosage)
                            .build();

                    OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ReminderWorker.class)
                            .setInitialDelay(calendar.getTimeInMillis() - System.currentTimeMillis(), java.util.concurrent.TimeUnit.MILLISECONDS)
                            .setInputData(data)
                            .build();

                    WorkManager.getInstance(this).enqueue(workRequest);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    interface OnDateSelectedListener {
        void onDateSelected(String date);
    }

    interface OnTimeSelectedListener {
        void onTimeSelected(String time);
    }
}
