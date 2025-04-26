package com.example.smams;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SelectedAppointmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SelectedAppointmentAdapter adapter;
    private List<DoctorAppointment> selectedAppointments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_appointments);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("             My Appointments");

        recyclerView = findViewById(R.id.recyclerViewSelectedAppointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        selectedAppointments = new ArrayList<>();
        loadSelectedAppointments();

        adapter = new SelectedAppointmentAdapter(this, selectedAppointments);
        recyclerView.setAdapter(adapter);
    }

    private void loadSelectedAppointments() {
        SharedPreferences sharedPreferences = getSharedPreferences("SelectedAppointments", Context.MODE_PRIVATE);
        Set<String> appointmentSet = sharedPreferences.getStringSet("appointments", null);

        if (appointmentSet != null) {
            for (String appointmentString : appointmentSet) {
                String[] parts = appointmentString.split("\\|\\|");
                if (parts.length == 10) {
                    DoctorAppointment appointment = new DoctorAppointment();
                    appointment.setPatientName(parts[0]);
                    appointment.setDoctorName(parts[1]);
                    appointment.setAppointmentDate(parts[2]);
                    appointment.setAppointmentTime(parts[3]);
                    appointment.setReason(parts[4]);
                    appointment.setEmail(parts[5]);
                    appointment.setPhone(parts[6]);
                    appointment.setNextOfKin(parts[7]);
                    appointment.setNextOfKinPhone(parts[8]);
                    appointment.setStatus(parts[9]);
                    selectedAppointments.add(appointment);
                }
            }
        } else {
            Toast.makeText(this, "No Selected Appointments", Toast.LENGTH_SHORT).show();
        }
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
