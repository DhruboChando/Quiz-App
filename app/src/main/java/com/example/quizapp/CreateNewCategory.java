package com.example.quizapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreateNewCategory extends AppCompatActivity {

    private EditText name;
    private EditText type;
    private EditText question_number;
    private Button create;
    private ImageView image;
    private byte[] imageArray;
    private CreatingDatabase db;
    private ActivityResultLauncher<Intent> imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_category);

        name = findViewById(R.id.et_name);
        type = findViewById(R.id.et_type);
        question_number = findViewById(R.id.et_number_question);

        image = findViewById(R.id.iv_upload_img);

        create = findViewById(R.id.btn_create_new);

        db = new CreatingDatabase(this);

        imagePicker = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    image.setImageBitmap(imageBitmap);
                    imageArray = bitmapToByteArray(imageBitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Error!!!",
                            Toast.LENGTH_SHORT).show();;
                }
            }
        });

        image.setOnClickListener(view -> showImageSelectionDialog());

        create.setOnClickListener(view -> createCategory());
    }

    private void createCategory() {
        String category_name = name.getText().toString();
        String category_type = type.getText().toString();
        int question_num = Integer.parseInt(question_number.getText().toString());

        if(category_name.isEmpty() || category_type.isEmpty() || imageArray==null) {
            Toast.makeText(this, "Please fill all the fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        boolean flag = db.insertNewCategory(category_name,category_type,question_num,imageArray);

        if(flag){
            Toast.makeText(this, "A new category is created\nAdd question in it\t",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CreateNewCategory.this, CreateQuestions.class);
            intent.putExtra("categoryName" , category_name);
            intent.putExtra("questionNumber" , question_num);
            startActivity(intent);

        }else {
            Toast.makeText(this, "Some errors occurred!!!\nCategory already exists!!!\nPlease try again!!",
                    Toast.LENGTH_SHORT).show();

            name.getText().clear();
            type.getText().clear();
            question_number.getText().clear();
            image.setImageResource(R.drawable.upload_image2);

        }

    }

    @SuppressLint("IntentReset")
    private void showImageSelectionDialog() {
        @SuppressLint("IntentReset") Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        imagePicker.launch(pickIntent);
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}