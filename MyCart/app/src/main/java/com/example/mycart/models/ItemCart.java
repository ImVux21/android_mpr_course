package com.example.mycart.models;

public class ItemCart {
    private int id;
    private String name;
    private String category;
    private String thumbnail;
    private int price;
    private int quantity;

    public ItemCart(int id, String name, String category, String thumbnail, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.thumbnail = thumbnail;
        this.price = price;
        this.quantity = quantity;
    }

    public ItemCart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
