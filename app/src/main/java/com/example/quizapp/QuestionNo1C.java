package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionNo1C extends AppCompatActivity {

    private ListView listView1;
    private CreatingDatabase db;
    private Button done;
    private Cursor cursor;
    private String categoryName;
    String user_name = "admin";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_no1_c);

        listView1 = findViewById(R.id.lv_questions);
        db = new CreatingDatabase(QuestionNo1C.this);
        done = findViewById(R.id.btn_done);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("categoryName");

        fetchQuestions(categoryName);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionNo1C.this, AdminDashBoard.class);
                intent.putExtra("user_name" , user_name);
                startActivity(intent);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        // Close the cursor when the activity is destroyed
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    private void fetchQuestions(String catName) {
        cursor = db.fetchAllQuestions(catName);

        QuestionAdapter adapter = new QuestionAdapter(this, cursor, 0);

        listView1.setAdapter(adapter);
    }
}
