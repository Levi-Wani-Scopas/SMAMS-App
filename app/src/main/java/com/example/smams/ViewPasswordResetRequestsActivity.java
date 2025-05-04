package com.example.smams;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ViewPasswordResetRequestsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PasswordResetRequestAdapter adapter;
    private List<PasswordResetRequest> requestList;
    private DatabaseReference resetRequestRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_password_reset_requests);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("      Password Reset Requests");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.reset_request_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestList = new ArrayList<>();
        adapter = new PasswordResetRequestAdapter(requestList);
        recyclerView.setAdapter(adapter);

        resetRequestRef = FirebaseDatabase.getInstance().getReference("Admin")
                .child("PasswordResetRequests");

        fetchResetRequests();
    }

    private void fetchResetRequests() {
        resetRequestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    PasswordResetRequest request = snap.getValue(PasswordResetRequest.class);
                    if (request != null) {
                        requestList.add(request);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewPasswordResetRequestsActivity.this, "Failed to fetch requests", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}