package com.example.projekt_zaliczeniowy.constants;

import android.provider.BaseColumns;

public final class DatabaseConstants {

    private DatabaseConstants() {}

    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String FIRSTNAME_COLUMN = "firstName";
        public static final String LASTNAME_COLUMN = "lastName";
        public static final String EMAIL_COLUMN = "email";
        public static final String PASSWORD_COLUMN = "password";
        public static final String PHONE_NUMBER_COLUMN = "phoneNumber";
    }

    public static class Products implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String PRODUCT_NAME_COLUMN = "productName";
        public static final String PRODUCT_DESCRIPTION_COLUMN = "productDescription";
        public static final String PRICE_COLUMN = "price";
        public static final String IMAGE_NAME_COLUMN = "imageName";
    }

    public static class Orders implements BaseColumns {
        public static final String TABLE_NAME = "orders";
        public static final String PRODUCTS_LIST_COLUMN = "productsList";
        public static final String DATE_COLUMN = "date";
        public static final String USER_ID_COLUMN = "userID";
        public static final String ORDER_UNIQUE_NUMBER_COLUMN = "orderUniqueNumber";
    }

    public static final String CREATE_USERS_TABLE = "CREATE TABLE " + Users.TABLE_NAME +
            " (" + Users._ID + " INTEGER PRIMARY KEY, " +
            Users.FIRSTNAME_COLUMN + " TEXT, " +
            Users.LASTNAME_COLUMN + " TEXT, " +
            Users.EMAIL_COLUMN + " TEXT, " +
            Users.PASSWORD_COLUMN + " TEXT, " +
            Users.PHONE_NUMBER_COLUMN + " TEXT)";

    public static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + Products.TABLE_NAME +
            " ( " + Products._ID + " INTEGER PRIMARY KEY, " +
            Products.PRODUCT_NAME_COLUMN + " TEXT, " +
            Products.PRODUCT_DESCRIPTION_COLUMN + " TEXT, " +
            Products.PRICE_COLUMN + " INTEGER, " +
            Products.IMAGE_NAME_COLUMN + " TEXT)";

    public static final String CREATE_ORDERS_TABLE = "CREATE TABLE " + Orders.TABLE_NAME +
            " ( " + Orders._ID + " INTEGER PRIMARY KEY, " +
            Orders.PRODUCTS_LIST_COLUMN + " TEXT, " +
            Orders.DATE_COLUMN + " INTEGER, " +
            Orders.USER_ID_COLUMN + " INTEGER, " +
            Orders.ORDER_UNIQUE_NUMBER_COLUMN + " INTEGER)";

    public static final String DELETE_TABLE = "DELETE TABLE IF EXISTS ";
}
