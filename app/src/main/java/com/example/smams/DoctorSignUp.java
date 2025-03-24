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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorSignUp extends AppCompatActivity {

    private EditText doctorFullName, doctorEmail, doctorSpecialization, doctorPassword, doctorConfirmPassword;
    private Button doctorSignUpButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    TextView toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_sign_up);

        toLogin = findViewById(R.id.DoctorBackToLogin);

        toLogin.setOnClickListener(view -> startActivity(new Intent(DoctorSignUp.this, DoctorLoginActivity.class)));

        doctorFullName = findViewById(R.id.doctorFullName);
        doctorEmail = findViewById(R.id.doctorEmail);
        doctorSpecialization = findViewById(R.id.doctorSpecialization);
        doctorPassword = findViewById(R.id.doctorPassword);
        doctorConfirmPassword = findViewById(R.id.doctorConfirmPassword);
        doctorSignUpButton = findViewById(R.id.doctorSignUpButton);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");

        doctorSignUpButton.setOnClickListener(v -> registerDoctor());
    }

    private void registerDoctor() {
        String fullName = doctorFullName.getText().toString().trim();
        String email = doctorEmail.getText().toString().trim();
        String specialization = doctorSpecialization.getText().toString().trim();
        String password = doctorPassword.getText().toString().trim();
        String confirmPassword = doctorConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        DoctorModel doctor = new DoctorModel(fullName, email, userId, specialization);
                        databaseReference.child(userId).setValue(doctor)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        Toast.makeText(this, "Doctor Registered Successfully!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(this, DoctorLoginActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(this, "Database Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
