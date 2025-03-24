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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminLoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");

        returnBack = findViewById(R.id.AdminBack);
        emailEditText = findViewById(R.id.AdminUsername);
        passwordEditText = findViewById(R.id.AdminPassword);
        loginButton = findViewById(R.id.AdminLoginButton);
        forgotPassword = findViewById(R.id.AdminForgotPassword);
        toSignUp = findViewById(R.id.AdminSignup);
        progressBar = new ProgressBar(this);

        loginButton.setOnClickListener(view -> loginAdmin());

        returnBack.setOnClickListener(view -> startActivity(new Intent(AdminLoginActivity.this, AuthenticationActivity.class)));

        toSignUp.setOnClickListener(view -> startActivity(new Intent(AdminLoginActivity.this, AdminSignUp.class)));

        forgotPassword.setOnClickListener(view -> {
            // Handle password reset
            Intent intent = new Intent(AdminLoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void loginAdmin() {
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
                            Intent intent = new Intent(AdminLoginActivity.this, AdminDashboard.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AdminLoginActivity.this, "Access Denied. You are not registered as an Admin.", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AdminLoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AdminLoginActivity.this, "Login Failed. Check credentials!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
