package com.projectfirebase.soen341.root;
public class FilterObject {
    private String stringFilter;
    private double minPrice;
    private double maxPrice;
    private int category;
    private int subCategory;

    public FilterObject() {
        this.stringFilter = "";
        this.minPrice = 0;
        this.maxPrice = Double.MAX_VALUE;
        this.category = -1;
        this.subCategory = -1;
    }

    public void setStringFilter(String filter) {
        this.stringFilter = filter.toLowerCase();
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setCategory(int category) { this.category = category; }

    public void setSubCategory(int subCategory) { this.subCategory = subCategory; }

    public boolean isInPriceRange(double price) {
        return price >= this.minPrice && price <= this.maxPrice;
    }

    public boolean isContainedIn(String container) {
        return container.toLowerCase().contains(this.stringFilter);
    }

    public boolean isCategory(int category){
        if(this.category < 0)
            return true;
        else
            return this.category == category;
    }

    public boolean isSubCategory(int subCategory){
        if(this.subCategory < 0)
            return true;
        else
            return this.subCategory == subCategory;
    }

    public void resetFilter() {
        this.stringFilter = "";
        this.minPrice = 0;
        this.maxPrice = Double.MAX_VALUE;
        this.category = -1;
        this.subCategory = -1;
    }
}
