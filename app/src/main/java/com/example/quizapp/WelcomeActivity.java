package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    public void clickedSignup(View view) {
        Toast.makeText(WelcomeActivity.this, "Sign up to use app", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(WelcomeActivity.this, CreateAccount.class);

        startActivity(intent);
    }

    public void clickedLogin(View view) {
        Toast.makeText(WelcomeActivity.this, "Log in pressed", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);

        startActivity(intent);
    }

    public void signUpFacebook(View view) {
        Toast.makeText(WelcomeActivity.this, "Signing up using FB", Toast.LENGTH_SHORT).show();
    }

    public void signUpTwitter(View view) {
        Toast.makeText(WelcomeActivity.this, "Signing up using TWITTER", Toast.LENGTH_SHORT).show();
    }

    public void signUpGoogle(View view) {
        Toast.makeText(WelcomeActivity.this, "Signing up using GOOGLE", Toast.LENGTH_SHORT).show();
    }
}