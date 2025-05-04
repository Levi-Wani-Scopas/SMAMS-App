package com.example.smams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CancelRequestAdapter extends RecyclerView.Adapter<CancelRequestAdapter.ViewHolder> {

    private List<CancelRequest> requestList;

    public CancelRequestAdapter(List<CancelRequest> requestList) {
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public CancelRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancel_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancelRequestAdapter.ViewHolder holder, int position) {
        CancelRequest request = requestList.get(position);

        holder.doctorName.setText("Doctor: " + request.getDoctorName());
        holder.patientName.setText("Patient: " + request.getPatientName());
        holder.date.setText("Date: " + request.getAppointmentDate());
        holder.time.setText("Time: " + request.getAppointmentTime());
        holder.reason.setText("Cancellation Reason: " + request.getReason());
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView doctorName, date, time, reason, patientName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.tvDoctorName);
            patientName = itemView.findViewById(R.id.tvPatientName);
            date = itemView.findViewById(R.id.tvDate);
            time = itemView.findViewById(R.id.tvTime);
            reason = itemView.findViewById(R.id.tvReason);
        }
    }
}