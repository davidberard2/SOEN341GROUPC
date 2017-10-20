package com.projectfirebase.soen341.root;

public class Listing {
	private String ID;
	private String name;
	private double price;

	public Listing(){
		this.ID = "";
		this.name = "";
		this.price = 0;
	}

	public Listing(String id, String name, double price) {
		this.ID = id;
		this.name = name;
		this.price = price;
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

	public void setID(String id){
		this.ID = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
