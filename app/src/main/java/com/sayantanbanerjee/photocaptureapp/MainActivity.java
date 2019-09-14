package com.sayantanbanerjee.photocaptureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ImageView image = (ImageView) findViewById(R.id.splashImage);
        image.animate().alphaBy(1f).setDuration(1500);
        new CountDownTimer(1500,1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, BottomNavigation_Activity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }.start();
    }
}
