package com.example.foodhealth;

import static com.example.foodhealth.DatabaseHelper.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhealth.databinding.ActivityProfileBinding;
import com.example.foodhealth.databinding.ActivitySignupBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {

    TextView et_name, et_date, et_phone, et_email, et_pass;
    ActivityProfileBinding binding;
    DatabaseHelper databaseHelper;
    String email1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        databaseHelper = new DatabaseHelper(this);

        et_name = findViewById(R.id.view_name);
        et_date = findViewById(R.id.view_dob);
        et_phone = findViewById(R.id.view_phone);
        et_email = findViewById(R.id.view_email);
        et_pass = findViewById(R.id.view_pass);

        email1 = getIntent().getStringExtra("email1");
        getUserDetails();

//        String email =
//        Cursor cursor = databaseHelper.readData();
//        if (cursor.getCount()==0) {
//            Toast.makeText(ProfilActivity.this, "", Toast.LENGTH_SHORT).show();
//            if (cursor.moveToNext()) {
//                 String fullname = cursor.getString(1);
//                 String date = cursor.getString(2);
//                 String phone = cursor.getString(3);
//                 String email = cursor.getString(4);
//                 String password = cursor.getString(5);
//
//                 et_name.setText(fullname);
//                 et_date.setText(date);
//                 et_phone.setText(phone);
//                 et_email.setText(email);
//                 et_pass.setText(password);
//            }
//        }

//        String email = binding.viewEmail.getText().toString();
//        String password = binding.viewPass.getText().toString();
//        if (email.equals("") || password.equals("")) {
//            Toast.makeText(ProfilActivity.this, "No Data is Available", Toast.LENGTH_SHORT).show();
//        } else {
//            Cursor res = databaseHelper.readData(email, password);
//            if (res.moveToNext()) {
//                String fullname = res.getString(1);
//                String date = res.getString(2);
//                String phone = res.getString(3);
//
//                et_name.setText(fullname);
//                et_date.setText(date);
//                et_phone.setText(phone);
//                et_email.setText(email);
//                et_pass.setText(password);
//            }
//        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        bottomNavigationView.setSelectedItemId(R.id.navprof);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navhome:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    return true;
                case R.id.navprof:
                    return true;
                case R.id.navcart:
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    finish();
                    return true;
                case R.id.navsupp:
                    startActivity(new Intent(getApplicationContext(), SupportActivity.class));
                    finish();
                    return true;
            }
            return false;
        });
    }

    public void getUserDetails() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<User> users = databaseHelper.readData(email1);
        User user = users.get(0);

        et_name.setText(user.getFullname());
        et_date.setText(user.getDate());
        et_phone.setText(user.getPhone());
        et_email.setText(user.getEmail());
        et_pass.setText(user.getPassword());
    }
}