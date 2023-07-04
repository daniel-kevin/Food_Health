package com.example.foodhealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Signup.db";

    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE = "image";

    public DatabaseHelper(@Nullable Context context){
        super(context,"Signup.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table allusers(email TEXT primary key, password TEXT, date TEXT, phone TEXT, fullname TEXT)");
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_IMAGE + " TEXT"
                + ")";
        MyDatabase.execSQL(CREATE_PRODUCTS_TABLE);
        DatabaseSeeder seeder = new DatabaseSeeder(MyDatabase);
        seeder.seedProducts();
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists allusers");
        MyDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
    }

    public Boolean insertData(String email, String password, String date, String phone, String fullname){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("date", date);
        contentValues.put("phone", phone);
        contentValues.put("fullname", fullname);
        long result = MyDatabase.insert("allusers", null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail (String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ?", new String[] {email});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword (String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ? and password = ?", new String[] {email, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<User> readData(String email1) {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM allusers WHERE email='"+email1+"'";
        Cursor cursor = MyDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String fullname = cursor.getString(1);
            String date = cursor.getString(2);
            String phone = cursor.getString(3);
            String email = cursor.getString(4);
            String password = cursor.getString(5);

            User user = new User();
            user.setFullname(fullname);
            user.setDate(date);
            user.setPhone(phone);
            user.setEmail(email);
            user.setPassword(password);

            users.add(user);
        }
        return users;
    }
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_IMAGE, product.getImagePath());

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public List<Product> getAllProducts(SQLiteDatabase db) {
        List<Product> productList = new ArrayList<>();
        Cursor cursor = null;

        try {
            String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int priceIndex = cursor.getColumnIndex(COLUMN_PRICE);
                int imageIndex = cursor.getColumnIndex(COLUMN_IMAGE);

                do {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    double price = cursor.getDouble(priceIndex);
                    String imagePath = cursor.getString(imageIndex);

                    Product product = new Product(id, name, price, imagePath);
                    productList.add(product);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        System.out.println(productList.size());
        return productList;
    }
}
