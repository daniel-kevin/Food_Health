package com.example.foodhealth;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseSeeder {
    private SQLiteDatabase database;

    public DatabaseSeeder(SQLiteDatabase database) {
        this.database = database;
    }

    public void seedProducts() {
        // Clear existing data (optional)
        database.execSQL("DELETE FROM products");

        // Insert new products
        ContentValues values = new ContentValues();

        values.put("name", "Product 1");
        values.put("price", 10.99);
        values.put("image", "product1.jpg");
        database.insert("products", null, values);

        values.put("name", "Product 2");
        values.put("price", 19.99);
        values.put("image", "product2.jpg");
        database.insert("products", null, values);

        // Add more products as needed

        // Close the database
//        database.close();
    }
}
