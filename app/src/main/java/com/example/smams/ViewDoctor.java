package com.example.smams;

public class ViewDoctor {
    private String fullName;
    private String email;
    private String specialization;
    private String phone;
    private String workingHours;
    private String profileImage; // URL from Firebase storage

    public ViewDoctor() {
        // Required empty constructor for Firebase
    }

    public ViewDoctor(String fullName, String email, String workingHours, String specialization, String profileImage, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.specialization = specialization;
        this.workingHours = workingHours;
        this.phone = phone;
        this.profileImage = profileImage;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhone(){
        return phone;
    }

    public String getWorkingHours(){
        return workingHours;
    }

    public String getProfileImage() {
        return profileImage;
    }
}