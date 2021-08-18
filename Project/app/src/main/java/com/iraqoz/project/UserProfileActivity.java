package com.iraqoz.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Services.DatabaseHelper;

public class UserProfileActivity extends AppCompatActivity {
    //Preference field
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button viewOrdersBTN, logoutBTN;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        viewOrdersBTN=findViewById(R.id.button_View_orders);
        logoutBTN=findViewById(R.id.button_logout);

        sharedPreferences=getSharedPreferences("USER_DATA",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        databaseHelper=new DatabaseHelper(this);


        viewOrdersBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfileActivity.this, ViewOrdersActivity.class);
                startActivity(intent);
                finish();
            }
        });
        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(UserProfileActivity.this, MainActivity.class);
                editor.clear();
                editor.commit();
                databaseHelper.clearCart();
                startActivity(intent);
                finish();
            }
        });


    }
}