package com.projectfirebase.soen341.root;

public class ItemDescription extends Listing {
    private String Description;
    private String URL;
    public ItemDescription(){
        super();
        this.Description = "";
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
