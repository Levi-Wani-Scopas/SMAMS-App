package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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

public class MyPatientsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPatients;
    private PatientAdapter patientAdapter;
    private List<Patient> patientList;
    private ProgressBar progressBar;

    private DatabaseReference patientsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("             My Patients");

        recyclerViewPatients = findViewById(R.id.recycler_view_patients);
        progressBar = findViewById(R.id.progress_bar);

        recyclerViewPatients.setLayoutManager(new LinearLayoutManager(this));
        patientList = new ArrayList<>();
        patientAdapter = new PatientAdapter(this, patientList);
        recyclerViewPatients.setAdapter(patientAdapter);

        // Initialize Firebase reference
        patientsRef = FirebaseDatabase.getInstance().getReference("Patients");

        loadPatients();
    }

    private void loadPatients() {
        progressBar.setVisibility(View.VISIBLE);

        patientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot patientSnap : snapshot.getChildren()) {
                        Patient patient = patientSnap.getValue(Patient.class);
                        if (patient != null) {
                            patientList.add(patient);
                        }
                    }
                    patientAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MyPatientsActivity.this, "No patients found.", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MyPatientsActivity.this, "Failed to load patients.", Toast.LENGTH_SHORT).show();
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
