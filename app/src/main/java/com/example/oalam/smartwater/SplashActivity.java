package com.example.oalam.smartwater;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start main activity
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                // close splash activity
                finish();
            }
        },650);

    }
}
