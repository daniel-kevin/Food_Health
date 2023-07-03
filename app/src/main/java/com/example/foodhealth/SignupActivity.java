package com.example.foodhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    EditText edt_user, edt_date, edt_phone, edt_email,edt_pass;
    Button btn_sign;
    CheckBox cbox;
    TextView txt_login;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-signup-7b7c9-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edt_user = findViewById(R.id.et_user);
        edt_date = findViewById(R.id.et_date);
        edt_phone = findViewById(R.id.et_phone);
        edt_email = findViewById(R.id.et_mail);
        edt_pass = findViewById(R.id.et_pas);
        btn_sign = findViewById(R.id.bt_sign);
        txt_login = findViewById(R.id.txt_lgn);
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

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //data
                final String fullname = edt_user.getText().toString();
                final String date = edt_date.getText().toString();
                final String phone = edt_phone.getText().toString();
                final String email = edt_email.getText().toString();
                final String password = edt_pass.getText().toString();

                if (fullname.isEmpty() || date.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(phone)){
                                Toast.makeText(SignupActivity.this, "Phone is already", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseReference.child("user").child(phone).child("fullname").setValue(fullname);
                                databaseReference.child("user").child(phone).child("date").setValue(date);
                                databaseReference.child("user").child(phone).child("email").setValue(email);
                                databaseReference.child("user").child(phone).child("password").setValue(password);

                               Toast.makeText(SignupActivity.this, "Register Succesfully", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                               finish();
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
