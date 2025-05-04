package com.example.smams;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;


public class DoctorScheduleFragment extends Fragment {

    private CalendarView calendarView;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private HashSet<String> bookedDates = new HashSet<>();

    private Button moodHappy, moodSmile, moodNeutral, moodSad, moodCry;
    private TextView selectedDateText;
    private EditText notepadInput;
    private Button saveNotepad;

    private String selectedDate; // Stores the currently selected date

    public DoctorScheduleFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_schedule, container, false);

        // Initialize Views
        calendarView = view.findViewById(R.id.calendar_view);
        selectedDateText = view.findViewById(R.id.selected_date_text);
        notepadInput = view.findViewById(R.id.notepad_input);
        saveNotepad = view.findViewById(R.id.save_notepad);

        auth = FirebaseAuth.getInstance();
        loadBookedAppointments(); // Load booked dates from Firebase

        // Handle date selection
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year; // Month is 0-based
            selectedDateText.setText("Selected Date: " + selectedDate);

            if (bookedDates.contains(selectedDate)) {
                Toast.makeText(getContext(), "You have an appointment on this date!", Toast.LENGTH_SHORT).show();
            }

            // Load saved mood and note for this date
            loadNotepad();
        });


        // Save Notepad Button
        saveNotepad.setOnClickListener(v -> saveNotepad());

        return view;
    }

    private void loadBookedAppointments() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) return;

        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Patients")
                .child("User_ID")
                .child("Appointments");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookedDates.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String date = data.child("appointmentDate").getValue(String.class);
                    if (date != null) {
                        bookedDates.add(date); // Add date to HashSet
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load appointments.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Save Notepad for Selected Date
    private void saveNotepad() {
        if (selectedDate == null) {
            Toast.makeText(getContext(), "Please select a date first!", Toast.LENGTH_SHORT).show();
            return;
        }

        String note = notepadInput.getText().toString().trim();
        if (note.isEmpty()) {
            Toast.makeText(getContext(), "Note is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getContext().getSharedPreferences("MiniNotepad", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(selectedDate, note);
        editor.apply();

        Toast.makeText(getContext(), "Note saved for " + selectedDate, Toast.LENGTH_SHORT).show();
    }

    // Load Notepad for Selected Date
    private void loadNotepad() {
        SharedPreferences prefs = getContext().getSharedPreferences("MiniNotepad", getContext().MODE_PRIVATE);
        String note = prefs.getString(selectedDate, "");

        notepadInput.setText(note);
    }
}
