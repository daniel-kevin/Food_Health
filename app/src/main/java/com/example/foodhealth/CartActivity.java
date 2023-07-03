package com.example.foodhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
            bottomNavigationView.setSelectedItemId(R.id.navcart);

            bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()){
                    case R.id.navhome:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        return true;
                    case R.id.navprof:
                        startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                        finish();
                        return true;
                    case R.id.navcart:
                        return true;
                    case R.id.navsupp:
                        startActivity(new Intent(getApplicationContext(), SupportActivity.class));
                        finish();
                        return true;
                }
                return false;
            } );
    }
}