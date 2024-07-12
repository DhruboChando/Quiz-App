package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAccount extends AppCompatActivity {

    private EditText mobile_no;
    private EditText user_name;
    private EditText password;
    private EditText confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mobile_no = findViewById(R.id.et_email_mobile);
        user_name = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        confirm_pass = findViewById(R.id.et_confirm_password);

    }

    public void clickedLogin(View view) {
        Toast.makeText(CreateAccount.this, "Directing to Log in page",
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
        startActivity(intent);
    }

    public void clickedSignup(View view) {
        String mobile = mobile_no.getText().toString().trim();
        String username = user_name.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirm = confirm_pass.getText().toString().trim();

        if (mobile.isEmpty() || username.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(CreateAccount.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirm)) {
            Toast.makeText(CreateAccount.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        CreatingDatabase db_connection = new CreatingDatabase(CreateAccount.this);
        boolean flag = db_connection.addSignupInfo(mobile, username, pass);

        if (flag) {
            Toast.makeText(CreateAccount.this, "You have successfully signed up. Please log in to continue.",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(CreateAccount.this, "Username or phone number already exists. Please try a different one.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
