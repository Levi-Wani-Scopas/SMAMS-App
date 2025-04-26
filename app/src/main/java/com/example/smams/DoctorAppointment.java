package com.example.smams;

public class DoctorAppointment {
    private String patientName;
    private String doctorName;
    private String email;
    private String phoneNumber;
    private String appointmentDate;
    private String appointmentTime;
    private String reason;
    private String nextOfKinName;
    private String nextOfKinPhone;
    private String status;
    private boolean selected;


    public DoctorAppointment() {
        // Default constructor required for Firebase
    }

    public DoctorAppointment(String patientName, String doctorName, String email, String phoneNumber, String appointmentDate, String appointmentTime, String reason, String nextOfKinName, String nextOfKinPhone, String status, boolean selected) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.nextOfKinName = nextOfKinName;
        this.nextOfKinPhone = nextOfKinPhone;
        this.status = status;
        this.selected = selected;


    }

    // Getters and Setters
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName (String doctorName) {this.doctorName = doctorName ; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhone(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getNextOfKinName() { return nextOfKinName; }
    public void setNextOfKin(String nextOfKinName) { this.nextOfKinName = nextOfKinName; }

    public String getNextOfKinPhone() { return nextOfKinPhone; }
    public void setNextOfKinPhone(String nextOfKinPhone) { this.nextOfKinPhone = nextOfKinPhone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

}
