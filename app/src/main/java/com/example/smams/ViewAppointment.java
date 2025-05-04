package com.example.smams;

public class ViewAppointment {
    private String doctorName;
    private String patientName;
    private String appointmentDate;
    private String appointmentTime;
    private String reason;
    private String nextOfKinName;
    private String nextOfKinPhone;

    public ViewAppointment() {
        // Default constructor required for Firebase
    }

    public ViewAppointment(String doctorName, String patientName, String appointmentDate, String appointmentTime, String reason, String nextOfKinName, String nextOfKinPhone) {
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.nextOfKinName = nextOfKinName;
        this.nextOfKinPhone = nextOfKinPhone;
        this.reason = reason;
    }

    public String getDoctorName() { return doctorName; }
    public String getPatientName() { return patientName; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getAppointmentTime() { return appointmentTime; }
    public String getReason() { return reason; }
    public String getNextOfKinName() { return nextOfKinName; }
    public String getNextOfKinPhone() { return nextOfKinPhone; }

    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setReason(String reason) { this.reason = reason; }
    public void setNextOfKinName(String nextOfKinName) {this.nextOfKinName = nextOfKinName; }
    public void setNextOfKinPhone(String nextOfKinPhone) {this.nextOfKinPhone = nextOfKinPhone; }
}