package com.example.smams;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientSignUp extends AppCompatActivity {

    TextView toLogin;

    private EditText patientFullName, patientEmail, patientPassword, patientConfirmPassword;
    private Button patientSignUpButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_sign_up);

        patientFullName = findViewById(R.id.patientFullName);
        patientEmail = findViewById(R.id.patientEmail);
        patientPassword = findViewById(R.id.patientPassword);
        patientConfirmPassword = findViewById(R.id.patientConfirmPassword);
        patientSignUpButton = findViewById(R.id.patientSignUpButton);
        progressBar = findViewById(R.id.progressBar);

        toLogin = findViewById(R.id.patientBackToLogin);

        toLogin.setOnClickListener(view -> startActivity(new Intent(PatientSignUp.this, PatientLoginActivity.class)));


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Patients");

        patientSignUpButton.setOnClickListener(v -> registerPatient());

    }

    private void registerPatient() {
        String fullName = patientFullName.getText().toString().trim();
        String email = patientEmail.getText().toString().trim();
        String password = patientPassword.getText().toString().trim();
        String confirmPassword = patientConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            PatientModel patient = new PatientModel(fullName, email, userId);
                            databaseReference.child(userId).setValue(patient)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            Toast.makeText(this, "Patient Registered Successfully!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(this, PatientLoginActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Database Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
