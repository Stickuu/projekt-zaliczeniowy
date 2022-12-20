package com.example.projekt_zaliczeniowy.models;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderModel {

    private int id;
    private String productsIDListString;
    private int userID;
    private int dateUnix;

    public OrderModel(int id, String productsList, int userID, int date) {
        this.id = id;
        this.productsIDListString = productsList;
        this.userID = userID;
        this.dateUnix = date;
    }

    public OrderModel(String productsList, int userID, int date) {
        this.productsIDListString = productsList;
        this.userID = userID;
        this.dateUnix = date;
    }

    public Instant getConvertedUnixDate() {
        return Instant.ofEpochSecond(dateUnix);
    }

    public List<Integer> returnProductsIDList() {
        return Arrays.asList(productsIDListString.split(",")).stream()
                .map(id -> Integer.valueOf(id))
                .collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductsIDListString() {
        return productsIDListString;
    }

    public void setProductsIDListString(String productsIDListString) {
        this.productsIDListString = productsIDListString;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getDateUnix() {
        return dateUnix;
    }

    public void setDateUnix(int dateUnix) {
        this.dateUnix = dateUnix;
    }
}
