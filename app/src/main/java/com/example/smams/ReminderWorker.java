package com.example.smams;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ReminderWorker extends Worker {

    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("ReminderWorker", "doWork: ReminderWorker started");

        // Get data passed to WorkManager
        String medicineName = getInputData().getString("medicineName");
        String dosage = getInputData().getString("dosage");
        String reminderId = getInputData().getString("reminderId");

        Log.d("ReminderWorker", "doWork: medicineName = " + medicineName + ", dosage = " + dosage + ", reminderId = " + reminderId);

        // Show notification
        showNotification(medicineName, dosage, reminderId);

        Log.d("ReminderWorker", "doWork: Notification shown successfully");
        return Result.success();
    }

    private void showNotification(String medicineName, String dosage, String reminderId) {
        int notificationId = reminderId.hashCode(); // Unique notification ID for the reminder

        // Create intents for "Taken" and "Missed" actions
        Intent takenIntent = new Intent(getApplicationContext(), DosageActionReceiver.class);
        takenIntent.setAction("ACTION_TAKEN");
        takenIntent.putExtra("reminderId", reminderId);
        takenIntent.putExtra("notificationId", notificationId);

        Intent missedIntent = new Intent(getApplicationContext(), DosageActionReceiver.class);
        missedIntent.setAction("ACTION_MISSED");
        missedIntent.putExtra("reminderId", reminderId);
        missedIntent.putExtra("notificationId", notificationId);

        // Pending intents
        PendingIntent takenPendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                notificationId,
                takenIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        PendingIntent missedPendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                notificationId + 1, // Different request code for the missed action
                missedIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "REMINDER_CHANNEL")
                .setSmallIcon(R.drawable.ic_medicine) // Replace with your icon
                .setContentTitle("Medicine Reminder")
                .setContentText("Time to take your medicine: " + medicineName + "\nDosage: " + dosage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_taken, "Taken", takenPendingIntent) // "Taken" action
                .addAction(R.drawable.ic_missed, "Missed", missedPendingIntent); // "Missed" action

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(notificationId, builder.build());
        }
    }
}
