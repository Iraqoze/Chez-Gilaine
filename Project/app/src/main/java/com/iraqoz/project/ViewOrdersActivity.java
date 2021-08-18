package com.iraqoz.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Adapters.OrdersAdapter;
import Models.CartItem;
import Models.Food;
import Models.Order;
import Models.User;

public class ViewOrdersActivity extends AppCompatActivity implements OrdersAdapter.OnCategoryFoodItemClickListener {
    private FirebaseFirestore firestore;
    ProgressDialog progressDialog;

    private LinearLayoutManager orderLinearLyout;

    //Preference field
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
ArrayList<Order> orders;
RecyclerView rv_orders;
OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        rv_orders=findViewById(R.id.rv_orderhistory);
        firestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        //Initialising preference
        sharedPreferences=getSharedPreferences("USER_DATA",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        orders=new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        String todayDate = simpleDateFormat.format(calendar.getTime());

        Query query= firestore.collection("RESTAURANTS").document("ORDERS")
                .collection(todayDate);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Order order = document.toObject(Order.class);
                        orders.add(order);

                        Log.d("ORDER:",order.getTotalAmount());
                        System.out.println(order.getTotalAmount());
                    }

                    for (Order order: orders){
                        System.out.println("TIME "+order.getTime());
                        System.out.println("AMOUNT "+order.getTotalAmount());
                        for (CartItem item: order.getCartItems()){
                            System.out.println("ITEM "+item.getName());
                            System.out.println("QUANTITY "+item.getQuantity());
                            System.out.println("PRICE "+item.getAmount()+" \n\n");
                        }
                    }
                }
                else {
                    Log.d("IMEKATAAA","IMAGINE BANA HAIFANYI");
                    System.out.println("IMEKATA KUFANYA");
                }
            }
        });

        orderLinearLyout = new LinearLayoutManager(ViewOrdersActivity.this,LinearLayoutManager.HORIZONTAL,false);
        rv_orders.setLayoutManager(orderLinearLyout);
        ordersAdapter=new OrdersAdapter(orders,this);
        rv_orders.setHasFixedSize(true);
        rv_orders.setAdapter(ordersAdapter);



    }
    private void getOrders() {
        ArrayList<Order>orders=new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        String todayDate = simpleDateFormat.format(calendar.getTime());

        Query query= firestore.collection("RESTAURANTS").document("ORDERS")
                .collection(todayDate);
        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                .build();

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Order order = document.toObject(Order.class);
                        orders.add(order);

                        Log.d("ORDER:",order.getTotalAmount());
                        System.out.println(order.getTotalAmount());
                    }

                }
                else {
                    Log.d("IMEKATAAA","IMAGINE BANA HAIFANYI");
                    System.out.println("IMEKATA KUFANYA");
                }
            }
        });
        for (Order order: orders){
            System.out.println("TIME "+order.getTime());
            System.out.println("AMOUNT "+order.getTotalAmount());
            for (CartItem item: order.getCartItems()){
                System.out.println("ITEM "+item.getName());
                System.out.println("QUANTITY "+item.getQuantity());
                System.out.println("PRICE "+item.getAmount()+" \n\n");
            }
        }

    }

    @Override
    public void onCategoryFoodItemClick(Order order) {

    }
}
