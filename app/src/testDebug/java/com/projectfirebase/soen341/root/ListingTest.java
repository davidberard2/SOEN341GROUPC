package com.projectfirebase.soen341.root;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ListingTest {

    String name = "name";
    double price = 123.456;
    Listing listingTest = new Listing(name, price);

    @Test
    public void ListingDefaultConstructorTest() {
        Listing listing = new Listing();
        assertTrue(listing.getName() == "");
        assertTrue(listing.getPrice() == 0);
    }

    @Test
    public void ListingConstructorTest() {
        String name = "name";
        double price = 123.456;
        Listing listing = new Listing(name, price);

        assertTrue(listing.getName() == name);
        assertTrue(listing.getPrice() == price);
    }

    @Test
    public void getName() throws Exception {
        assertTrue(listingTest.getName() == name);
    }

    @Test
    public void getPrice() throws Exception {
        assertTrue(listingTest.getPrice() == price);
    }

    @Test
    public void setName() throws Exception {
        String newName = "newName";
        listingTest.setName(newName);
        assertTrue(listingTest.getName() == newName);
    }

    @Test
    public void setPrice() throws Exception {
        double newPrice = 123.45;
        listingTest.setPrice(newPrice);
        assertTrue(listingTest.getPrice() == newPrice);
    }

}