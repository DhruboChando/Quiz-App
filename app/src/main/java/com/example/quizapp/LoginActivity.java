package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etPhone = findViewById(R.id.et_email_mobile);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = etPhone.getText().toString();
                String password = etPassword.getText().toString();

                if(phoneNo.equals("admin") && password.equals("admin")) {
                    Toast.makeText(LoginActivity.this, "Successfully logged into Quiz App\nas an Admin!!!",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, AdminDashBoard.class);
                    intent.putExtra("user_name" , phoneNo);
                    startActivity(intent);

                    return;
                }

                CreatingDatabase db = new CreatingDatabase(LoginActivity.this);

                if(!phoneNo.isEmpty() && !password.isEmpty()) {
                    boolean flag = db.checkLoginInfo(phoneNo,password);

                    if(flag){
                        Toast.makeText(LoginActivity.this, "Successfully logged into Quiz App\nAll the best!!!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, QuizCategory.class);
                        intent.putExtra("user_name" , phoneNo);
                        startActivity(intent);

                    }else {
                        Toast.makeText(LoginActivity.this, "There is some problem in the\ninformation you provided\nPlese try again",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Empty field.\nPlease input valid data!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clickedSignup(View view) {
        Toast.makeText(LoginActivity.this, "Directing to Sign Up page",
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, CreateAccount.class);
        startActivity(intent);

    }

}