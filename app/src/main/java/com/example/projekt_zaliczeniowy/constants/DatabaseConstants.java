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

    public static final String CREATE_USERS_TABLE = "CREATE TABLE " + Users.TABLE_NAME +
            " (" + Users._ID + " INTEGER PRIMARY KEY, " +
            Users.FIRSTNAME_COLUMN + " TEXT, " +
            Users.LASTNAME_COLUMN + " TEXT, " +
            Users.EMAIL_COLUMN + " TEXT, " +
            Users.PASSWORD_COLUMN + " TEXT, " +
            Users.PHONE_NUMBER_COLUMN + " TEXT)";

    public static final String DELETE_TABLE = "DELETE TABLE IF EXISTS";
}