package com.projectfirebase.soen341.root;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void UserDefaultConstructorTest() {
        User user = new User();

        assertEquals(user.FirstName, "");
        assertEquals(user.LastName, "");
        assertEquals(user.email, "");
        assertEquals(user.PhoneNumber, "");
        assertEquals(user.ZIPCode, "");
    }

    @Test
    public void UserConstructorTest() {
        String FirstName = "FirstName";
        String LastName = "LastName";
        String email = "Email";
        String PhoneNumber = "PhoneNumber";
        String ZIPCode = "ZIPCode";
        User userTest = new User(FirstName, LastName, email,PhoneNumber, ZIPCode);

        assertEquals(FirstName, userTest.FirstName);
        assertEquals(LastName, userTest.LastName);
        assertEquals(email, userTest.email);
        assertEquals(PhoneNumber, userTest.PhoneNumber);
        assertEquals(ZIPCode, userTest.ZIPCode);
    }
}