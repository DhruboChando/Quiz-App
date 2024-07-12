package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateQuestions extends AppCompatActivity {

    private EditText question_1;
    private EditText option_1;
    private EditText option_2;
    private EditText option_3;
    private EditText option_4;
    private EditText correct_answer;
    private Button add_btn;
    private CreatingDatabase db;
    private String categoryName = "";
    private int questionNumber = 0;
    private int count = 0; // To keep track of the number of questions added

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_questions);

        // Initialize views
        question_1 = findViewById(R.id.et_question);
        option_1 = findViewById(R.id.et_option_1);
        option_2 = findViewById(R.id.et_option_2);
        option_3 = findViewById(R.id.et_option_3);
        option_4 = findViewById(R.id.et_option_4);
        correct_answer = findViewById(R.id.et_correct_option);
        add_btn = findViewById(R.id.btn_add);

        // Initialize database
        db = new CreatingDatabase(CreateQuestions.this);

        // Get category name and question number from the intent
        Intent intent = getIntent();
        categoryName = intent.getStringExtra("categoryName");
        questionNumber = intent.getIntExtra("questionNumber", 0);

        // Set click listener for the add button
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatingQuestions();
            }
        });
    }

    private void creatingQuestions() {
        // Get input from EditTexts
        String ques_1 = question_1.getText().toString();
        String opt_1 = option_1.getText().toString();
        String opt_2 = option_2.getText().toString();
        String opt_3 = option_3.getText().toString();
        String opt_4 = option_4.getText().toString();
        String correct = correct_answer.getText().toString();

        // Check if any field is empty
        if (ques_1.isEmpty() || opt_1.isEmpty() || opt_2.isEmpty() || opt_3.isEmpty() || opt_4.isEmpty() || correct.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert new question into the database
        boolean flag = db.insertNewQuestion(categoryName, ques_1, opt_1, opt_2, opt_3, opt_4, correct);

        if (flag) {
            count++; // Increment the count of questions added
            Toast.makeText(this, "Question added successfully !!!", Toast.LENGTH_SHORT).show();

            // Check if the required number of questions has been added
            if (count == questionNumber) {
                Toast.makeText(this, questionNumber + " questions added\nCategory creation completed successfully !!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateQuestions.this, QuestionNo1C.class);
                intent.putExtra("categoryName" , categoryName);
                startActivity(intent);
                return;
            }

            // Clear the input fields for the next question
            question_1.getText().clear();
            option_1.getText().clear();
            option_2.getText().clear();
            option_3.getText().clear();
            option_4.getText().clear();
            correct_answer.getText().clear();

        } else {
            // Display error message and clear input fields
            Toast.makeText(this, "Some errors occurred!!!\nPlease try again!", Toast.LENGTH_SHORT).show();
            question_1.getText().clear();
            option_1.getText().clear();
            option_2.getText().clear();
            option_3.getText().clear();
            option_4.getText().clear();
            correct_answer.getText().clear();
        }
    }
}
