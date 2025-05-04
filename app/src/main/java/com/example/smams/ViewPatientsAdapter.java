package com.example.smams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ViewPatientsAdapter extends RecyclerView.Adapter<ViewPatientsAdapter.ViewHolder> {

    private List<ViewPatients> patientsList;

    public ViewPatientsAdapter(List<ViewPatients> patientsList) {
        this.patientsList = patientsList;
    }

    @NonNull
    @Override
    public ViewPatientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPatientsAdapter.ViewHolder holder, int position) {
        ViewPatients patient = patientsList.get(position);
        holder.fullName.setText(patient.getFullName());
        holder.gender.setText(patient.getGender());
        holder.phone.setText(patient.getPhone());
        holder.bloodGroup.setText(patient.getBloodGroup());
        holder.email.setText(patient.getEmail());

        // Decode Base64 string to Bitmap
        if (patient.getProfileImage() != null && !patient.getProfileImage().isEmpty()) {
            try {
                byte[] decodedBytes = Base64.decode(patient.getProfileImage(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                holder.imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return patientsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, gender, email, phone, bloodGroup;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.tvDoctorName);
            gender = itemView.findViewById(R.id.tvGender);
            bloodGroup = itemView.findViewById(R.id.bloodGroup);
            phone = itemView.findViewById(R.id.tvPhone);
            email = itemView.findViewById(R.id.tvDoctorEmail);
            imageView = itemView.findViewById(R.id.imgDoctorProfile);
        }
    }
}