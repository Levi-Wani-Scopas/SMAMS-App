package com.example.smams;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AdminDashboardFragment extends Fragment {

    CardView ViewDoctors, ViewPatients, ViewAppointments, ViewCancelRequests, ViewResetPasswordRequests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        // Initialize the CardView
        ViewDoctors = view.findViewById(R.id.viewDoctors);
        ViewPatients = view.findViewById(R.id.viewPatients);
        ViewAppointments = view.findViewById(R.id.stepsCount2);
        ViewCancelRequests = view.findViewById(R.id.DosageTrack2);
        ViewResetPasswordRequests = view.findViewById(R.id.stepsCount3);

        ViewDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllDoctorsActivity.class);
                startActivity(intent);
            }
        });

        ViewPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllPatientsActivity.class);
                startActivity(intent);
            }
        });


        ViewAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllAppointmentsActivity.class);
                startActivity(intent);
            }
        });

        ViewCancelRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminCancelRequestsActivity.class);
                startActivity(intent);
            }
        });

        ViewResetPasswordRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewPasswordResetRequestsActivity.class);
                startActivity(intent);
            }
        });



        return view;

    }

}