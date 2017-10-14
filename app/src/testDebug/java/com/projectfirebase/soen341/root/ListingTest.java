package com.projectfirebase.soen341.root;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ListingTest {

    String name = "name";
    double price = 123.456;
    Listing listingTest = new Listing(name, price);

    @Test
    public void ListingDefaultConstructorTest() {
        Listing listing = new Listing();
        assertEquals(listing.getName(), "");
        assertEquals(listing.getPrice(), 0, 0.001);
    }

    @Test
    public void ListingConstructorTest() {
        String name = "name";
        double price = 123.456;
        Listing listing = new Listing(name, price);

        assertEquals(listing.getName(), name);
        assertEquals(listing.getPrice(), price, 0.001);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(listingTest.getName(), name);
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(listingTest.getPrice(), price, 0.001);
    }

    @Test
    public void setName() throws Exception {
        String newName = "newName";
        listingTest.setName(newName);
        assertEquals(listingTest.getName(), newName);
    }

    @Test
    public void setPrice() throws Exception {
        double newPrice = 123.45;
        listingTest.setPrice(newPrice);
        assertEquals(listingTest.getPrice(), newPrice, 0.001);
    }

}