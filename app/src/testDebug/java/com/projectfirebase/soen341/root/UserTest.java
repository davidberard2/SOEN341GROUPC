package com.projectfirebase.soen341.root;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserTest {
    final private String EMPTY_FIELD = "";

    @Test
    public void UserDefaultConstructorTest() {
        User user = new User();

        assertEquals(EMPTY_FIELD, user.getFirstName());
        assertEquals(EMPTY_FIELD, user.getLastName());
        assertEquals(EMPTY_FIELD, user.getEmail());
        assertEquals(EMPTY_FIELD, user.getPhoneNumber());
        assertEquals(EMPTY_FIELD, user.getZipCode());
    }

    @Test
    public void UserConstructorTest() {
        String firstName = "FirstName";
        String lastName = "LastName";
        String email = "Email";
        String phoneNumber = "PhoneNumber";
        String zipCode = "ZIPCode";
        User userTest = new User(firstName, lastName, email, phoneNumber, zipCode);

        assertEquals(firstName, userTest.getFirstName());
        assertEquals(lastName, userTest.getLastName());
        assertEquals(email, userTest.getEmail());
        assertEquals(phoneNumber, userTest.getPhoneNumber());
        assertEquals(zipCode, userTest.getZipCode());
    }
}