package com.example.projekt_zaliczeniowy.database;

import static com.example.projekt_zaliczeniowy.constants.DatabaseConstants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.projekt_zaliczeniowy.models.OrderModel;
import com.example.projekt_zaliczeniowy.models.ProductModel;
import com.example.projekt_zaliczeniowy.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "shop.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DELETE_TABLE + Users.TABLE_NAME);
        db.execSQL(DELETE_TABLE + Products.TABLE_NAME);
        db.execSQL(DELETE_TABLE + Orders.TABLE_NAME);
    }

//    users table
    public boolean addUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        
        cv.put(Users.FIRSTNAME_COLUMN, userModel.getFirstName());
        cv.put(Users.LASTNAME_COLUMN, userModel.getLastName());
        cv.put(Users.EMAIL_COLUMN, userModel.getEmail());
        cv.put(Users.PASSWORD_COLUMN, userModel.getPassword());
        cv.put(Users.PHONE_NUMBER_COLUMN, userModel.getPhoneNumber());

        long insert = db.insert(Users.TABLE_NAME, null, cv);

        db.close();

        if(insert == -1) return false;

        return true;
    }

    public boolean deleteUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + Users.TABLE_NAME + " WHERE " + Users._ID + " = " + userModel.getId();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) return true;

        return false;
    }

    public UserModel getUserByEmailAndPassword(UserModel userModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Users.TABLE_NAME + " WHERE " + Users.EMAIL_COLUMN + " = '" + userModel.getEmail() + "' AND " + Users.PASSWORD_COLUMN + " = '" + userModel.getPassword() + "'" ;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) return new UserModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );

        return null;
    }

    public  UserModel getUserByEmailAndPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Users.TABLE_NAME + " WHERE " + Users.EMAIL_COLUMN + " = '" + email + "' AND " + Users.PASSWORD_COLUMN + " = '" + password + "'" ;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) return new UserModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );

        return null;
    }

    public UserModel getUserByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Users.TABLE_NAME + " WHERE " + Users._ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) return new UserModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );

        return null;
    }

    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();

        String query = "SELECT * FROM " + Users.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                users.add(new UserModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return users;
    }

//    products table

    public boolean addProduct(ProductModel productModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Products.PRODUCT_NAME_COLUMN, productModel.getName());
        cv.put(Products.PRODUCT_DESCRIPTION_COLUMN, productModel.getDescription());
        cv.put(Products.PRICE_COLUMN, productModel.getPrice());
        cv.put(Products.IMAGE_NAME_COLUMN, productModel.getImageName());

        long insert = db.insert(Products.TABLE_NAME, null, cv);

        db.close();

        if(insert == -1) return false;

        return true;
    }

    public ProductModel getProductByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Products.TABLE_NAME + " WHERE " + Products._ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) return new ProductModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4)
        );

        return null;
    }

    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();

        String query = "SELECT * FROM " + Products.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                products.add(new ProductModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return products;
    }

    // orders table
    public boolean addOrder(OrderModel orderModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Orders.PRODUCTS_LIST_COLUMN, orderModel.getProductsIDListString());
        cv.put(Orders.DATE_COLUMN, orderModel.getDateUnix());
        cv.put(Orders.USER_ID_COLUMN, orderModel.getUserID());
        cv.put(Orders.ORDER_UNIQUE_NUMBER_COLUMN, orderModel.getOrderUniqueNumber());
        cv.put(Orders.ORDER_TOTAL_PRICE_COLUMN, orderModel.getTotalPrice());

        long insert = db.insert(Orders.TABLE_NAME, null, cv);

        db.close();

        if(insert == -1) return false;

        return true;
    }

    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();

        String query = "SELECT * FROM " + Orders.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                orders.add(new OrderModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return orders;
    }

    public List<OrderModel> getUserOrders(int userID) {
        List<OrderModel> orders = new ArrayList<>();

        String query = "SELECT * FROM " + Orders.TABLE_NAME + " WHERE " + Orders.USER_ID_COLUMN + " = " + userID;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        Log.d("CUROSR", cursor.toString());

        if (cursor.moveToFirst()) {
            do {
                orders.add(new OrderModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return orders;
    }
}
