package com.example.quizapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class QuestionAdapter extends CursorAdapter {
    public QuestionAdapter(QuestionNo1C questionNo1C, Cursor cursor, int i) {
        super(questionNo1C, cursor, i);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_view, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find the views
        TextView question_name = view.findViewById(R.id.tv_question);
        RadioButton option1 = view.findViewById(R.id.option1);
        RadioButton option2 = view.findViewById(R.id.option2);
        RadioButton option3 = view.findViewById(R.id.option3);
        RadioButton option4 = view.findViewById(R.id.option4);

        // Extract properties from cursor
        String qus = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_QUESTION));
        String op1 = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_OPTION1));
        String op2 = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_OPTION2));
        String op3 = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_OPTION3));
        String op4 = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_OPTION4));

        // Populate fields with extracted properties
        question_name.setText(qus);
        option1.setText(op1);
        option2.setText(op2);
        option3.setText(op3);
        option4.setText(op4);

    }
}
