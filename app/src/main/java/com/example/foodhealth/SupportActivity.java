package com.example.foodhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SupportActivity extends AppCompatActivity {
    private List<CartItem> cartItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("cartItems")) {
            cartItems = (List<CartItem>) intent.getSerializableExtra("cartItems");
        } else {
            cartItems = new ArrayList<>();
        }
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
                        Intent cartIntent = new Intent(getApplicationContext(), CartActivity.class);
                        cartIntent.putExtra("cartItems", (Serializable) cartItems);
                        startActivity(cartIntent);
                        finish();
                        return true;
                    case R.id.navsupp:
                        return true;
                }
                return false;
            } );
    }
}
