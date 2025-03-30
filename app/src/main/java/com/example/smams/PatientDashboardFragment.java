package com.example.smams;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientDashboardFragment extends Fragment {


    CardView card1, card2;
    private ProgressBar dosageProgressBar;
    private TextView progressPercentage, progressTitle;
    Button selectButton1, selectButton2, selectButton3, selectButton4, selectButton5 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_dashboard, container, false);

        // Initialize the views
        dosageProgressBar = view.findViewById(R.id.dosageProgressBar);
        progressPercentage = view.findViewById(R.id.progressPercentage);
        progressTitle = view.findViewById(R.id.progressTitle);

        Button selectButton1 = view.findViewById(R.id.select_button1);
        Button selectButton2 = view.findViewById(R.id.select_button2);
        Button selectButton3 = view.findViewById(R.id.select_button3);
        Button selectButton4 = view.findViewById(R.id.select_button4);
        Button selectButton5 = view.findViewById(R.id.select_button5);

        // On button click, pass the doctor details
        selectButton1.setOnClickListener(v -> navigateToDoctorDetails(
                "Dr. Levi Wani Scopas",
                "General Practitioner",
                "leviscopas@gmail.com",
                "Dr. Levi is a dedicated General Practitioner with years of experience providing comprehensive and compassionate healthcare. Skilled in preventive care and chronic disease management, he is committed to empowering patients to achieve optimal health. Known for his patient-centered approach, Dr. Levi ensures every individual receives personalized and thorough care.",
                "Monday - Friday: 9 AM to 5 PM",
                R.drawable.levi
        ));

        selectButton2.setOnClickListener(v -> navigateToDoctorDetails(
                "Dr. Rushdy James",
                "Cardiologist",
                "rushdyjames@gmail.com",
                "Dr. Rushdy is an experienced cardiologist specializing in the diagnosis and treatment of heart conditions. With a patient-focused approach, he is dedicated to providing advanced cardiac care, including preventive strategies and personalized treatment plans, helping patients achieve optimal heart health.",
                "Monday - Thursday: 9 AM to 4 PM",
                R.drawable.rushdy
        ));

        selectButton3.setOnClickListener(v -> navigateToDoctorDetails(
                "Dr. Modong Rachael",
                "Orthopedic",
                "modongracheal@gmail.com",
                "Dr. Rachael is a skilled orthopedic doctor specializing in the diagnosis and treatment of musculoskeletal conditions. Dedicated to restoring mobility and improving quality of life, she provides personalized care for patients, from injury management to advanced orthopedic procedures.",
                "Monday - Friday: 9 AM to 3 PM",
                R.drawable.racheal
        ));

        selectButton4.setOnClickListener(v -> navigateToDoctorDetails(
                "Dr. Arinda Bridget",
                "ENT",
                "arindabridget@gmail.com",
                "Dr. Arinda is a dedicated ENT specialist with expertise in diagnosing and treating ear, nose, and throat conditions. Passionate about providing patient-focused care, she offers personalized solutions to improve hearing, breathing, and overall ENT health for patients of all ages.",
                "Tuesday - Friday: 11 AM to 3 PM",
                R.drawable.bridget
        ));

        selectButton5.setOnClickListener(v -> navigateToDoctorDetails(
                "Dr. Manasse William",
                "Psychiatrist",
                "manassewilliam@gmail.com",
                "Dr. Manasse is a respected psychiatrist with decades of experience in clinical practice. His work spans a wide range of mental health conditions, focusing particularly on innovative therapy approaches. Known for his compassionate demeanor, Dr. Manasse has significantly contributed to advancements in patient care and understanding.",
                "Monday - Friday: 12 PM to 5 PM",
                R.drawable.manasse
        ));

    /////////////

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

        // Fetch progress data
        fetchProgressData();

        return view;
    }

    private void fetchProgressData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference dosageTrackerRef = FirebaseDatabase.getInstance()
                .getReference("Patients")
                .child("User_ID")
                .child("Reminders");

        Log.d("ProgressBarDebug", "Database path: Patients/" + userId + "/Reminders");

        dosageTrackerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ProgressBarDebug", "Snapshot exists: " + snapshot.exists());

                final long[] totalDoses = {0};
                final long[] takenDoses = {0};

                if (!snapshot.exists()) {
                    Log.d("ProgressBarDebug", "No reminders found for user: " + userId);
                    dosageProgressBar.setProgress(0);
                    progressPercentage.setText("0%");
                    return;
                }

                for (DataSnapshot reminderSnapshot : snapshot.getChildren()) {
                    Log.d("ProgressBarDebug", "Processing Reminder ID: " + reminderSnapshot.getKey());
                    DataSnapshot dosageTracker = reminderSnapshot.child("dosageTracker");

                    if (!dosageTracker.exists()) {
                        Log.d("ProgressBarDebug", "No dosageTracker node found for Reminder ID: " + reminderSnapshot.getKey());
                        continue;
                    }

                    for (DataSnapshot doseSnapshot : dosageTracker.getChildren()) {
                        String status = doseSnapshot.child("status").getValue(String.class);
                        Log.d("ProgressBarDebug", "Dose ID: " + doseSnapshot.getKey() + ", Status: " + status);

                        if (status != null) {
                            totalDoses[0]++;
                            if (status.equalsIgnoreCase("Taken")) {
                                takenDoses[0]++;
                            }
                        }
                    }
                }

                Log.d("ProgressBarDebug", "Total doses: " + totalDoses[0] + ", Taken doses: " + takenDoses[0]);

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (totalDoses[0] > 0) {
                            int progress = (int) ((double) takenDoses[0] / totalDoses[0] * 100);
                            dosageProgressBar.setProgress(progress);
                            progressPercentage.setText(progress + "%");
                        } else {
                            dosageProgressBar.setProgress(0);
                            progressPercentage.setText("0%");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProgressBarDebug", "Failed to fetch data: " + error.getMessage());
                progressPercentage.setText("Error loading data");
            }
        });
    }
    private void navigateToDoctorDetails(String name, String specialization, String email, String biography, String workingHours, int imageResId) {
        PatientDoctorFragment doctorFragment = new PatientDoctorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("doctor_name", name);
        bundle.putString("doctor_specialization", specialization);
        bundle.putString("doctor_email",email);
        bundle.putString("doctor_biography", biography);
        bundle.putString("doctor_working_hours", workingHours);
        bundle.putInt("doctor_image", imageResId);
        doctorFragment.setArguments(bundle);

        // Load the PatientDoctorsFragment with data
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, doctorFragment)
                .addToBackStack(null) // Optional, adds to back stack for navigating back
                .commit();
    }

}