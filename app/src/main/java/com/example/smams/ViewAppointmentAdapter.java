package com.example.smams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewAppointmentAdapter extends RecyclerView.Adapter<ViewAppointmentAdapter.AppointmentViewHolder> {

    private List<ViewAppointment> appointmentList;

    public ViewAppointmentAdapter(List<ViewAppointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        ViewAppointment appointment = appointmentList.get(position);
        holder.patientName.setText("Patient: " + appointment.getPatientName());
        holder.doctorName.setText("Doctor: " + appointment.getDoctorName());
        holder.date.setText("Date: " + appointment.getAppointmentDate());
        holder.time.setText("Time: " + appointment.getAppointmentTime());
        holder.reason.setText("Reason: " + appointment.getReason());
        holder.kinName.setText("Next of Kin Name: " + appointment.getNextOfKinName());
        holder.kinNumber.setText("Next of Kin Phone: " + appointment.getNextOfKinPhone());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView patientName, doctorName, date, time, reason, kinName, kinNumber;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.tvPatientName);
            doctorName = itemView.findViewById(R.id.tvDoctorName);
            date = itemView.findViewById(R.id.tvDate);
            time = itemView.findViewById(R.id.tvTime);
            reason = itemView.findViewById(R.id.tvReason);
            kinName = itemView.findViewById(R.id.tvKinName);
            kinNumber = itemView.findViewById(R.id.tvKinNumber);
        }
    }
}