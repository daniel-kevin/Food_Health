package com.example.foodhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-signup-7b7c9-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
            bottomNavigationView.setSelectedItemId(R.id.navhome);

            bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()){
                    case R.id.navhome:
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
                        startActivity(new Intent(getApplicationContext(), SupportActivity.class));
                        finish();
                        return true;
                }
                return false;
            } );
    }
}