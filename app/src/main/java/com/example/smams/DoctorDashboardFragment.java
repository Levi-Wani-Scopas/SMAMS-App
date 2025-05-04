package com.example.smams;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DoctorDashboardFragment extends Fragment {

    CardView DrugsCard, AppointmentCard, VideoConferenceCard, MyPatientsCard, MedicalReportCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_doctor_dashboard, container, false);

        // Initialize the CardView
        DrugsCard = view.findViewById(R.id.drugsCard);
        AppointmentCard = view.findViewById(R.id.myAppointmentCard);
        VideoConferenceCard =  view.findViewById(R.id.videoConference);
        MyPatientsCard =  view.findViewById(R.id.mypatient);
        MedicalReportCard = view.findViewById(R.id.report);

        // Set Click Listener
        DrugsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrugsAcivity.class);
                startActivity(intent);
            }
        });

        MyPatientsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPatientsActivity.class);
                startActivity(intent);
            }
        });
        AppointmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectedAppointmentsActivity.class);
                startActivity(intent);
            }
        });

        VideoConferenceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoctorVideoConferenceActivity.class);
                startActivity(intent);
            }
        });
        MedicalReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MedicalReportActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
}
