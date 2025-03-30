package com.example.smams;

public class Doctor {
    private String name;
    private String specialization;
    private String email;
    private String biography;
    private String workingHours;
    private int imageResourceId; // Resource ID for the doctor's image

    // Constructor
    public Doctor(String name, String specialization, String email, String biography, String workingHours, int imageResourceId) {
        this.name = name;
        this.specialization = specialization;
        this.email = email;
        this.biography = biography;
        this.workingHours = workingHours;
        this.imageResourceId = imageResourceId;
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getEmail() { return email; }
    public String getBiography() { return biography; }
    public String getWorkingHours() { return workingHours; }
    public int getImageResourceId() { return imageResourceId; }
}

