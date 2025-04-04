package com.example.smams;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;

public class PatientScheduleFragment extends Fragment {
    private CalendarView calendarView;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private HashSet<String> bookedDates = new HashSet<>();

    private Button moodHappy, moodSmile, moodNeutral, moodSad, moodCry;
    private TextView selectedDateText;
    private EditText notepadInput;
    private Button saveNotepad;

    private String selectedDate; // Stores the currently selected date

    public PatientScheduleFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_schedule, container, false);

        // Initialize Views
        calendarView = view.findViewById(R.id.calendar_view);
        selectedDateText = view.findViewById(R.id.selected_date_text);
        moodHappy = view.findViewById(R.id.mood_happy);
        moodSmile = view.findViewById(R.id.mood_smile);
        moodNeutral = view.findViewById(R.id.mood_neutral);
        moodSad = view.findViewById(R.id.mood_sad);
        moodCry = view.findViewById(R.id.mood_cry);
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
            loadMood();
            loadNotepad();
        });

        // Mood Tracker Buttons
        moodHappy.setOnClickListener(v -> saveMood("😃", "Very happy!"));
        moodSmile.setOnClickListener(v -> saveMood("😊", "Happy!"));
        moodNeutral.setOnClickListener(v -> saveMood("😐", "Neutral!"));
        moodSad.setOnClickListener(v -> saveMood("☹️", "Sad!"));
        moodCry.setOnClickListener(v -> saveMood("😢", "Very Sad!"));

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

    // Save Mood for Selected Date with a Toast Message
    private void saveMood(String mood, String moodMessage) {
        if (selectedDate == null) {
            Toast.makeText(getContext(), "Please select a date first!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getContext().getSharedPreferences("MoodTracker", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(selectedDate, mood);
        editor.apply();

        Toast.makeText(getContext(), moodMessage, Toast.LENGTH_SHORT).show();
    }

    // Load Mood for Selected Date
    private void loadMood() {
        SharedPreferences prefs = getContext().getSharedPreferences("MoodTracker", getContext().MODE_PRIVATE);
        String mood = prefs.getString(selectedDate, null); // Returns null if no mood is saved

        if (mood != null) {
            String moodMessage = getMoodMessage(mood); // Get message based on emoji
            Toast.makeText(getContext(), "Mood for " + selectedDate + ": " + mood + " " + moodMessage, Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to return mood messages based on saved emoji
    private String getMoodMessage(String mood) {
        switch (mood) {
            case "😃": return "Very happy!";
            case "😊": return "Happy!";
            case "😐": return "Neutral!";
            case "☹️": return "Sad!";
            case "😢": return "Very sad!";
            default: return "";
        }
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
