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

public class AdminSignUp extends AppCompatActivity {

    private EditText adminFullName, adminEmail, adminPassword, adminConfirmPassword;
    private Button adminSignUpButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    TextView toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_sign_up);

        adminFullName = findViewById(R.id.adminFullName);
        adminEmail = findViewById(R.id.adminEmail);
        adminPassword = findViewById(R.id.adminPassword);
        adminConfirmPassword = findViewById(R.id.adminConfirmPassword);
        adminSignUpButton = findViewById(R.id.adminSignUpButton);
        progressBar = findViewById(R.id.progressBar);

        toLogin = findViewById(R.id.adminBackToLogin);

        toLogin.setOnClickListener(view -> startActivity(new Intent(AdminSignUp.this, AdminLoginActivity.class)));

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");

        adminSignUpButton.setOnClickListener(v -> registerAdmin());
    }

    private void registerAdmin() {
        String fullName = adminFullName.getText().toString().trim();
        String email = adminEmail.getText().toString().trim();
        String password = adminPassword.getText().toString().trim();
        String confirmPassword = adminConfirmPassword.getText().toString().trim();

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
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            AdminModel admin = new AdminModel(fullName, email, userId);
                            databaseReference.child(userId).setValue(admin)
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
