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
import android.widget.TextView;

public class CategoryResourceFileAdapter extends CursorAdapter {
    public CategoryResourceFileAdapter(QuizCategory quizCategory, Cursor cursor, int i) {
        super(quizCategory, cursor, i);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.catergories_resource_file, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find the views
        TextView categoryNameTextView = view.findViewById(R.id.tv_category_name);
        ImageView categoryImageView = view.findViewById(R.id.iv_image);

        // Extract properties from cursor
        String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_CATEGORY_NAME));
        byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(CreatingDatabase.COLUMN_IMAGE_ARRAY));

        // Populate fields with extracted properties
        categoryNameTextView.setText(categoryName);

        // Check if the image byte array is not null
        if (imageByteArray != null) {
            // Decode with inSampleSize to scale down the image
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 100, 100); // Adjust the size as needed

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length, options);

            // Set the decoded bitmap to the ImageView
            categoryImageView.setImageBitmap(bitmap);
        } else {
            // Set a default image or handle the null case appropriately
            categoryImageView.setImageResource(R.drawable.database); // Replace with your default image resource
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
