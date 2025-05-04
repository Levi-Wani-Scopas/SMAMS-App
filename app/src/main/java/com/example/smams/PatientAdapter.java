package com.example.smams;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private Context context;
    private List<Patient> patientList;

    public PatientAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = patientList.get(position);

        holder.textPatientName.setText(patient.getName());
        holder.textPatientEmail.setText(patient.getEmail());
        holder.textPatientPhone.setText(patient.getPhone());
        holder.textNextOfKinName.setText(patient.getKinName());
        holder.textNextOfKinPhone.setText(patient.getKinPhone());

        // Load profile picture from Base64
        if (patient.getProfileImage() != null && !patient.getProfileImage().isEmpty()) {
            setBase64Image(patient.getProfileImage(), holder.imagePatientProfile);
        } else {
            holder.imagePatientProfile.setImageResource(R.drawable.ic_profile_placeholder); // default image
        }

        holder.buttonDosageTrack.setOnClickListener(v -> {
            Intent intent = new Intent(context, DosageProgress.class);
            // Pass patient data to the next activity if needed
            intent.putExtra("patientId", patient.getId());
            intent.putExtra("patientName", patient.getName());
            context.startActivity(intent);
        });


        holder.buttonTrack.setOnClickListener(v -> {
            Intent intent = new Intent(context, DosageHistoryActivity.class);
            context.startActivity(intent);
        });

        holder.buttonRecords.setOnClickListener(v -> {
            Intent intent = new Intent(context, DisplayRemindersActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {

        ImageView imagePatientProfile;
        TextView textPatientName, textPatientEmail, textPatientPhone, textNextOfKinName, textNextOfKinPhone;
        Button buttonDosageTrack, buttonTrack, buttonRecords;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            imagePatientProfile = itemView.findViewById(R.id.image_patient_profile);
            textPatientName = itemView.findViewById(R.id.text_patient_name);
            textPatientEmail = itemView.findViewById(R.id.text_patient_email);
            textPatientPhone = itemView.findViewById(R.id.text_patient_phone);
            textNextOfKinName = itemView.findViewById(R.id.text_next_of_kin_name);
            textNextOfKinPhone = itemView.findViewById(R.id.text_next_of_kin_phone);
            buttonDosageTrack = itemView.findViewById(R.id.button_dosage_track);
            buttonTrack = itemView.findViewById(R.id.Track);
            buttonRecords = itemView.findViewById(R.id.Records);
        }
    }

    // Helper method to decode Base64 string to Bitmap and set to ImageView
    private void setBase64Image(String base64String, ImageView imageView) {
        try {
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            imageView.setImageBitmap(decodedBitmap);
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(R.drawable.ic_profile_placeholder); // fallback in case of error
        }
    }
}