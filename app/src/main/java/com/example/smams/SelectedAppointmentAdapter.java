package com.example.smams;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectedAppointmentAdapter extends RecyclerView.Adapter<SelectedAppointmentAdapter.ViewHolder> {

    private Context context;
    private List<DoctorAppointment> appointmentList;

    public SelectedAppointmentAdapter(Context context, List<DoctorAppointment> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_selected_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorAppointment appointment = appointmentList.get(position);

        holder.textPatientName.setText("Patient: " + appointment.getPatientName());
        holder.textDoctorName.setText("Doctor: " + appointment.getDoctorName());
        holder.textEmail.setText("Email:" + appointment.getEmail());
        holder.textDateTime.setText("Date: " + appointment.getAppointmentDate() + " | Time: " + appointment.getAppointmentTime());
        holder.textReason.setText("Reason: " + appointment.getReason());
        holder.textStatus.setText("Status: " + appointment.getStatus());

        // Handle Confirm button click
        holder.buttonConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(context, ConfirmAppointmentActivity.class);
            intent.putExtra("patientName", appointment.getPatientName());
            intent.putExtra("doctorName", appointment.getDoctorName());
            intent.putExtra("email", appointment.getEmail());
            intent.putExtra("appointmentDate", appointment.getAppointmentDate());
            intent.putExtra("appointmentTime", appointment.getAppointmentTime());
            intent.putExtra("reason", appointment.getReason());
            intent.putExtra("status", appointment.getStatus());
            context.startActivity(intent);
        });

        // Handle Cancel button click
        holder.buttonCancel.setOnClickListener(v -> {
            // Remove the appointment from the list
            appointmentList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            notifyItemRangeChanged(holder.getAdapterPosition(), appointmentList.size());
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textPatientName, textDoctorName, textDateTime, textReason, textStatus, textEmail;
        Button buttonConfirm, buttonCancel; // Added buttons here

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textPatientName = itemView.findViewById(R.id.text_selected_patient_name);
            textDoctorName = itemView.findViewById(R.id.text_selected_doctor_name);
            textEmail = itemView.findViewById(R.id.text_email);
            textDateTime = itemView.findViewById(R.id.text_selected_datetime);
            textReason = itemView.findViewById(R.id.text_selected_reason);
            textStatus = itemView.findViewById(R.id.text_selected_status);

            // Initialize buttons
            buttonConfirm = itemView.findViewById(R.id.button_confirm);
            buttonCancel = itemView.findViewById(R.id.button_cancel);
        }
    }


}