package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuizCategory extends AppCompatActivity {

    private GridView gridView1;
    private CreatingDatabase db;
    private ArrayList<String> categoryName;
    private TextView tv_logout;
    Cursor cursor;
    private String user_name = "";

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);

        // Initialize views and database instance
        gridView1 = findViewById(R.id.gv_categories);
        db = new CreatingDatabase(QuizCategory.this);
        tv_logout = findViewById(R.id.tv_logout);

        //receiving passed data
        user_name = getIntent().getStringExtra("user_name");

        if(user_name==null) {
            Toast.makeText(this, "User name is nulllll", Toast.LENGTH_SHORT).show();
            return;
        }

        if(user_name.equals("admin")) {
            // Display categories
            categoryName = showCategory();

            // Set click listener for GridView items for admin
            gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedCategory = categoryName.get(position);

                    // Create an intent to start QuestionsActivity
                    Intent intent = new Intent(QuizCategory.this, QuestionNo1C.class);

                    // Pass relevant category attributes as extras to the intent
                    intent.putExtra("categoryName", selectedCategory);

                    // Start the activity
                    startActivity(intent);
                }
            });

        }else {
            // Display categories
            categoryName = showCategory();
            tv_logout.setText("Log out");
            tv_logout.setBackgroundResource(R.color.yellow);

            // Set click listener for GridView items for participants
            gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedCategory = categoryName.get(position);

                    // Create an intent to start QuestionsActivity
                    Intent intent = new Intent(QuizCategory.this, Questions.class);

                    // Pass relevant category attributes as extras to the intent
                    intent.putExtra("categoryName", selectedCategory);

                    // Start the activity
                    startActivity(intent);
                }
            });

            tv_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(QuizCategory.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the categories list when returning to this activity
        categoryName = showCategory();
    }

    protected void onDestroy() {
        super.onDestroy();
        // Close the cursor when the activity is destroyed
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    private ArrayList<String> showCategory() {
        ArrayList<String> categories = new ArrayList<>();
        cursor = null;

        try {
            // Fetch all categories from the database
            cursor = db.showAllCategory();
            CategoryResourceFileAdapter resourceFileAdapter = new CategoryResourceFileAdapter(QuizCategory.this, cursor, 0);
            gridView1.setAdapter(resourceFileAdapter);

            // Iterate through the cursor and add category names to the list
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_CATEGORY_NAME));
                    categories.add(categoryName);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return categories;
    }
}
