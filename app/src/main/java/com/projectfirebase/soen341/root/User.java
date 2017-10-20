package com.projectfirebase.soen341.root;

public class User {
    public String FirstName;
    public String LastName;
    public String PhoneNumber;
    public String ZIPCode;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        FirstName = "";
        LastName = "";
        PhoneNumber = "";
        ZIPCode = "";
    }

    public User(String firstName, String lastName, String phoneNumber, String ZIPCode) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.PhoneNumber = phoneNumber;
        this.ZIPCode = ZIPCode;
    }
}
