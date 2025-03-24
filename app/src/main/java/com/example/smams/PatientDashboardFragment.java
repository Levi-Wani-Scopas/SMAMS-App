package com.example.smams;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class PatientDashboardFragment extends Fragment {

    CardView card1, card2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_dashboard, container, false);

        // Find the CardView by its ID
        View cardView = view.findViewById(R.id.medicineReminderCard);
        View card1 = view.findViewById(R.id.displayReminders);
        View card2 = view.findViewById(R.id.pharmacy);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AddReminderActivity
                Intent intent = new Intent(getActivity(), DisplayRemindersActivity.class);
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AddReminderActivity
                Intent intent = new Intent(getActivity(), Pharmacy.class);
                startActivity(intent);
            }
        });




        // Set a click listener for the CardView
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AddReminderActivity
                Intent intent = new Intent(getActivity(), AddReminderActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
