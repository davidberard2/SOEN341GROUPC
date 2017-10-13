package com.projectfirebase.soen341.root;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class UserTest {

    @Test
    public void UserDefaultConstructorTest() {
        User user = new User();

        assertTrue(user.FirstName == "");
        assertTrue(user.LastName == "");
        assertTrue(user.PhoneNumber == "");
        assertTrue(user.ZIPCode == "");
    }

    @Test
    public void UserConstructorTest() {
        String FirstName = "FirstName";
        String LastName = "LastName";
        String PhoneNumber = "PhoneNumber";
        String ZIPCode = "ZIPCode";
        User userTest = new User(FirstName, LastName, PhoneNumber, ZIPCode);

        assertTrue(FirstName == userTest.FirstName);
        assertTrue(LastName == userTest.LastName);
        assertTrue(PhoneNumber == userTest.PhoneNumber);
        assertTrue(ZIPCode == userTest.ZIPCode);
    }
}