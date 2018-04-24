package com.example.muj.goldenfurniture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.muj.goldenfurniture.database.Cart;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity
{
    private ListView listView;
    private Button buyButton;
    private ArrayList<Cart> cartArrayList;
    private CartAdapter cartAdapter;
    private TextView totalAmountTextView;
    private static long totalAmount;
    private static String products;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listView = findViewById(R.id.list_view_cart);
        buyButton = findViewById(R.id.cart_buy_button);
        totalAmountTextView = findViewById(R.id.cart_total_amount);

        cartArrayList = new ArrayList<>();
        cartAdapter = new CartAdapter(this,R.layout.cart_list_item,cartArrayList);
        cartArrayList.clear();
        cartAdapter.clear();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("cart");
        getCartItems();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                getCartItems();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,BuyNowActivity.class);
                Bundle bundle = new Bundle();
                String amount = String.valueOf(totalAmount);
                String productModel = products;
                bundle.putString("amount",amount);
                bundle.putString("model",productModel);
                intent.putExtras(bundle);
                startActivity(intent);
                Query query = FirebaseDatabase.getInstance().getReference().child("cart").orderByChild("uid").equalTo(firebaseAuth.getCurrentUser().getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot cartSnapshot: dataSnapshot.getChildren())
                        {
                            cartSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                finish();
            }
        });

    }
     public void getCartItems()
     {
         cartArrayList.clear();
         cartAdapter.clear();
         listView.setAdapter(cartAdapter);
         totalAmount = 0;
         products = "";
         Query query;
         query = databaseReference.orderByChild("uid").equalTo(firebaseAuth.getCurrentUser().getUid());
         query.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot)
             {
                 cartArrayList.clear();
                 cartAdapter.clear();
                 listView.setAdapter(cartAdapter);
                 totalAmount = 0;
                 products = "";
                 for (DataSnapshot cartSnapshot : dataSnapshot.getChildren())
                 {
                     cartArrayList.add(cartSnapshot.getValue(Cart.class));
                 }
                 for (Cart cart: cartArrayList)
                 {
                     totalAmount += cart.getProductPrice();
                     products += cart.getProductId()+", ";
                 }
                 listView.setAdapter(cartAdapter);
                 totalAmountTextView.setText("Total Amount: Rs."+totalAmount);

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

     }
}
