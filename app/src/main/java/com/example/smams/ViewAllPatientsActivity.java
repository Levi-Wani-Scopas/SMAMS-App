package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewAllPatientsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewPatientsAdapter adapter;
    ArrayList<ViewPatients> patientsList;
    DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_all_patients);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("            Registered Patients");


        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recyclerViewPatients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        patientsList = new ArrayList<>();
        adapter = new ViewPatientsAdapter(patientsList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Patients");

        fetchPatients();
    }

    private void fetchPatients() {

        progressBar.setVisibility(View.VISIBLE);

        databaseReference.get().addOnCompleteListener(task -> {

            progressBar.setVisibility(View.GONE);

            if (task.isSuccessful()) {
                patientsList.clear();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    ViewPatients patient = snapshot.getValue(ViewPatients.class);
                    if (patient != null) {
                        patientsList.add(patient);
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Failed to fetch doctors", Toast.LENGTH_SHORT).show();
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