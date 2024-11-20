package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {
    private EditText email;
    private EditText user_name;
    private EditText password;
    private EditText confirm_pass;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email = findViewById(R.id.et_email);
        user_name = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        confirm_pass = findViewById(R.id.et_confirm_password);

        mAuth = FirebaseAuth.getInstance();

    }

    public void clickedLogin(View view) {
        Toast.makeText(CreateAccount.this, "Directing to Log in page",
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
        startActivity(intent);
    }

    public void clickedSignup(View view) {
        String mail = email.getText().toString().trim();
        String username = user_name.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirm = confirm_pass.getText().toString().trim();

        if (mail.isEmpty() || username.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(CreateAccount.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirm)) {
            Toast.makeText(CreateAccount.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

//        CreatingDatabase db_connection = new CreatingDatabase(CreateAccount.this);
//        boolean flag = db_connection.addSignupInfo(mobile, username, pass);
//
//        if (flag) {
//            Toast.makeText(CreateAccount.this, "You have successfully signed up. Please log in to continue.",
//                    Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(CreateAccount.this, "Username or phone number already exists. Please try a different one.",
//                    Toast.LENGTH_SHORT).show();
//        }


        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(CreateAccount.this, "You have successfully signed up. Please log in to continue.",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
                        startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(CreateAccount.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            });

    }
}
