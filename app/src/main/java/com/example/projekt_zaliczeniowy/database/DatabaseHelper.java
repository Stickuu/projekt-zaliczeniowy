package com.example.projekt_zaliczeniowy.database;

import static com.example.projekt_zaliczeniowy.constants.DatabaseConstants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(UserModel userModel) {
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

    public boolean deleteOne(UserModel userModel) {
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
                int userID = cursor.getInt(0);
                String userFirstName = cursor.getString(1);
                String userLastName = cursor.getString(2);
                String userEmail = cursor.getString(3);
                String userPassword = cursor.getString(4);
                String phoneNumber = cursor.getString(5);

                UserModel newUser = new UserModel(
                        userID,
                        userFirstName,
                        userLastName,
                        userEmail,
                        userPassword,
                        phoneNumber
                );

                users.add(newUser);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return users;
    }
}
