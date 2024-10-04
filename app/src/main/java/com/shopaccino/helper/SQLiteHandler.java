package com.shopaccino.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    // Login table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_PRODUCTS = "products";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CUSTOMER_ID = "customerId";
    private static final String KEY_CUSTOMER_NAME = "customerName";
    private static final String KEY_CUSTOMER_EMAIL = "customerEmail";

    private static final String KEY_PRODUCT_ID = "productId";

    public SQLiteHandler(Context mContext, String dbName){
        super(mContext, dbName, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CUSTOMER_ID + " TEXT,"
                + KEY_CUSTOMER_NAME + " TEXT,"
                + KEY_CUSTOMER_EMAIL + " TEXT"
                + ")";

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_PRODUCT_ID + " TEXT"
                + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String customerId, String customerName, String customerEmail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CUSTOMER_ID, customerId); // Name
        values.put(KEY_CUSTOMER_NAME, customerName); // Email
        values.put(KEY_CUSTOMER_EMAIL, customerEmail); // Email
       /* values.put(KEY_DISCOUNT_PERCENT, discountPercent);
        values.put(KEY_IS_WHOLESALER, isWholeSaler);*/

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("customerId", cursor.getString(1));
            user.put("customerName", cursor.getString(2));
            user.put("customerEmail", cursor.getString(3));
            /*user.put("discountPercent", cursor.getString(4));
            user.put("isWholeSaler", cursor.getString(5));*/
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.delete(TABLE_PRODUCTS, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void addProduct(String productId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, productId); // Name

        // Inserting Row
        long id = db.insert(TABLE_PRODUCTS, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New product inserted into sqlite: " + id);
    }

    public ArrayList<String> getProductIds(String productId) {
        ArrayList<String> products = new ArrayList<>();

        String selectQuery = "SELECT productId" +
                " FROM " + TABLE_PRODUCTS +
                " WHERE " + KEY_PRODUCT_ID + " != '" + productId.trim() + "'" +
                " ORDER BY id desc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row

        while (cursor.moveToNext()) {
            products.add(cursor.getString(0));
        }

        cursor.close();
        //db.close();
        return products;
    }

    public void deleteProduct(String productId) {
        String condition = "productId = ?";
        String[] conditionArgs = new String[]{productId};

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PRODUCTS, condition, conditionArgs);

        //db.close();
        Log.d(TAG, "product deleted");
    }
}
