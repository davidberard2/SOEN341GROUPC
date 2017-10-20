package com.projectfirebase.soen341.root;

public class ItemDescription extends Listing {
    private String Description;
    private String URL;
    public ItemDescription(){
        super();
        this.Description = "";
        this.URL = "";
    }

    public ItemDescription(String d, String url, Listing listing){
        super(listing.getID(), listing.getName(), listing.getPrice());
        this.Description = d;
        this.URL = url;
    }

    public ItemDescription(String id, String name, double price, String desc, String url){
        super();
        this.setID(id);
        this.setName(name);
        this.setPrice(price);
        this.setDescription(desc);
        this.setURL(url);
    }

    public String getDescription(){
        return this.Description;
    }

    public String getURL(){
        return this.URL;
    }

    public void setDescription(String description){
        this.Description = description;
    }

    public void setURL(String url){
        this.URL = url;
    }
}
