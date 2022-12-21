package com.example.projekt_zaliczeniowy.models;

import android.util.Log;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class OrderModel {

    private int id;
    private String productsIDListString;
    private int userID;
    private int dateUnix;
    private int orderUniqueNumber;

    public OrderModel(int id, String productsList, int userID, int date, int orderUniqueNumber) {
        this.id = id;
        this.productsIDListString = productsList;
        this.userID = userID;
        this.dateUnix = date;
        this.orderUniqueNumber = orderUniqueNumber;
    }

    public OrderModel(String productsList, int userID, int date) {
        this.productsIDListString = productsList;
        this.userID = userID;
        this.dateUnix = date;
        this.orderUniqueNumber = generateUniqueOrderNumber();
    }

    public Instant getConvertedUnixDate() {
        return Instant.ofEpochSecond(dateUnix);
    }

    public List<Integer> returnProductsIDList() {
        return Arrays.asList(productsIDListString.split(",")).stream()
                .map(id -> Integer.valueOf(id))
                .collect(Collectors.toList());
    }

    public int generateUniqueOrderNumber() {
        Random random = new Random();
        int uniqueNumber = 10000000 + random.nextInt(90000000);

        return uniqueNumber;
    }

    public int getOrderUniqueNumber() {
        return orderUniqueNumber;
    }

    public void setOrderUniqueNumber(int orderUniqueNumber) {
        this.orderUniqueNumber = orderUniqueNumber;
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
