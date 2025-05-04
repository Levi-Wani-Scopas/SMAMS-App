package com.example.smams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PasswordResetRequestAdapter extends RecyclerView.Adapter<PasswordResetRequestAdapter.ViewHolder> {

    private List<PasswordResetRequest> requestList;

    public PasswordResetRequestAdapter(List<PasswordResetRequest> requestList) {
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public PasswordResetRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_password_reset_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordResetRequestAdapter.ViewHolder holder, int position) {
        PasswordResetRequest request = requestList.get(position);
        holder.emailText.setText("Email: " + request.getEmail());
        holder.accountTypeText.setText("Account Type: " + request.getAccountType());
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView emailText, accountTypeText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            emailText = itemView.findViewById(R.id.email_text);
            accountTypeText = itemView.findViewById(R.id.account_type_text);
        }
    }
}