package com.iraqoz.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity {
Button addFoodItemBTN, viewOrdersBTN, logoutBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addFoodItemBTN=findViewById(R.id.button_add_foodItem);
        viewOrdersBTN=findViewById(R.id.button_view_orders);
        logoutBTN=findViewById(R.id.button_admin_logout);

        addFoodItemBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity.this, AddFoodItemActivity.class);
                startActivity(intent);
            }
        });
        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}