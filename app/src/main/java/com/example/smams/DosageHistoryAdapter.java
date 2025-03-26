package com.example.smams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DosageHistoryAdapter extends RecyclerView.Adapter<DosageHistoryAdapter.ViewHolder> {

    private final List<DosageHistory> dosageHistoryList;

    public DosageHistoryAdapter(List<DosageHistory> dosageHistoryList) {
        this.dosageHistoryList = dosageHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dosage_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DosageHistory history = dosageHistoryList.get(position);

        holder.tvMedicineName.setText(history.getMedicineName());
        holder.tvDosage.setText(history.getDosage());
        holder.tvStatus.setText(history.getStatus());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
        String formattedTimestamp = dateFormat.format(new Date(history.getTimestamp()));
        holder.tvTimestamp.setText(formattedTimestamp);
    }

    @Override
    public int getItemCount() {
        return dosageHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMedicineName, tvDosage, tvStatus, tvTimestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMedicineName = itemView.findViewById(R.id.tv_medicine_name);
            tvDosage = itemView.findViewById(R.id.tv_dosage);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvTimestamp = itemView.findViewById(R.id.tv_timestamp);
        }
    }
}
