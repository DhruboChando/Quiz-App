package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Questions extends AppCompatActivity {
    private TextView tv_q_no;
    private TextView tv_q;
    private RadioButton radio_1;
    private RadioButton radio_2;
    private RadioButton radio_3;
    private RadioButton radio_4;
    private RadioGroup radioGroup;
    private Button submit_btn;
    private TextView dialog_box;
    private CreatingDatabase db;
    private String category_name = "";
    private TextView title_bar;
    private ArrayList<QuestionDetails> questionDetails;
    private int i = 0;
    private int count = i + 1;
    private Cursor cursor;
    private String correct_answer;
    private int totalCorrect = 0;  // Variable to keep track of total correct answers

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        title_bar = findViewById(R.id.tv_categoryName);
        tv_q_no = findViewById(R.id.tv_question_no);
        tv_q = findViewById(R.id.tv_question);

        radio_1 = findViewById(R.id.option1);
        radio_2 = findViewById(R.id.option2);
        radio_3 = findViewById(R.id.option3);
        radio_4 = findViewById(R.id.option4);
        radioGroup = findViewById(R.id.radio_group);

        submit_btn = findViewById(R.id.btn_submit);
        dialog_box = findViewById(R.id.tv_dialog_box);

        db = new CreatingDatabase(Questions.this);
        category_name = getIntent().getStringExtra("categoryName");
        title_bar.setText(category_name);

        questionDetails = new ArrayList<QuestionDetails>();

        loadQuestion();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadQuestion() {
        // Retrieving questions
        cursor = db.fetchAllQuestions(category_name);

        // Storing data in ArrayList
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String question = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_QUESTION));
                String option1 = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_OPTION1));
                String option2 = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_OPTION2));
                String option3 = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_OPTION3));
                String option4 = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_OPTION4));
                correct_answer = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_CORRECT_ANSWER));

                // Creating objects
                QuestionDetails qd = new QuestionDetails(question, option1, option2, option3, option4, correct_answer);

                // Add it to the list
                questionDetails.add(qd);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        // Set question
        if (!questionDetails.isEmpty()) {
            displayQuestion(i);
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayQuestion(int index) {
        tv_q_no.setText((index + 1) + " / " + questionDetails.size());
        tv_q.setText(questionDetails.get(index).question);
        radio_1.setText(questionDetails.get(index).option1);
        radio_2.setText(questionDetails.get(index).option2);
        radio_3.setText(questionDetails.get(index).option3);
        radio_4.setText(questionDetails.get(index).option4);
        radioGroup.clearCheck();  // Clear any previous selection
    }

    @SuppressLint("SetTextI18n")
    private void nextQuestion() {
        // Check if the answer is correct
        RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String selectedAnswer = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";

        if (selectedAnswer.equals(questionDetails.get(i).correct_answer)) {
            totalCorrect++;  // Increment the total correct answers counter
            dialog_box.setText("Correct!");
        } else {
            dialog_box.setText("Incorrect!\nCorrect answer: " + questionDetails.get(i).correct_answer);
        }

        Toast.makeText(Questions.this, "Next question", Toast.LENGTH_SHORT).show();

        i++;

        if (i < questionDetails.size()) {
            displayQuestion(i);
        } else {
            // Navigate to ResultActivity
            Intent intent = new Intent(Questions.this, Results.class);
            intent.putExtra("totalCorrect", totalCorrect);
            startActivity(intent);
            finish();
        }

    }
}

