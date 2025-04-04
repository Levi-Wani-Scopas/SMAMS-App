package com.example.smams;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientSelectedDoctorFragment extends Fragment {
    private RecyclerView recyclerView;
    private AppointmentAdapter appointmentAdapter;
    private List<Appointment> appointmentList;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private TextView noAppointmentsText;
    private Button deleteAllAppointmentsButton; // Added delete button

    public PatientSelectedDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_selected_doctor, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_appointments);
        progressBar = view.findViewById(R.id.progress_bar);
        noAppointmentsText = view.findViewById(R.id.no_appointments_text);
        deleteAllAppointmentsButton = view.findViewById(R.id.delete_all_appointments_button); // Initialize button

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appointmentList = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(getContext(), appointmentList);
        recyclerView.setAdapter(appointmentAdapter);

        auth = FirebaseAuth.getInstance();
        loadAppointments();

        // Handle Delete All Appointments button click
        deleteAllAppointmentsButton.setOnClickListener(v -> confirmDeleteAllAppointments());

        return view;
    }

    private void loadAppointments() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            Log.e("FirebaseData", "User not logged in");
            progressBar.setVisibility(View.GONE);
            noAppointmentsText.setVisibility(View.VISIBLE);
            return;
        }

        String userId = user.getUid(); // Get current user's ID
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Patients")
                .child("User_ID")
                .child("Appointments");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointmentList.clear();
                Log.d("FirebaseData", "Snapshot exists: " + snapshot.exists()); // Debugging

                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Appointment appointment = data.getValue(Appointment.class);
                        if (appointment != null) {
                            appointmentList.add(appointment);
                            Log.d("FirebaseData", "Fetched Appointment: " + appointment.getDoctorName());
                        }
                    }
                    appointmentAdapter.notifyDataSetChanged();
                    noAppointmentsText.setVisibility(appointmentList.isEmpty() ? View.VISIBLE : View.GONE);
                } else {
                    noAppointmentsText.setVisibility(View.VISIBLE);
                    Log.d("FirebaseData", "No appointments found.");
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Data retrieval failed: " + error.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    // Show confirmation dialog before deleting all appointments
    private void confirmDeleteAllAppointments() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete All Appointments")
                .setMessage("Are you sure you want to delete all appointments? This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> deleteAllAppointments())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Delete all appointments from Firebase
    private void deleteAllAppointments() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }


        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Patients")
                .child("User_ID")
                .child("Appointments");

        databaseReference.removeValue()
                .addOnSuccessListener(aVoid -> {
                    appointmentList.clear();
                    appointmentAdapter.notifyDataSetChanged();
                    noAppointmentsText.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "All appointments deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Failed to delete appointments", Toast.LENGTH_SHORT).show()
                );
    }
}
