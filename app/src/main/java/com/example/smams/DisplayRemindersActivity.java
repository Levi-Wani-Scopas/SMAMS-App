package com.example.smams;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayRemindersActivity extends AppCompatActivity {

    private RecyclerView rvReminders;
    private Button btnClearAll;
    private ImageView backImg;
    private SavedReminderAdapter reminderAdapter;
    private List<Reminder> reminderList;
    private DatabaseReference databaseReference;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_reminders);

        // Initialize views
        rvReminders = findViewById(R.id.rv_reminders);
        tvTitle = findViewById(R.id.tv_title);
        btnClearAll = findViewById(R.id.btn_clear_all);
        backImg = findViewById(R.id.btn_exit);


        backImg.setOnClickListener(view -> startActivity(new Intent(DisplayRemindersActivity.this, PatientDashboard.class)));


        // Set up RecyclerView
        rvReminders.setLayoutManager(new LinearLayoutManager(this));
        reminderList = new ArrayList<>();
        reminderAdapter = new SavedReminderAdapter(reminderList);
        rvReminders.setAdapter(reminderAdapter);

        // Firebase Reference
        String userId = "User_ID"; // Replace with actual user ID
        databaseReference = FirebaseDatabase.getInstance().getReference("Patients")
                .child(userId)
                .child("Reminders");

        // Fetch reminders
        fetchReminders();

        // Clear all reminders when button is clicked
        btnClearAll.setOnClickListener(v -> clearAllReminders());
    }

    private void fetchReminders() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reminderList.clear();
                for (DataSnapshot reminderSnapshot : snapshot.getChildren()) {
                    Reminder reminder = reminderSnapshot.getValue(Reminder.class);
                    if (reminder != null) {
                        reminderList.add(reminder);
                    }
                }
                reminderAdapter.notifyDataSetChanged();

                if (reminderList.isEmpty()) {
                    tvTitle.setText("No Reminders Found");
                } else {
                    tvTitle.setText("Saved Reminders");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void clearAllReminders() {
        // Show a confirmation dialog
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Clear All Reminders")
                .setMessage("Are you sure you want to clear all saved reminders? This action cannot be undone.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // User confirmed, proceed to clear all reminders
                    databaseReference.removeValue()
                            .addOnSuccessListener(aVoid -> {
                                reminderList.clear();
                                reminderAdapter.notifyDataSetChanged();
                                Toast.makeText(DisplayRemindersActivity.this, "All reminders cleared.", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(DisplayRemindersActivity.this, "Failed to clear reminders.", Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // User canceled, dismiss the dialog
                    dialog.dismiss();
                })
                .create()
                .show();
    }
}
