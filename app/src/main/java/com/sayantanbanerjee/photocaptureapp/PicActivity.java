package com.sayantanbanerjee.photocaptureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

public class PicActivity extends AppCompatActivity {
    ImageView bigImageView;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public void onBackPressed() {
        bigImageView.setAlpha(0.2f);
        textView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        textView = findViewById(R.id.loading_view6);
        progressBar = findViewById(R.id.loading_view7);
        textView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        bigImageView = (ImageView) findViewById(R.id.imageBig);
        File directory = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(directory.getAbsolutePath());
        bigImageView.setImageBitmap(bitmap);
    }
}
