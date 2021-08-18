package com.iraqoz.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Adapters.CartItemAdapter;
import Models.CartItem;
import Models.Order;
import Models.User;
import Services.DatabaseHelper;

public class ViewCartActivity extends AppCompatActivity implements CartItemAdapter.OnCartRemoveItemClickListener {
    private FirebaseFirestore firestore;
    //Preference field
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button checkOutBTN;
    DatabaseHelper _dbHelper;
    RecyclerView rv_cartItems;
    ArrayList<CartItem> _cartItems;
    CartItemAdapter adapter;
    TextView totalTXT;
    Integer total=0;
    String UserId;
    private LinearLayoutManager cartItemsLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        _cartItems=new ArrayList<>();
        _dbHelper=new DatabaseHelper(this);
        rv_cartItems=findViewById(R.id.rv_cart_items);
        totalTXT=findViewById(R.id.textView_total);
        checkOutBTN=findViewById(R.id.button_checkOut);

        firestore = FirebaseFirestore.getInstance();
        //Initialising preference
        sharedPreferences=getSharedPreferences("USER_DATA",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        UserId=sharedPreferences.getString("USER_ID","");

        Cursor res=_dbHelper.getCartItems();
        _cartItems.clear();
        if (res.getCount()==0){
            Toast.makeText(this,"No Cart Items",Toast.LENGTH_SHORT).show();
        }
        while(res.moveToNext()) {
            _cartItems.add(new CartItem(res.getString(0),res.getString(1),res.getString(2),res.getString(3)));

        }
        for (CartItem item: _cartItems){
            total +=Integer.parseInt(item.getAmount());
        }
        totalTXT.setText("Total Amount: "+total+" KSH");

        //Setting the Recycler View Adapter
        cartItemsLayoutManager=new LinearLayoutManager(this);
        adapter=new CartItemAdapter(_cartItems,this);
        rv_cartItems.setLayoutManager(cartItemsLayoutManager);
        rv_cartItems.setAdapter(adapter);
        //End

        checkOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cursor res=_dbHelper.getCartItems();
               if (res.getCount()==0){
                   Toast.makeText(ViewCartActivity.this,"Cart Is Empty. Add Items First Before Check Out",Toast.LENGTH_SHORT).show();
               }
               else {
                   Intent intent=new Intent(ViewCartActivity.this, CheckOutActivity.class);
                   startActivity(intent);
                   finish();
               }
            }
        });



    }
    private void order(ArrayList<Order> orders) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(new Date());

      /* firestore.collection("RESTAURANTS").document("ORDERS")
                 .collection(formattedDate).add(orders).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        total=0;
        _cartItems.clear();
        Cursor res=_dbHelper.getCartItems();
        if (res.getCount()==0){
            return;
            //Toast.makeText(this,"No Cart Items",Toast.LENGTH_SHORT).show();
        }
        while(res.moveToNext()) {
            _cartItems.add(new CartItem(res.getString(0),res.getString(1),res.getString(2),res.getString(3)));

        }

        for (CartItem item: _cartItems){
            total +=Integer.parseInt(item.getAmount());
        }
        totalTXT.setText("Total Amount: "+total+" KSH");

        adapter=new CartItemAdapter(_cartItems,this);
        rv_cartItems.setAdapter(adapter);

    }

    @Override
    public void onCartRemoveItemClick(CartItem cartItem) {
        System.out.println("Remove Button Has Been Clicked");
        _dbHelper.deleteCartItem(cartItem.getUrl());
        Toast.makeText(ViewCartActivity.this, cartItem.getName()+" is removed from Cart",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(ViewCartActivity.this, ViewCartActivity.class);
        startActivity(intent);
        finish();

    }
}