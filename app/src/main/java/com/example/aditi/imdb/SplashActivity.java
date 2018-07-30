package com.example.aditi.imdb;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //start Main activity

                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        }, 1000L);

    }
}
