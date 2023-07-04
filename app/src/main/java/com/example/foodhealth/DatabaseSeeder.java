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

        values.put("name", "Nasi Goreng");
        values.put("price", 15000);
        values.put("image", R.drawable.nasi_goreng);
        database.insert("products", null, values);

        values.put("name", "Ayam Goreng");
        values.put("price", 23000);
        values.put("image", R.drawable.ayam);
        database.insert("products", null, values);

        values.put("name", "Bihun Goreng");
        values.put("price", 14000);
        values.put("image", R.drawable.bihun);
        database.insert("products", null, values);

        values.put("name", "Mie Goreng");
        values.put("price", 16000);
        values.put("image", R.drawable.mie);
        database.insert("products", null, values);

        values.put("name", "Es Teh");
        values.put("price", 5000);
        values.put("image", R.drawable.teh);
        database.insert("products", null, values);
    }
}
