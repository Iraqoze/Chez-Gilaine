package com.iraqoz.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import Adapters.CategoryAdapter;
import Adapters.FirestoreAdapter;
import Models.Category;
import Models.Food;

public class HomeMainActivity extends AppCompatActivity implements FirestoreAdapter.OnListItemClick, CategoryAdapter.OnCategoryFoodItemClickListener{
    private FirebaseFirestore firestore;
    FirestoreAdapter mealsAdapter;
    FirestoreAdapter starterAdapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
SearchView sv;
ImageButton cartImgBTN, profileBTN;
RecyclerView rvCategories;
RecyclerView rvTopPicks;
RecyclerView rvNewDishes;
ArrayList<Category>categories;
CategoryAdapter adapter;
private LinearLayoutManager categoryLinearLyout;
private LinearLayoutManager newDishesLinearLyout;
private LinearLayoutManager topPicksLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        cartImgBTN=findViewById(R.id.imageButton_cart);
        profileBTN=findViewById(R.id.imageButton_profile);
        sv=findViewById(R.id.searchView);
        cartImgBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeMainActivity.this, ViewCartActivity.class);
                startActivity(intent);
            }
        });
        profileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeMainActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(HomeMainActivity.this, CategoryFoodItemTemplateActivity.class);
                intent.putExtra("CATEGORY_NAME",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Initialising preference
        sharedPreferences=getSharedPreferences("USER_DATA",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        System.out.println("USER ID: "+sharedPreferences.getString("USER_ID",""));


        //Initializing Firebase Firestore service
        firestore = FirebaseFirestore.getInstance();
        //START
        Query qStarters = firestore
                .collection("RESTAURANTS").document("MENU").collection("Starters");
        FirestoreRecyclerOptions<Food> oStarters = new FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(qStarters, Food.class)
                .build();
        
        Query qMeals = firestore
                .collection("RESTAURANTS").document("MENU").collection("Meals");
        FirestoreRecyclerOptions<Food> oMeals = new FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(qMeals, Food.class)
                .build();

        rvCategories=findViewById(R.id.rv_allcategories);
        rvTopPicks=findViewById(R.id.rv_toppicks);
        rvNewDishes=findViewById(R.id.rv_newdishes);
        categories=new ArrayList<>();
        getCategories();
        adapter= new CategoryAdapter(categories,this);

        //Featured Category Adapter
          categoryLinearLyout = new LinearLayoutManager(HomeMainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        rvCategories.setLayoutManager(categoryLinearLyout);
        rvCategories.setAdapter(adapter);

       //Top Picks
        topPicksLayoutManager=new GridLayoutManager(HomeMainActivity.this,3);
        mealsAdapter =new FirestoreAdapter(oMeals,this);
        rvTopPicks.setLayoutManager(topPicksLayoutManager);
        rvTopPicks.setAdapter(mealsAdapter);

        //New Dishes Adapter
        newDishesLinearLyout= new LinearLayoutManager(HomeMainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        starterAdapter =new FirestoreAdapter(oStarters,this);
        rvNewDishes.setLayoutManager(newDishesLinearLyout);
        rvNewDishes.setAdapter(starterAdapter);




    }

    @Override
    public void onItemclick(Food foodItem, int position) {
        System.out.println("Clicked Food Item: "+foodItem.getFoodname()+" at position: " +position);

        Bundle bundle=new Bundle();
        Intent intent=new Intent(HomeMainActivity.this, ProductDetailActivity.class);
        bundle.putString("NAME",foodItem.getFoodname());
        bundle.putString("CATEGORY",foodItem.getCategory());
        bundle.putString("DESCRIPTION",foodItem.getDescription());
        bundle.putString("URL",foodItem.getImageUrl());
        bundle.putInt("PRICE",foodItem.getPrice());
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void onCategoryFoodItemClick(Category category) {
        System.out.println("CATEGORY CLICKED: "+category.getName());
        Intent intent = new Intent(HomeMainActivity.this, CategoryFoodItemTemplateActivity.class);
        intent.putExtra("CATEGORY_NAME",category.getName());
        startActivity(intent);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        mealsAdapter.stopListening();
        starterAdapter.stopListening();
    }

    @Override
    protected void onStart(){
        super.onStart();
        mealsAdapter.startListening();
        starterAdapter.startListening();
    }
    private void getCategories(){

        categories.add(new Category("Desserts",1,R.drawable.dessertimg));
        categories.add(new Category("Drinks",2,R.drawable.juice));
        categories.add(new Category("Meals",3,R.drawable.mainmeal));
        categories.add(new Category("Starters",4,R.drawable.starters));
        categories.add(new Category("Salads",5,R.drawable.salads));


    }
}