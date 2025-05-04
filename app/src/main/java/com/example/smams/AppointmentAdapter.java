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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private Context context;
    private List<Appointment> appointmentList;
    private DatabaseReference databaseReference;
    private String currentUserId;

    public AppointmentAdapter(Context context, List<Appointment> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
        this.currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.databaseReference = FirebaseDatabase.getInstance()
                .getReference("Patients")
                .child("User_Id")
                .child("Appointments");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);

        holder.doctorNameText.setText(appointment.getDoctorName());
        holder.appointmentStatusText.setText("Status: " + appointment.getStatus());
        holder.patientNameText.setText("Patient: " + appointment.getPatientName());
        holder.reasonText.setText("Appt Reason: " + appointment.getReason());
        holder.appointmentDatetimeText.setText("Date: " + appointment.getAppointmentDate() + " | Time: " + appointment.getAppointmentTime());

        holder.viewDetailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, AppointmentDetailsActivity.class);
            intent.putExtra("appointmentId", appointment.getAppointmentId());
            context.startActivity(intent);
        });

        holder.cancelAppointmentButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, CancelAppointmentRequestActivity.class);
            intent.putExtra("appointmentId", appointment.getAppointmentId());
            intent.putExtra("doctorName", appointment.getDoctorName());
            intent.putExtra("reason", appointment.getReason());
            intent.putExtra("patientName", appointment.getPatientName());
            intent.putExtra("appointmentDate", appointment.getAppointmentDate());
            intent.putExtra("appointmentTime", appointment.getAppointmentTime());


            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView doctorNameText, appointmentStatusText, appointmentDatetimeText, patientNameText, reasonText;
        Button viewDetailsButton, cancelAppointmentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorNameText = itemView.findViewById(R.id.doctor_name_text);
            appointmentStatusText = itemView.findViewById(R.id.appointment_status_text);
            appointmentDatetimeText = itemView.findViewById(R.id.appointment_datetime_text);
            reasonText = itemView.findViewById(R.id.appointment_reason_text);
            viewDetailsButton = itemView.findViewById(R.id.view_details_button);
            cancelAppointmentButton = itemView.findViewById(R.id.cancel_appointment_button);
            patientNameText = itemView.findViewById(R.id.patient_name_text);
        }
    }
}
