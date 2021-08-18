package com.iraqoz.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Models.Order;
import Services.DatabaseHelper;

public class ProductDetailActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    //Preference field
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ImageView imageView;
    TextView titleTXT, descriptionTXT,priceTXT, quantityTXT,totalTXT;
    Button decreaseBTN, increaseBTN, addToCartBTN, checkOutBTN;
    Integer amount=1;
    Integer quantity=1;
    Integer totalAmount=0;
    DatabaseHelper _dBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        _dBHelper=new DatabaseHelper(getApplicationContext());
        firestore = FirebaseFirestore.getInstance();
        //Initialising preference
        sharedPreferences=getSharedPreferences("USER_DATA",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        imageView=findViewById(R.id.image_view_cart_view_img);
        titleTXT=findViewById(R.id.textView_p_name);
        descriptionTXT=findViewById(R.id.textView_p_description);
        priceTXT=findViewById(R.id.textView_p_price);
        quantityTXT=findViewById(R.id.textView_p_quantity);
        totalTXT=findViewById(R.id.textView_p_total_amount);

        decreaseBTN=findViewById(R.id.button_decrease);
        increaseBTN=findViewById(R.id.button_increase);
        addToCartBTN=findViewById(R.id.button_p_add_to_cart);
        checkOutBTN =findViewById(R.id.button_p_order_now);

        Bundle bundle=getIntent().getExtras();
        amount=bundle.getInt("PRICE",1);
        titleTXT.setText(bundle.getString("NAME"));
        descriptionTXT.setText(bundle.getString("DESCRIPTION"));
        priceTXT.setText("@ "+amount+" KSH");
        Glide.with(this).load(bundle.getString("URL")).into(imageView);
        quantityTXT.setText("1");
        totalTXT.setText("Total Amount: "+amount+" KSH");

        String name=bundle.getString("NAME");
        String Url=bundle.getString("URL");



        decreaseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantity==1)
                {
                    totalTXT.setText("Total Amount  "+ amount*quantity+" KSH");
                    quantityTXT.setText("1");
                }

                else{
                    quantity--;
                    totalTXT.setText("Total Amount  "+ amount*quantity+" KSH");
                    quantityTXT.setText(""+quantity);
                }

            }
        });
        increaseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantity<15){
                    quantity++;
                    totalTXT.setText("Total Amount  "+ amount*quantity+" KSH");
                    quantityTXT.setText(""+quantity);
                }
                else{
                    totalTXT.setText("Total Amount  "+ amount*quantity+" KSH");
                    quantityTXT.setText("15");
                }
            }
        });
        addToCartBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalAmount=quantity*amount;
                Cursor res=_dBHelper.getCartItem(Url);
                if (res.getCount()==0){
                    boolean isInserted= _dBHelper.insertCartItem(name,Url,quantity.toString(),totalAmount.toString());
                    if (isInserted){
                        Toast.makeText(ProductDetailActivity.this,name+ " Has Been Added To Cart",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ProductDetailActivity.this,ViewCartActivity.class);
                        System.out.println("IMEFANYA VIZURI");
                        startActivity(intent);
                        finish();
                    }
                }else   {
                    boolean isUpdated=_dBHelper.updateCartItem(name,Url,quantity.toString(),totalAmount.toString());
                    if (isUpdated)
                    Toast.makeText(ProductDetailActivity.this,name+" is updated to Cart",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ProductDetailActivity.this,ViewCartActivity.class);
                    System.out.println("IMEFANYA VIZURI");
                    startActivity(intent);
                    finish();
                }

            }
        });
        checkOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalAmount=quantity*amount;
                Cursor res=_dBHelper.getCartItem(Url);
                if (res.getCount()==0){
                    boolean isInserted= _dBHelper.insertCartItem(name,Url,quantity.toString(),totalAmount.toString());
                    if (isInserted){
                        Toast.makeText(ProductDetailActivity.this,name+ " Has Been Added To Cart",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ProductDetailActivity.this,CheckOutActivity.class);
                        System.out.println("IMEFANYA VIZURI");
                        startActivity(intent);
                        finish();
                    }
                }else   {
                    boolean isUpdated=_dBHelper.updateCartItem(name,Url,quantity.toString(),totalAmount.toString());
                    if (isUpdated){
                        Toast.makeText(ProductDetailActivity.this,name+" is updated to Cart",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ProductDetailActivity.this,CheckOutActivity.class);
                        System.out.println("IMEFANYA VIZURI");
                        startActivity(intent);
                        finish();
                    }

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
}