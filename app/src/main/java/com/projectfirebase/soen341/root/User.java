package com.projectfirebase.soen341.root;

public class User {
    public String FirstName;
    public String LastName;
    public String email;
    public String PhoneNumber;
    public String ZIPCode;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        FirstName = "";
        LastName = "";
        email = "";
        PhoneNumber = "";
        ZIPCode = "";
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String ZIPCode) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.email = email;
        this.PhoneNumber = phoneNumber;
        this.ZIPCode = ZIPCode;
    }
}
