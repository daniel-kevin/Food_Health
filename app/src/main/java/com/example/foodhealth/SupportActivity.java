package com.example.foodhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
            bottomNavigationView.setSelectedItemId(R.id.navsupp);

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
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        finish();
                        return true;
                    case R.id.navsupp:
                        return true;
                }
                return false;
            } );
    }
}