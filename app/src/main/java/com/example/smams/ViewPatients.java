package com.example.smams;

public class ViewPatients {
    private String fullName;
    private String email;
    private String gender;
    private String phone;
    private String bloodGroup;
    private String profileImage; // URL from Firebase storage

    public ViewPatients() {
        // Required empty constructor for Firebase
    }

    public ViewPatients(String fullName, String email, String gender, String bloodGroup, String profileImage, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.profileImage = profileImage;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone(){
        return phone;
    }

    public String getBloodGroup(){
        return bloodGroup;
    }

    public String getProfileImage() {
        return profileImage;
    }
}