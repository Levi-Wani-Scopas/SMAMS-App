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

import java.util.ArrayList;

public class ViewAllDoctorsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewDoctorAdapter adapter;
    ArrayList<ViewDoctor> doctorList;
    DatabaseReference databaseReference;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_doctors);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        getSupportActionBar().setTitle("            Registered Doctors");


        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recyclerViewDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        doctorList = new ArrayList<>();
        adapter = new ViewDoctorAdapter(doctorList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");

        fetchDoctors();
    }

    private void fetchDoctors() {

        progressBar.setVisibility(View.VISIBLE);

        databaseReference.get().addOnCompleteListener(task -> {

            progressBar.setVisibility(View.GONE);

            if (task.isSuccessful()) {
                doctorList.clear();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    ViewDoctor doctor = snapshot.getValue(ViewDoctor.class);
                    if (doctor != null) {
                        doctorList.add(doctor);
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