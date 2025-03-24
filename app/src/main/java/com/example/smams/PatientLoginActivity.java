package com.example.smams;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PatientLoginActivity extends AppCompatActivity {


    private ImageView returnBack;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private TextView forgotPassword, toSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_login);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Patients");


        returnBack = findViewById(R.id.PatientBack);
        emailEditText = findViewById(R.id.PatientUsername); 
        passwordEditText = findViewById(R.id.PatientPassword);
        loginButton = findViewById(R.id.PatientLoginButton);
        forgotPassword = findViewById(R.id.PatientForgotPassword);
        progressBar = new ProgressBar(this);
        toSignUp = findViewById(R.id.PatientSignup);

        loginButton.setOnClickListener(view -> loginPatient());

        returnBack.setOnClickListener(view -> startActivity(new Intent(PatientLoginActivity.this, AuthenticationActivity.class)));

        toSignUp.setOnClickListener(view -> startActivity(new Intent(PatientLoginActivity.this, PatientSignUp.class)));

        forgotPassword.setOnClickListener(view -> {
            // Handle password reset
            Intent intent = new Intent(PatientLoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

    }

    private void loginPatient() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Check if user is in Patients node
                String userId = mAuth.getCurrentUser().getUid();
                databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        if (snapshot.exists()) {
                            // Redirect to patient dashboard
                            Intent intent = new Intent(PatientLoginActivity.this, PatientDashboard.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(PatientLoginActivity.this, "Access Denied. You are not registered as a Patient.", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(PatientLoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PatientLoginActivity.this, "Login Failed. Check credentials!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



