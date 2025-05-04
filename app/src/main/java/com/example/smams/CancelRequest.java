package com.example.smams;

public class CancelRequest {
    private String doctorName;
    private String patientName;
    private String appointmentDate;
    private String appointmentTime;
    private String reason;

    // Required empty constructor for Firebase
    public CancelRequest() {
    }

    // Constructor with parameters
    public CancelRequest(String doctorName, String appointmentDate, String appointmentTime, String reason, String patientName) {
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
    }

    // Getters and setters
    public String getDoctorName() {
        return doctorName;
    }


    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(){
        this.patientName = patientName;

    }
    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}