package com.projectfirebase.soen341.root;

public class Listing {
	private String ID;
	private String name;
	private double price;
	private String imageURL;

	public Listing(){
		this.ID = "";
		this.name = "";
		this.price = 0;
		this.imageURL = "";
	}

	public Listing(String id, String name, double price, String imageURL) {
		this.ID = id;
		this.name = name;
		this.price = price;
		this.imageURL = imageURL;
	}

	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getImageURL(){
		return this.imageURL;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setImageURL(String url){
		this.imageURL = url;
	}
}
