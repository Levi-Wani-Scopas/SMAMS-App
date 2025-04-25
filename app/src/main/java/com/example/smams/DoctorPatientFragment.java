package com.example.smams;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DoctorPatientFragment extends Fragment {

    private RecyclerView recyclerView;
    private DoctorAppointmentAdapter adapter;
    private List<DoctorAppointment> appointmentList;
    private DatabaseReference appointmentsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_patient, container, false);

        recyclerView = view.findViewById(R.id.recycler_doctor_appointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appointmentList = new ArrayList<>();
        adapter = new DoctorAppointmentAdapter(getContext(), appointmentList);
        recyclerView.setAdapter(adapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String doctorUID = currentUser.getUid();
            appointmentsRef = FirebaseDatabase.getInstance()
                    .getReference("Patients")
                    .child("User_ID")
                    .child("Appointments");

            appointmentsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    appointmentList.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DoctorAppointment appointment = data.getValue(DoctorAppointment.class);
                        if (appointment != null) {
                            appointmentList.add(appointment);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Failed to load appointments", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
