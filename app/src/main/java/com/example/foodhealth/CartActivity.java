package com.example.foodhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.graphics.Color;


public class CartActivity extends AppCompatActivity {
    private List<CartItem> cartItemList;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;
    private ImageView qrCodeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Get the cart items from the intent
        // Get the cart items from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("cartItems")) {
            cartItemList = (List<CartItem>) intent.getSerializableExtra("cartItems");
        } else {
            cartItemList = new ArrayList<>();
        }



        // Set up the RecyclerView and CartAdapter
        recyclerView = findViewById(R.id.recyclerViewCartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartItemList, CartActivity.this);
        recyclerView.setAdapter(cartAdapter);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        qrCodeImageView = findViewById(R.id.qrCodeImageView);

        double totalPrice = calculateTotalPrice();
        setTotalPrice(totalPrice);
        // Set the total price text
        totalPriceTextView.setText(String.valueOf(totalPrice));

        // Generate QR code
        generateQRCode(totalPrice);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        bottomNavigationView.setSelectedItemId(R.id.navcart);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
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
        });
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (CartItem cartItem : cartItemList) {
            totalPrice += cartItem.getQuantity() * cartItem.getPrice();
        }
        return totalPrice;
    }

    private void generateQRCode(double totalPrice) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(String.valueOf(totalPrice), BarcodeFormat.QR_CODE, 200, 200);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void setTotalPrice(double totalPrice) {
        String totalText = getResources().getString(R.string.total_price_format, totalPrice);
        totalPriceTextView.setText(totalText);
    }


}