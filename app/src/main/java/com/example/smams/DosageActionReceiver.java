package com.example.smams;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DosageActionReceiver extends BroadcastReceiver {

    private static final String TAG = "DosageActionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.e(TAG, "Intent is null. Cannot process the dosage action.");
            return;
        }

        String action = intent.getAction();
        String reminderId = intent.getStringExtra("reminderId");
        int notificationId = intent.getIntExtra("notificationId", -1); // Retrieve notification ID

        if (reminderId == null || reminderId.isEmpty()) {
            Log.e(TAG, "Reminder ID is null or empty. Cannot log dosage action.");
            return;
        }

        long timestamp = System.currentTimeMillis();

        // Firebase Database reference
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Patients")
                .child("User_ID") // Replace with the actual user ID dynamically
                .child("Reminders")
                .child(reminderId)
                .child("dosageTracker");

        Map<String, Object> trackerData = new HashMap<>();
        trackerData.put("timestamp", timestamp);

        if ("ACTION_TAKEN".equals(action)) {
            trackerData.put("status", "Taken");
            logDosageAction(reference, trackerData, "Taken");
        } else if ("ACTION_MISSED".equals(action)) {
            trackerData.put("status", "Missed");
            logDosageAction(reference, trackerData, "Missed");
        } else {
            Log.e(TAG, "Unknown action: " + action);
        }

        // Cancel the notification
        if (notificationId != -1) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.cancel(notificationId); // Cancel the notification
                Log.d(TAG, "Notification canceled: ID = " + notificationId);
            }
        }
    }

    private void logDosageAction(DatabaseReference reference, Map<String, Object> trackerData, String actionStatus) {
        reference.push().setValue(trackerData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Dosage action '" + actionStatus + "' logged successfully: " + trackerData))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to log dosage action '" + actionStatus + "'", e));
    }
}
