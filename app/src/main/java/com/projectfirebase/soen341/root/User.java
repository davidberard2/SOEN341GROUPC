package com.projectfirebase.soen341.root;

public class User {
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String zipCode;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        firstName = "";
        lastName = "";
        email = "";
        phoneNumber = "";
        zipCode = "";
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String ZIPCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
    }
}
