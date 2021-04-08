package com.example.softwarepatterns;

public class Order {
    double totalCost;
    int numItems;
    String user,itemsOrdered; //id of user buying the order

    public Order() {
    }

    public Order(double totalCost, int numItems, String user, String itemsOrdered) {
        this.totalCost = totalCost;
        this.numItems = numItems;
        this.user = user;
        this.itemsOrdered = itemsOrdered;
    }

    public String getItemsOrdered() {
        return itemsOrdered;
    }

    public void setItemsOrdered(String itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
