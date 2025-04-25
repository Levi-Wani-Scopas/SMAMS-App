package com.example.smams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAppointmentAdapter extends RecyclerView.Adapter<DoctorAppointmentAdapter.ViewHolder> {

    private Context context;
    private List<DoctorAppointment> appointmentList;

    public DoctorAppointmentAdapter(Context context, List<DoctorAppointment> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorAppointment appointment = appointmentList.get(position);

        holder.textPatientName.setText("Patient: " + appointment.getPatientName());
        holder.textDoctorName.setText("Doctor: " + appointment.getDoctorName());
        holder.textAppointmentDatetime.setText("Date: " + appointment.getAppointmentDate() + " | Time: " + appointment.getAppointmentTime());
        holder.textReason.setText("Reason: " + appointment.getReason());
        holder.textContact.setText("Email: " + appointment.getEmail() + " | Phone: " + appointment.getPhoneNumber());
        holder.textKin.setText("Next of Kin: " + appointment.getNextOfKinName() + " | Phone: " + appointment.getNextOfKinPhone());
        holder.textStatus.setText("Status: " + appointment.getStatus());

        // You can add functionality to the tick button here
        holder.tickButton.setOnClickListener(v -> {
            Toast.makeText(context, "Appointment Selected", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textPatientName, textDoctorName, textAppointmentDatetime, textReason, textContact, textKin, textStatus;
        ImageView tickButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textPatientName = itemView.findViewById(R.id.text_patient_name);
            textDoctorName = itemView.findViewById(R.id.text_doctor_name);
            textAppointmentDatetime = itemView.findViewById(R.id.text_appointment_datetime);
            textReason = itemView.findViewById(R.id.text_reason);
            textContact = itemView.findViewById(R.id.text_contact);
            textKin = itemView.findViewById(R.id.text_kin);
            textStatus = itemView.findViewById(R.id.text_status);
            tickButton = itemView.findViewById(R.id.button_select_appointment);
        }
    }
}

