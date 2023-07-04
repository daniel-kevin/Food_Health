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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnQuantityChangedListener {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    private List<CartItem> cartItems;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-signup-7b7c9-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();
        productList = databaseHelper.getAllProducts(db);
        cartItems = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(productList, cartItems);
        recyclerView.setAdapter(productAdapter);
        productAdapter.setOnQuantityChangedListener(this);
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
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    intent.putExtra("cartItems", (Serializable) cartItems);
                    startActivity(intent);
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

    @Override
    public void onQuantityIncreased(int position) {
        Product product = productList.get(position);

        // Check if the product is already in the cart
        CartItem existingCartItem = findCartItemById(product.getId());
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
        } else {
            CartItem cartItem = new CartItem(product.getId(), product.getName(), product.getPrice());
            cartItems.add(cartItem);
        }

        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onQuantityDecreased(int position) {
        Product product = productList.get(position);

        // Check if the product is already in the cart
        CartItem existingCartItem = findCartItemById(product.getId());
        if (existingCartItem != null) {
            int quantity = existingCartItem.getQuantity();
            if (quantity > 1) {
                existingCartItem.setQuantity(quantity - 1);
            } else {
                cartItems.remove(existingCartItem);
            }
        }

        productAdapter.notifyDataSetChanged();
    }

    private CartItem findCartItemById(int productId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductId() == productId) {
                return cartItem;
            }
        }
        return null;
    }

}
