package com.example.smams;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientDashboardFragment extends Fragment {

    CardView card1, card2;
    private ProgressBar dosageProgressBar;
    private TextView progressPercentage, progressTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_dashboard, container, false);

        // Initialize the views
        dosageProgressBar = view.findViewById(R.id.dosageProgressBar);
        progressPercentage = view.findViewById(R.id.progressPercentage);
        progressTitle = view.findViewById(R.id.progressTitle);

        // CardView setup
        View cardView = view.findViewById(R.id.medicineReminderCard);
        View card1 = view.findViewById(R.id.displayReminders);
        View card2 = view.findViewById(R.id.DosageTrack);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DisplayRemindersActivity
                Intent intent = new Intent(getActivity(), DisplayRemindersActivity.class);
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DosageHistoryActivity
                Intent intent = new Intent(getActivity(), DosageHistoryActivity.class);
                startActivity(intent);
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AddReminderActivity
                Intent intent = new Intent(getActivity(), AddReminderActivity.class);
                startActivity(intent);
            }
        });

        // Update dosage progress
        updateDosageProgress();

        return view;
    }

    private void updateDosageProgress() {
        DatabaseReference dosageTrackerRef = FirebaseDatabase.getInstance()
                .getReference("Patients")
                .child("User_ID") // Replace "User_ID" with the dynamic user ID
                .child("DosageProgress");

        dosageTrackerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long totalDoses = 0, takenDoses = 0;

                for (DataSnapshot child : snapshot.getChildren()) {
                    String status = child.child("status").getValue(String.class);
                    if (status != null) {
                        totalDoses++;
                        if (status.equals("Taken")) {
                            takenDoses++;
                        }
                    }
                }

                if (totalDoses > 0) {
                    int progress = (int) ((double) takenDoses / totalDoses * 100);
                    dosageProgressBar.setProgress(progress);
                    progressPercentage.setText(progress + "%");
                } else {
                    dosageProgressBar.setProgress(0);
                    progressPercentage.setText("0%");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressTitle.setText("Failed to load data");
            }
        });
    }
}
