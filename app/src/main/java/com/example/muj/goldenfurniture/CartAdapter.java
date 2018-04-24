package com.example.muj.goldenfurniture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.muj.goldenfurniture.database.Cart;
import com.example.muj.goldenfurniture.database.product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<Cart>
{
    public CartAdapter(Context context, int resource, ArrayList<Cart> cartArrayList)
    {
        super(context, resource, cartArrayList);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cart_list_item, parent, false);

        }
        final Cart cartitem = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.cart_tv_furniture_name);
        TextView priceTextView = listItemView.findViewById(R.id.cart_tv_furniture_price);
        ImageView productImage = listItemView.findViewById(R.id.cart_image_furniture);
        Button deleteButton = listItemView.findViewById(R.id.cart_delete_item);

        nameTextView.setText(cartitem.getProductBrand() + " " + cartitem.getProductId());
        priceTextView.setText("Rs. " + String.valueOf(cartitem.getProductPrice()));
        Glide.with(productImage.getContext())
                .load(cartitem.getProductImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(productImage);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("cart").orderByChild("productId").equalTo(cartitem.getProductId());
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

            }
        });

        return listItemView;
    }
}
