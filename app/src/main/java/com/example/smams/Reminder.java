package com.example.smams;

import java.util.List;

public class Reminder {
    private String reminderId;
    private String medicineName;
    private String dosage;
    private String startDate;
    private String endDate;
    private String repeatOption;
    private List<String> reminderTimes;

    public Reminder() {
        // Default constructor required for calls to DataSnapshot.getValue(Reminder.class)
    }

    public Reminder(String reminderId, String medicineName, String dosage, String startDate,
                    String endDate, String repeatOption, List<String> reminderTimes) {
        this.reminderId = reminderId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.repeatOption = repeatOption;
        this.reminderTimes = reminderTimes;
    }

    // Getters and Setters
    public String getReminderId() {
        return reminderId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRepeatOption() {
        return repeatOption;
    }

    public void setRepeatOption(String repeatOption) {
        this.repeatOption = repeatOption;
    }

    public List<String> getReminderTimes() {
        return reminderTimes;
    }

    public void setReminderTimes(List<String> reminderTimes) {
        this.reminderTimes = reminderTimes;
    }
}
