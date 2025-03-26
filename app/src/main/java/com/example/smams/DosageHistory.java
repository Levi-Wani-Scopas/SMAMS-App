package com.example.smams;

public class DosageHistory {
    private String medicineName;
    private String dosage;
    private String status;
    private long timestamp;

    public DosageHistory() {
        // Required for Firebase
    }

    public DosageHistory(String medicineName, String dosage, String status, long timestamp) {
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.status = status;
        this.timestamp = timestamp;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
