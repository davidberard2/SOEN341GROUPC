package com.projectfirebase.soen341.root;

public class Listing {
	private String ID;
	private String Name;
	private double Price;
	private String ImageURL;
	private int Category;
	private int SubCategory;

	public Listing(){
		this.ID = "";
		this.Name = "";
		this.Price = 0;
		this.ImageURL = "";
		this.Category = 0;
		this.SubCategory = 0;
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

	public Listing(String id, String name, double price, String imageURL, int category, int subCategory) {
		this.ID = id;
		this.Name = name;
		this.Price = price;
		this.ImageURL = imageURL;
		this.Category = category;
		this.SubCategory = subCategory;
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

	public int getCategory() { return this.Category; }

	public int getSubCategory() { return this.SubCategory; }

	public void setName(String name) {
		this.Name = name;
	}

	public void setPrice(double price) {
		this.Price = price;
	}

	public void setImageURL(String url){
		this.ImageURL = url;
	}

	public void setCategory(int category) { this.Category = category; }

	public void setSubCategory(int subCategory) { this.SubCategory = subCategory; }
}
