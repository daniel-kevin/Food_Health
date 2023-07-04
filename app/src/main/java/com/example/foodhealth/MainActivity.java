package com.example.foodhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-signup-7b7c9-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();
        productList = databaseHelper.getAllProducts(db);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);
//        System.out.println('test');
        System.out.println(productList);
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        productList = databaseHelper.getAllProducts(db);
        productAdapter.setProductList(productList);
        productAdapter.notifyDataSetChanged();
    }
}
