package com.example.smams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SavedReminderAdapter extends RecyclerView.Adapter<SavedReminderAdapter.ReminderViewHolder> {

    private List<Reminder> reminderList;

    public SavedReminderAdapter(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.tvMedicineName.setText("Medicine: " + reminder.getMedicineName());
        holder.tvDosage.setText("Dosage: " + reminder.getDosage());
        holder.tvStartDate.setText("Start Date: " + reminder.getStartDate());
        holder.tvEndDate.setText("End Date: " + reminder.getEndDate());
        holder.tvRepeatOption.setText("Repeat: " + reminder.getRepeatOption());
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    static class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView tvMedicineName, tvDosage, tvStartDate, tvEndDate, tvRepeatOption;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMedicineName = itemView.findViewById(R.id.tv_medicine_name);
            tvDosage = itemView.findViewById(R.id.tv_dosage);
            tvStartDate = itemView.findViewById(R.id.tv_start_date);
            tvEndDate = itemView.findViewById(R.id.tv_end_date);
            tvRepeatOption = itemView.findViewById(R.id.tv_repeat_option);
        }
    }
}
