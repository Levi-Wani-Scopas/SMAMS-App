package com.example.smams;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class PatientDoctorFragment extends Fragment {

    private TextView noDoctorSelected, doctorName, doctorSpecialization, doctorBiography, doctorWorkingHours, doctorEmail, cancelDoctor;
    private ImageView doctorImage, emailIcon;
    private Button bookAppointmentButton;
    private LinearLayout doctorDetailsLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_doctor, container, false);

        // Initialize views
        noDoctorSelected = view.findViewById(R.id.no_doctor_selected);
        doctorDetailsLayout = view.findViewById(R.id.doctor_details_layout);
        doctorImage = view.findViewById(R.id.doctor_image);
        emailIcon = view.findViewById(R.id.email_icon);
        doctorName = view.findViewById(R.id.doctor_name);
        cancelDoctor = view.findViewById(R.id.CancelDoctor);
        doctorSpecialization = view.findViewById(R.id.doctor_specialization);
        doctorBiography = view.findViewById(R.id.doctor_biography);
        doctorEmail = view.findViewById(R.id.doctor_email);
        doctorWorkingHours = view.findViewById(R.id.doctor_working_hours);
        bookAppointmentButton = view.findViewById(R.id.book_appointment_button);


        cancelDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DisplayRemindersActivity
                Intent intent = new Intent(getActivity(), PatientDashboard.class);
                startActivity(intent);
            }
        });

        // Set click listener for the button to navigate to AppointmentFormFragment

        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ensure doctor details are available before proceeding
                if (doctorName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "No doctor selected!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get doctor’s name from the TextView
                String selectedDoctorName = doctorName.getText().toString();

                // Create an intent to open AppointmentFormActivity
                Intent intent = new Intent(getActivity(), AppointmentFormActivity.class);

                // Pass the doctor’s name to the intent
                intent.putExtra("doctor_name", selectedDoctorName);

                // Start the activity
                startActivity(intent);
            }
        });



        // Get arguments passed from PatientDashboardFragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Hide "No doctor selected" text and show details layout
            noDoctorSelected.setVisibility(View.GONE);
            doctorDetailsLayout.setVisibility(View.VISIBLE);

            // Extract doctor details from bundle
            String name = bundle.getString("doctor_name");
            String specialization = bundle.getString("doctor_specialization");
            String email = bundle.getString("doctor_email");
            String biography = bundle.getString("doctor_biography");
            String workingHours = bundle.getString("doctor_working_hours");
            int imageResId = bundle.getInt("doctor_image");

            // Set values to views
            doctorName.setText(name);
            doctorSpecialization.setText(specialization);
            doctorEmail.setText(email);
            doctorBiography.setText(biography);
            doctorWorkingHours.setText(workingHours);
            doctorImage.setImageResource(imageResId);

            // Add click listener for email icon (optional)
            emailIcon.setOnClickListener(v -> {
                // Logic to send an email to the doctor
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"doctor@example.com"}); // Replace with dynamic email if available
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Consultation Request");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send Email"));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Show "No doctor selected" text and hide details layout
            noDoctorSelected.setVisibility(View.VISIBLE);
            doctorDetailsLayout.setVisibility(View.GONE);
        }

        return view;
    }
}
