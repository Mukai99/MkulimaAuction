package com.example.mkulimaauction;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String role; // "farmer" or "buyer"
    private String location;

    // Empty constructor (required for Firestore)
    public User() {}

    public User(String userId, String name, String email, String phone, String role, String location) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.location = location;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
