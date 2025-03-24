package com.example.smams;

import android.content.Intent;
import android.media.Image;
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

public class DoctorLoginActivity extends AppCompatActivity {

    private ImageView returnBack;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private TextView forgotPassword,toSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_login);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");

        returnBack = findViewById(R.id.DoctorBack);
        emailEditText = findViewById(R.id.DoctorUsername);
        passwordEditText = findViewById(R.id.DoctorPassword);
        loginButton = findViewById(R.id.DoctorLoginButton);
        forgotPassword = findViewById(R.id.DoctorForgotPassword);
        progressBar = new ProgressBar(this);
        toSignUp = findViewById(R.id.DoctorSignup);

        loginButton.setOnClickListener(view -> loginDoctor());


        returnBack.setOnClickListener(view -> startActivity(new Intent(DoctorLoginActivity.this, AuthenticationActivity.class)));

        toSignUp.setOnClickListener(view -> startActivity(new Intent(DoctorLoginActivity.this, DoctorSignUp.class)));

        forgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorLoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });
    }
        private void loginDoctor() {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String userId = mAuth.getCurrentUser().getUid();
                    databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressBar.setVisibility(View.GONE);
                            if (snapshot.exists()) {
                                Intent intent = new Intent(DoctorLoginActivity.this, DoctorDashboard.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(DoctorLoginActivity.this, "Access Denied. You are not registered as a Doctor.", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(DoctorLoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DoctorLoginActivity.this, "Login Failed. Check credentials!", Toast.LENGTH_SHORT).show();
                }
            });

    }
}