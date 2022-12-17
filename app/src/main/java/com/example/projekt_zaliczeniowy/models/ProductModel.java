package com.example.projekt_zaliczeniowy.models;

public class ProductModel {
    private int id;
    private String name;
    private String description;
    private int price;
    private String imageName;

    public ProductModel(int id, String name, String description, int price, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageName = image;
    }

    public ProductModel(String name, String description, int price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageName = image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
