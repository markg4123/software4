package com.example.softwarepatterns;

public class Review {
    String  review, itemName, itemManufacturer;
    float stars;
    public Review(){

    }

    public Review(float stars, String review, String itemName, String itemManufacturer) {
        this.stars = stars;
        this.review = review;
        this.itemName= itemName;
        this.itemManufacturer = itemManufacturer;
    }

    public String getItemManufacturer() {
        return itemManufacturer;
    }

    public void setItemManufacturer(String itemManufacturer) {
        this.itemManufacturer = itemManufacturer;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
