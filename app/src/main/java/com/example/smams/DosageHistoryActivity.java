package com.example.smams;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class DosageHistoryActivity extends AppCompatActivity {

    private RecyclerView rvDosageHistory;
    private TextView tvSummary;
    private ImageView backImg;
    private DosageHistoryAdapter adapter;
    private List<DosageHistory> dosageHistoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosage_history);

        rvDosageHistory = findViewById(R.id.rv_dosage_history);
        tvSummary = findViewById(R.id.tv_summary);
        backImg = findViewById(R.id.btn_exit);


        backImg.setOnClickListener(view -> startActivity(new Intent(DosageHistoryActivity.this, PatientDashboard.class)));


        adapter = new DosageHistoryAdapter(dosageHistoryList);
        rvDosageHistory.setLayoutManager(new LinearLayoutManager(this));
        rvDosageHistory.setAdapter(adapter);

        fetchDosageHistory();
    }

    private void fetchDosageHistory() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Patients")
                .child("User_ID") // Replace with actual user ID
                .child("Reminders");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int takenCount = 0;
                int totalCount = 0;

                dosageHistoryList.clear();

                for (DataSnapshot reminderSnapshot : snapshot.getChildren()) {
                    String medicineName = reminderSnapshot.child("medicineName").getValue(String.class);
                    String dosage = reminderSnapshot.child("dosage").getValue(String.class);

                    DataSnapshot trackerSnapshot = reminderSnapshot.child("dosageTracker");
                    for (DataSnapshot tracker : trackerSnapshot.getChildren()) {
                        String status = tracker.child("status").getValue(String.class);
                        long timestamp = tracker.child("timestamp").getValue(Long.class);

                        dosageHistoryList.add(new DosageHistory(medicineName, dosage, status, timestamp));

                        if ("Taken".equalsIgnoreCase(status)) {
                            takenCount++;
                        }
                        totalCount++;
                    }
                }

                adapter.notifyDataSetChanged();

                String summary = String.format("Taken: %d, Missed: %d", takenCount, totalCount - takenCount);
                tvSummary.setText(summary);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DosageHistoryActivity", "Error fetching data: " + error.getMessage());
            }
        });
    }
}
