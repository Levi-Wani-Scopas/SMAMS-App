package com.example.smams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.textDateTime.setText("Date: " + appointment.getAppointmentDate() + " | Time: " + appointment.getAppointmentTime());
        holder.textReason.setText("Reason: " + appointment.getReason());
        holder.textStatus.setText("Status: " + appointment.getStatus());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textPatientName, textDoctorName, textDateTime, textReason, textStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textPatientName = itemView.findViewById(R.id.text_selected_patient_name);
            textDoctorName = itemView.findViewById(R.id.text_selected_doctor_name);
            textDateTime = itemView.findViewById(R.id.text_selected_datetime);
            textReason = itemView.findViewById(R.id.text_selected_reason);
            textStatus = itemView.findViewById(R.id.text_selected_status);
        }
    }
}
