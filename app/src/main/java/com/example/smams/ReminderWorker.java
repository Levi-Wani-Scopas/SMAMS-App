package com.example.smams;

import android.app.NotificationManager;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import android.content.Context;

public class ReminderWorker extends Worker {

    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Get the data passed to the WorkManager
        String medicineName = getInputData().getString("medicineName");
        String dosage = getInputData().getString("dosage");
        String startDate = getInputData().getString("startDate");

        // Show the notification
        showNotification(medicineName, dosage);

        return Result.success();
    }

    private void showNotification(String medicineName, String dosage) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "REMINDER_CHANNEL")
                .setSmallIcon(R.drawable.ic_medicine) // Replace with your app's icon
                .setContentTitle("Medicine Reminder")
                .setContentText("You will be notified to take:   " + medicineName + "\n Dosage:   (" + dosage + ")")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}
