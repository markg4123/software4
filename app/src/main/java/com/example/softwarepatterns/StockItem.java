package com.example.softwarepatterns;

public class StockItem {
    String title, manufacturer, category, url, key;
    int price, numStock;

    public StockItem(){

    }

    public StockItem(String title, String manufacturer, String category, String url, int price, int numStock, String key) {
        this.title = title;
        this.manufacturer = manufacturer;
        this.category = category;
        this.url = url;
        this.price = price;
        this.numStock = numStock;
        this.key = key;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumStock() {
        return numStock;
    }

    public void setNumStock(int numStock) {
        this.numStock = numStock;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
