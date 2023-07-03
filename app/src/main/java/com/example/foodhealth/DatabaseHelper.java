package com.example.foodhealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import javax.crypto.Cipher;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Signup.db";

    public DatabaseHelper(@Nullable Context context){
        super(context,"Signup.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table allusers(email TEXT primary key, password TEXT, date TEXT, phone TEXT, fullname TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists allusers");
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
}
