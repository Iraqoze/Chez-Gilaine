package com.iraqoz.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Adapters.FirestoreAdapter;
import Models.Food;

public class CategoryFoodItemTemplateActivity extends AppCompatActivity implements FirestoreAdapter.OnListItemClick {
    RecyclerView rv_foods;
    TextView categoryNameTXT;
    private FirebaseFirestore firestore;
    FirestoreAdapter foodsAdapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private LinearLayoutManager linearLayoutManager;
    String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_food_item_template);
        rv_foods=findViewById(R.id.rv_category_foods);
        categoryNameTXT=findViewById(R.id.text_view_category_name_template);

        firestore = FirebaseFirestore.getInstance();

        //Initialising preference
        sharedPreferences=getSharedPreferences("USER_DATA",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        //Getting category name from intent
        categoryName= getIntent().getStringExtra("CATEGORY_NAME");
        categoryNameTXT.setText(categoryName);
        System.out.println("USER ID: "+sharedPreferences.getString("USER_ID",""));

        //START
        //querying foods from the firestore that match the category name
        Query query = firestore
                .collection("RESTAURANTS").document("MENU").collection(categoryName);
        FirestoreRecyclerOptions<Food> foods = new FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();
        if (foods==null){
            Toast.makeText(CategoryFoodItemTemplateActivity.this,"Sorry We don't have that food Item at the moment. Please come back later",Toast.LENGTH_LONG).show();
            return;
        }
        //Top Picks
        linearLayoutManager =new GridLayoutManager(CategoryFoodItemTemplateActivity.this,3);
        foodsAdapter =new FirestoreAdapter(foods,this);
        rv_foods.setLayoutManager(linearLayoutManager);
        rv_foods.setAdapter(foodsAdapter);
    }
    @Override
    protected void onStop(){
        super.onStop();
        foodsAdapter.stopListening();
    }

    @Override
    protected void onStart(){
        super.onStart();
        foodsAdapter.startListening();
    }

    @Override
    public void onItemclick(Food foodItem, int position) {
        System.out.println("Clicked Food Item: "+foodItem.getFoodname()+" at position: " +position);

        Bundle bundle=new Bundle();
        Intent intent=new Intent(CategoryFoodItemTemplateActivity.this, ProductDetailActivity.class);
        bundle.putString("NAME",foodItem.getFoodname());
        bundle.putString("CATEGORY",foodItem.getCategory());
        bundle.putString("DESCRIPTION",foodItem.getDescription());
        bundle.putString("URL",foodItem.getImageUrl());
        bundle.putInt("PRICE",foodItem.getPrice());
        intent.putExtras(bundle);

        startActivity(intent);
    }
}