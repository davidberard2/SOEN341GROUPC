package com.projectfirebase.soen341.root;

public class ItemDescription extends Listing {
    private String Description;
    public ItemDescription(){
        super();
        this.Description = "";
    }

    public ItemDescription(String d, Listing listing){
        super(listing.getID(), listing.getName(), listing.getPrice(), listing.getImageURL());
        this.Description = d;
    }

    public ItemDescription(String id, String name, double price, String desc, String url){
        super(id);
        this.setName(name);
        this.setPrice(price);
        this.setDescription(desc);
        this.setImageURL(url);
    }

    public String getDescription(){
        return this.Description;
    }

    public void setDescription(String description){
        this.Description = description;
    }

}
