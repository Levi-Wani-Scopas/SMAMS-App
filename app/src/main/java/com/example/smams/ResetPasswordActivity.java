package com.example.smams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText emailInput;
    private Spinner accountTypeSpinner;
    private Button submitResetRequestButton;
    private TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailInput = findViewById(R.id.email_input);
        accountTypeSpinner = findViewById(R.id.account_type_spinner);
        submitResetRequestButton = findViewById(R.id.submit_reset_request_button);
        cancel = findViewById(R.id.Cancelx);

        cancel.setOnClickListener(view -> startActivity(new Intent(ResetPasswordActivity.this, AuthenticationActivity.class)));


        submitResetRequestButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String accountType = accountTypeSpinner.getSelectedItem().toString();

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create request object
            PasswordResetRequest request = new PasswordResetRequest(email, accountType);

            // Save to Firebase
            FirebaseDatabase.getInstance().getReference("Admin")
                    .child("PasswordResetRequests")
                    .push()
                    .setValue(request)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Reset request submitted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed to submit request", Toast.LENGTH_SHORT).show()
                    );
        });
    }
}