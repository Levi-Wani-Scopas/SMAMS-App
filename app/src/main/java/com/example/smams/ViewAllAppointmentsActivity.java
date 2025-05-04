package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAllAppointmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ViewAppointmentAdapter adapter;
    private List<ViewAppointment> appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_appointments);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("            Appointments");

        recyclerView = findViewById(R.id.recyclerViewAppointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appointmentList = new ArrayList<>();
        adapter = new ViewAppointmentAdapter(appointmentList);
        recyclerView.setAdapter(adapter);

        fetchAppointments();
    }

    private void fetchAppointments() {

        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance()
                .getReference("Patients")
                .child("User_ID")
                .child("Appointments");

        appointmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointmentList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot appointmentSnapshot : snapshot.getChildren()) {
                        ViewAppointment appointment = appointmentSnapshot.getValue(ViewAppointment.class);
                        if (appointment != null) {
                            appointmentList.add(appointment);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ViewAllAppointmentsActivity.this, "No appointments found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewAllAppointmentsActivity.this, "Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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