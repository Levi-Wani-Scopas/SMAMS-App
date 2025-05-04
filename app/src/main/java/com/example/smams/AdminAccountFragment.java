package com.example.smams;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class AdminAccountFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImage;
    private EditText editName, editEmail, editPhone, editBloodGroup, editKinName;
    private RadioGroup genderGroup;
    private RadioButton radioMale, radioFemale;
    private Button btnEditProfile, btnSaveChanges, btnChangeProfileImage, btnLogout, btnDob;

    private FirebaseAuth auth;
    private DatabaseReference userRef;
    private Uri imageUri;

    private String selectedDob = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_account, container, false);

        // Initialize UI elements
        profileImage = view.findViewById(R.id.profile_image);
        editName = view.findViewById(R.id.edit_name);
        editEmail = view.findViewById(R.id.edit_email);
        editPhone = view.findViewById(R.id.edit_phone);
        editBloodGroup = view.findViewById(R.id.edit_blood_group);
        editKinName = view.findViewById(R.id.edit_kin_name);
        genderGroup = view.findViewById(R.id.gender_group);
        radioMale = view.findViewById(R.id.radio_male);
        radioFemale = view.findViewById(R.id.radio_female);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnSaveChanges = view.findViewById(R.id.btn_save_changes);
        btnChangeProfileImage = view.findViewById(R.id.change_profile_image);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnDob = view.findViewById(R.id.btn_dob);

        // Firebase setup
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            userRef = FirebaseDatabase.getInstance().getReference("Admin").child(userId);
            loadUserProfile();
        }

        // Click Listeners
        btnEditProfile.setOnClickListener(v -> enableEditing(true));
        btnSaveChanges.setOnClickListener(v -> saveProfileChanges());
        btnLogout.setOnClickListener(v -> logoutUser());
        profileImage.setOnClickListener(v -> selectImage());

        btnDob.setOnClickListener(v -> openDatePicker());

        return view;
    }

    private void enableEditing(boolean enable) {
        editName.setEnabled(enable);
        editPhone.setEnabled(enable);
        editBloodGroup.setEnabled(enable);
        editEmail.setEnabled(enable);
        editKinName.setEnabled(enable);
        genderGroup.setEnabled(enable);
        radioMale.setEnabled(enable);
        radioFemale.setEnabled(enable);
        btnDob.setEnabled(enable);
        btnSaveChanges.setVisibility(enable ? View.VISIBLE : View.GONE);
        btnEditProfile.setVisibility(enable ? View.GONE : View.VISIBLE);
    }

    private void loadUserProfile() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    editName.setText(snapshot.child("fullName").getValue(String.class));
                    editEmail.setText(snapshot.child("email").getValue(String.class));
                    editPhone.setText(snapshot.child("phone").getValue(String.class));
                    editBloodGroup.setText(snapshot.child("code").getValue(String.class));
                    editKinName.setText(snapshot.child("kinName").getValue(String.class));

                    String gender = snapshot.child("gender").getValue(String.class);
                    if ("Male".equals(gender)) {
                        radioMale.setChecked(true);
                    } else if ("Female".equals(gender)) {
                        radioFemale.setChecked(true);
                    }

                    String dob = snapshot.child("dob").getValue(String.class);
                    if (dob != null) {
                        selectedDob = dob;
                        btnDob.setText("DOB: " + dob);
                    }

                    String base64Image = snapshot.child("profileImage").getValue(String.class);
                    if (base64Image != null) {
                        Bitmap bitmap = decodeBase64ToImage(base64Image);
                        profileImage.setImageBitmap(bitmap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProfileChanges() {
        String name = editName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String bloodGroup = editBloodGroup.getText().toString().trim();
        String kinName = editKinName.getText().toString().trim();
        String gender = radioMale.isChecked() ? "Male" : "Female";

        userRef.child("fullName").setValue(name);
        userRef.child("phone").setValue(phone);
        userRef.child("code").setValue(bloodGroup);
        userRef.child("kinName").setValue(kinName);
        userRef.child("gender").setValue(gender);
        userRef.child("dob").setValue(selectedDob);

        Toast.makeText(getContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
        enableEditing(false);
    }

    private void logoutUser() {
        auth.signOut();
        Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), AuthenticationActivity.class));
        getActivity().finish();
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
            selectedDob = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            btnDob.setText("DOB: " + selectedDob);
        }, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            try {
                imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);

                String base64Image = encodeImageToBase64(bitmap);
                userRef.child("profileImage").setValue(base64Image);
                Toast.makeText(getContext(), "Profile picture updated!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap decodeBase64ToImage(String base64Image) {
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}