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

public class ViewDoctorAdapter extends RecyclerView.Adapter<ViewDoctorAdapter.ViewHolder> {

    private List<ViewDoctor> doctorList;

    public ViewDoctorAdapter(List<ViewDoctor> doctorList) {
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public ViewDoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_doctor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDoctorAdapter.ViewHolder holder, int position) {
        ViewDoctor doctor = doctorList.get(position);
        holder.fullName.setText(doctor.getFullName());
        holder.workingHours.setText(doctor.getWorkingHours());
        holder.specialization.setText(doctor.getSpecialization());
        holder.phone.setText(doctor.getPhone());
        holder.email.setText(doctor.getEmail());


        // Decode Base64 string to Bitmap
        if (doctor.getProfileImage() != null && !doctor.getProfileImage().isEmpty()) {
            try {
                byte[] decodedBytes = Base64.decode(doctor.getProfileImage(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                holder.imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, specialization, email, phone, workingHours;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.tvDoctorName);
            specialization = itemView.findViewById(R.id.tvSpecialization);
            phone = itemView.findViewById(R.id.tvPhone);
            email = itemView.findViewById(R.id.tvDoctorEmail);
            workingHours = itemView.findViewById(R.id.tvWorkHours);

            imageView = itemView.findViewById(R.id.imgDoctorProfile);
        }
    }
}