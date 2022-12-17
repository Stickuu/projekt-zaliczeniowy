package com.example.projekt_zaliczeniowy.database;

import static com.example.projekt_zaliczeniowy.constants.DatabaseConstants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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

        long insert = db.insert(Users.TABLE_NAME, null, cv);

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
}
