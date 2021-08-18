package com.iraqoz.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Models.CartItem;
import Models.Order;
import Models.User;
import Services.DatabaseHelper;

public class CheckOutActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    //Preference field
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    TextView usernameTXT, emailTXT,phoneTXT,totalTXT;
    EditText addressEDT, phoneEDT;
    Button makePaymentBTN;
    DatabaseHelper _dbHelper;
    ArrayList<CartItem> _cartItems;
    Integer total=0;
    String UserId;
    String email,username,phone;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        dialog=new Dialog(this);

        usernameTXT=findViewById(R.id.textView_checkout_username);
        emailTXT=findViewById(R.id.textView_checkout_email);
        phoneTXT=findViewById(R.id.textView_checkout_phone);
        totalTXT=findViewById(R.id.textView_checkout_totalamount);
        addressEDT=findViewById(R.id.textView_checkout_address);
        phoneEDT=findViewById(R.id.editText_checkout_mpesa_number);
        makePaymentBTN=findViewById(R.id.button_make_payment);
        _dbHelper=new DatabaseHelper(this);
        _cartItems=new ArrayList<>();

        progressDialog=new ProgressDialog(this);
        firestore = FirebaseFirestore.getInstance();
        //Initialising preference
        sharedPreferences=getSharedPreferences("USER_DATA",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        UserId=sharedPreferences.getString("USER_ID","");
        username=sharedPreferences.getString("USERNAME","");
        email=sharedPreferences.getString("EMAIL","");
        phone=sharedPreferences.getString("PHONE","");
        usernameTXT.setText(""+username);
        emailTXT.setText(""+email);
        phoneTXT.setText(""+phone);
        addressEDT=findViewById(R.id.textView_checkout_address);

        dialog.setContentView(R.layout.activity_custom_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog



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
        totalTXT.setText(total+" KSH");

        makePaymentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address= addressEDT.getText().toString();
                String phone= phoneEDT.getText().toString();
                boolean isValid=validate(address, phone);
                if (!isValid){

                    addressEDT.setError("required");
                    phoneEDT.setError("required");
                    return;
                }
                Order o= new Order(UserId,phone,username,address,_cartItems,total.toString());
                order(o);
                _dbHelper.clearCart();
                dialog.show();



                new CountDownTimer(8000, 1000){
                    public void onTick(long millisUntilFinished){
                    }
                    public  void onFinish(){
                    }
                }.start();

                Intent intent= new Intent(CheckOutActivity.this, HomeMainActivity.class);
                startActivity(intent);
                finish();


            }
        });




    }

    private void order(Order order) {
        progressDialog.setMessage("Sending Order");
        progressDialog.show();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        String todayDate = simpleDateFormat.format(calendar.getTime());
        firestore.collection("RESTAURANTS").document("ORDERS").collection(todayDate)
                .add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                progressDialog.dismiss();
                Toast.makeText(CheckOutActivity.this, "Your Order has Been Sent Successfully", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(CheckOutActivity.this, HomeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean validate(String address, String phone){
        if (address.length()<15)
            return  false;
       else if (phone.length()<10)
            return false;
        else
            return true;
    }



}