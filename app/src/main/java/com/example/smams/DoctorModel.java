package com.example.smams;

public class DoctorModel {
    public String fullName, email, specialization, userId;

    public DoctorModel() { }

    public DoctorModel(String fullName, String email, String userId, String specialization) {
        this.fullName = fullName;
        this.email = email;
        this.userId = userId;
        this.specialization = specialization;
    }
}

