package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminDashBoard extends AppCompatActivity {

    Button btn1;
    Button btn2;
    Button btn3;
    String user_name = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);

        btn1 = findViewById(R.id.btn_existing_categories);
        btn2 = findViewById(R.id.btn_new_categories);
        btn3 = findViewById(R.id.btn_log_out);

        user_name = getIntent().getStringExtra("user_name");

        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AdminDashBoard.this, QuizCategory.class);
                intent.putExtra("user_name" , user_name);
                startActivity(intent);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AdminDashBoard.this, CreateNewCategory.class);
                startActivity(intent);

            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AdminDashBoard.this, LoginActivity.class);
                startActivity(intent);

            }
        });

    }

}