package com.example.smams;

public class Patient {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String kinName;
    private String kinPhone;
    private String profileImage;

    public Patient() {
        // Needed for Firebase or empty initialization
    }

    public Patient(String id, String name, String email, String phone, String kinName, String kinPhone, String profileImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.kinName = kinName;
        this.kinPhone = kinPhone;
        this.profileImage = profileImage;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getKinName() { return kinName; }
    public String getKinPhone() { return kinPhone; }
    public String getProfileImage() { return profileImage; }
}
