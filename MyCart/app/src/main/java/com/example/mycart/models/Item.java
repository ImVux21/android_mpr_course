package com.example.mycart.models;

public class Item {
    private int id;
    private String thumbnail;
    private String name;
    private String category;
    private int unitPrice;

    public Item(int id, String thumbnail, String name, String category, int unitPrice) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String convertToVietNamDong() {
        String price = String.valueOf(unitPrice);
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (int i = price.length() - 1; i >= 0; i--) {
            stringBuilder.append(price.charAt(i));
            count++;
            if (count == 3 && i != 0) {
                stringBuilder.append(".");
                count = 0;
            }
        }
        return stringBuilder.reverse().toString();
    }
}
