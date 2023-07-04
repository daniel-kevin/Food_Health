package com.example.foodhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText edt_phone,edt_pass;
    Button btn_login;
    CheckBox cbox;
    TextView txt_sign;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-signup-7b7c9-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         edt_phone = findViewById(R.id.et_phone);
         edt_pass = findViewById(R.id.et_pass);
         btn_login = findViewById(R.id.bt_login);
         txt_sign = findViewById(R.id.txt_signup);
         cbox = findViewById(R.id.c_box);

         cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 if (!b) {
                     edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                 } else {
                     edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                 }
             }
         });

         txt_sign.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                 startActivity(i);
                 finish();
             }
         });

         btn_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String phone, password;
                 phone = String.valueOf(edt_phone.getText());
                 password = String.valueOf(edt_pass.getText());

                 if (phone.isEmpty() || password.isEmpty()){
                     Toast.makeText(LoginActivity.this, "Please Input Your phone and Password!", Toast.LENGTH_SHORT).show();
                 } else {
                     databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             System.out.println(snapshot);
                             if (snapshot.hasChild(phone)){
                                 String getPasword = snapshot.child(phone).child("password").getValue(String.class);

                                 if (getPasword.equals(password)){

//                                     String user = snapshot.child(phone).child("fullname").getValue(String.class);
//                                     String date = snapshot.child(phone).child("date").getValue(String.class);
//                                     String email = snapshot.child(phone).child("email").getValue(String.class);
//
//                                     Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
//                                     intent.putExtra("fullname", user);
//                                     intent.putExtra("date", date);
//                                     intent.putExtra("phone", phone);
//                                     intent.putExtra("email", email);
//                                     intent.putExtra("password", password);
//                                     startActivity(intent);

                                     Toast.makeText(LoginActivity.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                                     startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                     finish();
                                 }  else {
                                     Toast.makeText(LoginActivity.this, "Password Invalid", Toast.LENGTH_SHORT).show();
                                 }
                             } else {
                                 Toast.makeText(LoginActivity.this, "Check Your Phone and Password", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });
                 }
             }
         });
    }
}
