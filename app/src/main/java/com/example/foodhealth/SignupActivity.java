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

import com.example.foodhealth.databinding.ActivitySignupBinding;
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
    ActivitySignupBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.btSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = binding.etUser.getText().toString();
                String date = binding.etDate.getText().toString();
                String phone = binding.etPhone.getText().toString();
                String email = binding.etMail.getText().toString();
                String password = binding.etPas.getText().toString();

                if (email.equals("") || password.equals("") || date.equals("") || phone.equals("") || fullname.equals("")) {
                    Toast.makeText(SignupActivity.this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUserEmail = databaseHelper.checkEmail(email);
                    if (checkUserEmail == false) {
                        Boolean insert = databaseHelper.insertData(fullname, date, phone, email, password);
                        if (insert == true) {
                            Toast.makeText(SignupActivity.this, "Signup Succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignupActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "User Already Exists, Please Login", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.txtLgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        EditText ed = findViewById(R.id.et_pas);
        CheckBox c = findViewById(R.id.c_box);
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    ed.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    ed.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }
}