package com.iraqoz.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    //Preference field
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Initialising preference
        sharedPreferences=getSharedPreferences("USER_DATA",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean status= sharedPreferences.getBoolean("LOGGED_IN_STATUS", false);
                if (status){
                    Intent mainIntent=new Intent(SplashScreenActivity.this, HomeMainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    Intent mainIntent=new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        },3000);
    }
}