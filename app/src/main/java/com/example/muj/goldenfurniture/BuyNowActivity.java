package com.example.muj.goldenfurniture;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.muj.goldenfurniture.database.orders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuyNowActivity extends AppCompatActivity {

    private int amount;
    private String productModel;
    EditText et1,et2,et3,et4,et5;
    TextView totalAmount;
    Button confirmOrder;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseRefrence;
    private FirebaseAuth firebaseAuth;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);

        et1 = findViewById(R.id.address_line1);
        et2 = findViewById(R.id.address_line2);
        et3 = findViewById(R.id.address_city);
        et4 = findViewById(R.id.address_pincode);
        et5 = findViewById(R.id.address_state);
        totalAmount = findViewById(R.id.tv_amount);
        confirmOrder = findViewById(R.id.button_confirmOrder);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        amount = Integer.valueOf(bundle.getString("amount"));
        totalAmount.setText("Amount : "+amount);
        productModel = bundle.getString("model");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        mMessagesDatabaseRefrence = mFirebaseDatabase.getReference().child("orders");

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean addressIsNotEmpty = ( !TextUtils.isEmpty(et1.getText().toString().trim()) ) &&  ( !TextUtils.isEmpty(et2.getText().toString().trim()) )  &&  ( !TextUtils.isEmpty(et3.getText().toString().trim()) )  &&  ( !TextUtils.isEmpty(et4.getText().toString().trim()) )  && ( !TextUtils.isEmpty(et5.getText().toString().trim()) );
                if (addressIsNotEmpty)
                {
                    String Address = et1.getText().toString().trim() + ", " + et2.getText().toString().trim() + ", " + et3.getText().toString().trim() + ", " + et4.getText().toString().trim() + ", " + et5.getText().toString().trim();
                    orders newOrder = new orders(firebaseAuth.getCurrentUser().getUid(), Address, productModel, amount);
                    mMessagesDatabaseRefrence.push().setValue(newOrder);
                    //finish();
                    AlertDialog.Builder builder = new AlertDialog.Builder(BuyNowActivity.this);
                    builder.setTitle("Order Confirmed")
                            .setCancelable(false)
                            .setMessage(firebaseAuth.getCurrentUser().getDisplayName() + ", Your order for our product " + productModel + " is confirmed.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                    finish();
                                }
                            });
                    alertDialog = builder.create();
                    alertDialog.show();
                }
                else
                {
                    Toast.makeText(BuyNowActivity.this,"All fields are required",Toast.LENGTH_LONG).show();
                }
            }

        });


    }
}
