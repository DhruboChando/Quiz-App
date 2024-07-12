package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreatingDatabase extends SQLiteOpenHelper {
    // Database
    private static final String DATABASE_NAME = "MyDB";
    private static final int DATABASE_VERSION = 1;

    // Table1
    private static final String TABLE1_NAME = "UserInformation";
    private static final String COLUMN_ID_TABLE1 = "ID";
    private static final String COLUMN_PHONE_NO = "PhoneNo";
    private static final String COLUMN_USERNAME = "User_name";
    private static final String COLUMN_PASSWORD = "Password";

    // Table2
    private static final String TABLE2_NAME = "CategoryCreation";
    public static final String COLUMN_ID_TABLE2 = "ID";
    public static final String COLUMN_CATEGORY_NAME = "Category_name";
    public static final String COLUMN_CATEGORY_TYPE = "Type_of_category";
    public static final String COLUMN_QUESTION_NUMBER = "Number_of_questions";
    public static final String COLUMN_IMAGE_ARRAY = "Image_array";

    // Table3
    private static final String TABLE3_NAME = "QuestionCreation";
    public static final String COLUMN_ID_TABLE3 = "ID";
    public static final String COLUMN_QUESTION = "Question";
    public static final String COLUMN_OPTION1 = "Option1";
    public static final String COLUMN_OPTION2 = "Option2";
    public static final String COLUMN_OPTION3 = "Option3";
    public static final String COLUMN_OPTION4 = "Option4";
    public static final String COLUMN_CORRECT_ANSWER = "Correct_answer";
    public static final String COLUMN_CATEGORY_NAME_TABLE3 = "Category_name";

    public CreatingDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Table creation
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table 1
        String createTable1 = "CREATE TABLE " + TABLE1_NAME + " (" +
                COLUMN_ID_TABLE1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PHONE_NO + " TEXT UNIQUE, " +
                COLUMN_USERNAME + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTable1);

        // Table 2
        String createTable2 = "CREATE TABLE " + TABLE2_NAME + " (" +
                COLUMN_ID_TABLE2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT UNIQUE, " +
                COLUMN_CATEGORY_TYPE + " TEXT, " +
                COLUMN_QUESTION_NUMBER + " INTEGER, " +
                COLUMN_IMAGE_ARRAY + " BLOB)";
        db.execSQL(createTable2);

        // Table 3
        String createTable3 = "CREATE TABLE " + TABLE3_NAME + " (" +
                COLUMN_ID_TABLE3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT UNIQUE, " +
                COLUMN_OPTION1 + " TEXT, " +
                COLUMN_OPTION2 + " TEXT, " +
                COLUMN_OPTION3 + " TEXT, " +
                COLUMN_OPTION4 + " TEXT, " +
                COLUMN_CORRECT_ANSWER + " TEXT, " +
                COLUMN_CATEGORY_NAME_TABLE3 + " TEXT)";
        db.execSQL(createTable3);
    }

    // Deleting tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        onCreate(db);
    }

    // Adding sign-up information to the database
    public boolean addSignupInfo(String mobile, String username, String password) {
        if (checkNumberUsername(mobile, username)) {
            return false;
        } else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PHONE_NO, mobile);
            contentValues.put(COLUMN_USERNAME, username);
            contentValues.put(COLUMN_PASSWORD, password);

            long result = db.insert(TABLE1_NAME, null, contentValues);
            db.close();
            return result != -1;
        }
    }

    // Checking if user has already signed in
    private boolean checkNumberUsername(String mobile, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_PHONE_NO + ", " + COLUMN_USERNAME + " FROM " + TABLE1_NAME +
                " WHERE " + COLUMN_PHONE_NO + " = ? OR " + COLUMN_USERNAME + " = ?";

        try (Cursor cursor = db.rawQuery(query, new String[]{mobile, username})) {
            return cursor != null && cursor.moveToFirst();
        }
    }

    // Checking user info and logging them in
    public boolean checkLoginInfo(String mobile, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_PHONE_NO + ", " + COLUMN_USERNAME + " FROM " + TABLE1_NAME +
                " WHERE " + COLUMN_PHONE_NO + " = ? AND " + COLUMN_PASSWORD + " = ?";
        try (Cursor cursor = db.rawQuery(query, new String[]{mobile, password})) {
            return cursor != null && cursor.moveToFirst();
        }
    }

    // Inserting new category
    public boolean insertNewCategory(String categoryName, String categoryType,
                                     int questionNum, byte[] imageArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_NAME, categoryName);
        contentValues.put(COLUMN_CATEGORY_TYPE, categoryType);
        contentValues.put(COLUMN_QUESTION_NUMBER, questionNum);
        contentValues.put(COLUMN_IMAGE_ARRAY, imageArray);

        long result = db.insert(TABLE2_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    // Inserting new question
    public boolean insertNewQuestion(String categoryName, String question, String option1, String option2, String option3,
                                     String option4, String correctAnswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_NAME_TABLE3, categoryName);
        contentValues.put(COLUMN_QUESTION, question);
        contentValues.put(COLUMN_OPTION1, option1);
        contentValues.put(COLUMN_OPTION2, option2);
        contentValues.put(COLUMN_OPTION3, option3);
        contentValues.put(COLUMN_OPTION4, option4);
        contentValues.put(COLUMN_CORRECT_ANSWER, correctAnswer);

        long result = db.insert(TABLE3_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    //showAllCategory method
    public Cursor showAllCategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID_TABLE2 + " AS _id, " + COLUMN_CATEGORY_NAME + ", " + COLUMN_IMAGE_ARRAY + " FROM " + TABLE2_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    //FetchAllQuestions method
    public Cursor fetchAllQuestions(String cat_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID_TABLE3 + " AS _id, * FROM " + TABLE3_NAME + " WHERE " + COLUMN_CATEGORY_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{cat_name});

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    //Delete category
    public void deleteCategory(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE2_NAME, COLUMN_CATEGORY_NAME + " = ?", new String[]{categoryName});
        db.close();
    }

}
