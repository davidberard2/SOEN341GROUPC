package com.projectfirebase.soen341.root;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ItemDescriptionTest {

    String id = "id1";
    String name = "name";
    double price = 123.456;
    String description = "test";
    String url = "wabadabadingdong";
    ItemDescription itemTest = new ItemDescription(id, name, price, description, url);
    Listing listTest = new Listing(id, name, price);
    ItemDescription itemTest2 = new ItemDescription(description, url, listTest);

    @Test
    public void ItemDescriptionDefaultConstructorTest() {
        ItemDescription item = new ItemDescription();
        assertEquals(item.getID(), "");
        assertEquals(item.getName(), "");
        assertEquals(item.getPrice(), 0, 0.001);
        assertEquals(item.getURL(), "");
        assertEquals(item.getDescription(), "");

    }

    @Test
    public void ItemDescriptionConstructorTest() {
        String id = "id1";
        String name = "name";
        double price = 123.456;
        String description = "test";
        String url = "wabadabadingdong";
        ItemDescription item = new ItemDescription(id, name, price, description, url);

        assertEquals(item.getID(), id);
        assertEquals(item.getName(), name);
        assertEquals(item.getPrice(), price, 0.001);
        assertEquals(item.getDescription(), description);
        assertEquals(item.getURL(), url);
    }

    @Test
    public void ItemDescriptionOtherConstructorTest(){
        String description = "test";
        String url = "wabadabadingdong";
        ItemDescription item = new ItemDescription(description, url, listTest);
    }

    @Test
    public void getID() throws Exception {
        assertEquals(itemTest.getName(), name);
        assertEquals(itemTest2.getName(), name);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(itemTest.getName(), name);
        assertEquals(itemTest2.getName(), name);
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(itemTest.getPrice(), price, 0.001);
        assertEquals(itemTest2.getPrice(), price, 0.001);
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals(itemTest.getDescription(), description);
        assertEquals(itemTest2.getDescription(), description);
    }

    @Test
    public void getURL() throws Exception {
        assertEquals(itemTest.getURL(), url);
        assertEquals(itemTest2.getURL(), url);
    }


    @Test
    public void setName() throws Exception {
        String newName = "newName";
        itemTest.setName(newName);
        assertEquals(itemTest.getName(), newName);
    }

    @Test
    public void setPrice() throws Exception {
        double newPrice = 123.45;
        itemTest.setPrice(newPrice);
        assertEquals(itemTest.getPrice(), newPrice, 0.001);
    }

    @Test
    public void setDescription() throws Exception{
        String description = "test";
        itemTest.setDescription(description);
        assertEquals(itemTest.getDescription(), description);
    }


    @Test
    public void setURL() throws Exception{
        String url = "wabadabadingdong";
        itemTest.setURL(url);
        assertEquals(itemTest.getURL(), url);
    }
}