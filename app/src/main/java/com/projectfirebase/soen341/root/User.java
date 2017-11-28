package com.projectfirebase.soen341.root;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String zipCode;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        firstName = "";
        lastName = "";
        email = "";
        phoneNumber = "";
        zipCode = "";
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }
}
