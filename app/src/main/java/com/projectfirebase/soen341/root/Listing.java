package com.projectfirebase.soen341.root;

public class Listing {
	private String ID;
	private String Name;
	private double Price;
	private String ImageURL;

	public Listing(){
		this.ID = "";
		this.Name = "";
		this.Price = 0;
		this.ImageURL = "";
	}

	public Listing(String id){
		this.ID = id;
	}

	public Listing(String id, String name, double price, String imageURL) {
		this.ID = id;
		this.Name = name;
		this.Price = price;
		this.ImageURL = imageURL;
	}

	public String getID() {
		return ID;
	}

	public String getName() {
		return Name;
	}

	public double getPrice() {
		return Price;
	}

	public String getImageURL(){
		return this.ImageURL;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public void setPrice(double price) {
		this.Price = price;
	}

	public void setImageURL(String url){
		this.ImageURL = url;
	}
}
