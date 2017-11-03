package com.projectfirebase.soen341.root;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ItemDescriptionTest {

    String id = "id1";
    String name = "name";
    double price = 123.456;
    String description = "test";
    String url = "wabadabadingdong";
    ItemDescription itemTest = new ItemDescription(id, name, price, url, description);
    Listing listTest = new Listing(id, name, price, url);
    ItemDescription itemTest2 = new ItemDescription(listTest, description);

    @Test
    public void ItemDescriptionDefaultConstructorTest() {
        ItemDescription item = new ItemDescription();
        assertEquals(item.getID(), "");
        assertEquals(item.getName(), "");
        assertEquals(item.getPrice(), 0, 0.001);
        assertEquals(item.getImageURL(), "");
        assertEquals(item.getDescription(), "");

    }

    @Test
    public void ItemDescriptionConstructorTest() {
        String id = "id1";
        String name = "name";
        double price = 123.456;
        String description = "test";
        String url = "wabadabadingdong";
        ItemDescription item = new ItemDescription(id, name, price, url, description);

        assertEquals(item.getID(), id);
        assertEquals(item.getName(), name);
        assertEquals(item.getPrice(), price, 0.001);
        assertEquals(item.getDescription(), description);
        assertEquals(item.getImageURL(), url);
    }

    @Test
    public void ItemDescriptionOtherConstructorTest(){
        String description = "test";
        ItemDescription item = new ItemDescription(listTest, description);
        assertEquals(item.getDescription(), description);
        assertEquals(item.getImageURL(), listTest.getImageURL());
        assertEquals(item.getName(), listTest.getName());
        assertEquals(item.getID(), listTest.getID());
        assertEquals(item.getPrice(), listTest.getPrice(), 0.001);
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
        assertEquals(itemTest.getImageURL(), url);
        assertEquals(itemTest2.getImageURL(), url);
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
        itemTest.setImageURL(url);
        assertEquals(itemTest.getImageURL(), url);
    }
}