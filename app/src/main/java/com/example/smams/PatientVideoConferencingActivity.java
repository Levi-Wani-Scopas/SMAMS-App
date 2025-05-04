package com.example.smams;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PatientVideoConferencingActivity extends AppCompatActivity {

    private ImageView backImg;
    private Button buttonStartMeeting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_video_conferencing);


        buttonStartMeeting = findViewById(R.id.button_start_meeting);
        backImg = findViewById(R.id.btn_exit);


        backImg.setOnClickListener(view -> startActivity(new Intent(PatientVideoConferencingActivity.this, PatientDashboard.class)));

        buttonStartMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://meet.google.com"));
                startActivity(intent);
            }
        });
    }
}