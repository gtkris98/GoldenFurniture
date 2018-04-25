package com.example.muj.goldenfurniture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.muj.goldenfurniture.database.Cart;
import com.example.muj.goldenfurniture.database.orders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity
{
    private ListView listView;
    private ArrayList<orders> ordersArrayList;
    private OrderAdapter orderAdapter;


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        listView = findViewById(R.id.list_view_orders);
        ordersArrayList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, R.layout.order_list_item, ordersArrayList);
        //ordersArrayList.clear();
        //orderAdapter.clear();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("orders");

        getOrders();
    }
    private void getOrders()
    {
        ordersArrayList.clear();
        orderAdapter.clear();
        listView.setAdapter(orderAdapter);
        Query query;
        query = databaseReference.orderByChild("userId").equalTo(firebaseAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot orderSnapshot: dataSnapshot.getChildren())
                {
                    ordersArrayList.add(orderSnapshot.getValue(orders.class));
                }
                Log.d("After",String.valueOf(ordersArrayList.size()));
                listView.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
