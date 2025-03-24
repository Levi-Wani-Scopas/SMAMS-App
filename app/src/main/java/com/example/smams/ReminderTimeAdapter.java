package com.example.smams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReminderTimeAdapter extends RecyclerView.Adapter<ReminderTimeAdapter.TimeViewHolder> {

    private final List<String> reminderTimes;
    private final OnTimeDeleteListener onTimeDeleteListener;

    public ReminderTimeAdapter(List<String> reminderTimes, OnTimeDeleteListener onTimeDeleteListener) {
        this.reminderTimes = reminderTimes;
        this.onTimeDeleteListener = onTimeDeleteListener;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder_time, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        String time = reminderTimes.get(position);
        holder.tvTime.setText(time);

        // Delete time on delete button click
        holder.ivDelete.setOnClickListener(v -> onTimeDeleteListener.onTimeDeleted(position));
    }

    @Override
    public int getItemCount() {
        return reminderTimes.size();
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        ImageView ivDelete;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }

    public interface OnTimeDeleteListener {
        void onTimeDeleted(int position);
    }
}
