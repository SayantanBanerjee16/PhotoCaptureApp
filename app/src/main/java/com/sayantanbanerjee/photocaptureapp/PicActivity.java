package com.sayantanbanerjee.photocaptureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class PicActivity extends AppCompatActivity {
    ImageView bigImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        bigImageView = (ImageView) findViewById(R.id.imageBig);
        File directory = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(directory.getAbsolutePath());
        bigImageView.setImageBitmap(bitmap);
    }
}
