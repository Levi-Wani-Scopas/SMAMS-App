package com.example.smams;

public class PasswordResetRequest {
    private String email;
    private String accountType;

    public PasswordResetRequest() {
        // Default constructor required for Firebase
    }

    public PasswordResetRequest(String email, String accountType) {
        this.email = email;
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountType() {
        return accountType;
    }
}