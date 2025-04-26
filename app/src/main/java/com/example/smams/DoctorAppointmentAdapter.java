package com.example.smams;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        holder.tickButton.setOnClickListener(v -> {
            DoctorAppointment selectedAppointment = appointmentList.get(holder.getAdapterPosition());

            SharedPreferences sharedPreferences = context.getSharedPreferences("SelectedAppointments", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String appointmentString = selectedAppointment.getPatientName() + "||" +
                    selectedAppointment.getDoctorName() + "||" +
                    selectedAppointment.getAppointmentDate() + "||" +
                    selectedAppointment.getAppointmentTime() + "||" +
                    selectedAppointment.getReason() + "||" +
                    selectedAppointment.getEmail() + "||" +
                    selectedAppointment.getPhoneNumber() + "||" +
                    selectedAppointment.getNextOfKinName() + "||" +
                    selectedAppointment.getNextOfKinPhone() + "||" +
                    selectedAppointment.getStatus();

            Set<String> appointmentSet = sharedPreferences.getStringSet("appointments", new HashSet<>());

            Set<String> updatedSet = new HashSet<>(appointmentSet);
            updatedSet.add(appointmentString);

            editor.putStringSet("appointments", updatedSet);
            editor.apply();

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
