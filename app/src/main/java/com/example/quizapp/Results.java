package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Results extends AppCompatActivity {
    private TextView tv_total_correct;
    String user_name="";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tv_total_correct = findViewById(R.id.tv_total_correct);

        // Retrieve the total correct answers from the intent
        int totalCorrect = getIntent().getIntExtra("totalCorrect", 0);
        tv_total_correct.setText("Total Correct Answers: " + totalCorrect);
    }

    public void clickedContinue(View view) {
        // Navigate to QuizCategory
        Intent intent = new Intent(Results.this, QuizCategory.class);
        intent.putExtra("user_name" , user_name);
        startActivity(intent);
        finish();
    }
}