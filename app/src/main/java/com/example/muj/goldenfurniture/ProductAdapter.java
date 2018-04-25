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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.muj.goldenfurniture.database.Cart;
import com.example.muj.goldenfurniture.database.product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by MUJ on 21-Apr-18.
 */

public class ProductAdapter extends ArrayAdapter<product>
{

    public ProductAdapter(Context context, int resource, ArrayList<product> productArrayList)
    {
        super(context, resource, productArrayList);
    }
    @Override
public View getView(final int position, View convertView, ViewGroup parent)
{
    View listItemView = convertView;
    if (listItemView == null)
    {
        listItemView = LayoutInflater.from(getContext()).inflate(
                R.layout.list_item, parent, false);

    }
    final product currentProduct = getItem(position);

    TextView nameTextView = listItemView.findViewById(R.id.tv_furniture_name);
    TextView sizeTextView =  listItemView.findViewById(R.id.tv_furniture_size);
    TextView priceTextView = listItemView.findViewById(R.id.tv_furniture_price);
    ImageView productImage = listItemView.findViewById(R.id.image_furniture);
    Button buttonCart = listItemView.findViewById(R.id.button_cart);
    Button buttonBuyNow = listItemView.findViewById(R.id.button_buy);

    nameTextView.setText(currentProduct.getBrand()+" "+currentProduct.getModel());
    sizeTextView.setText("Size : "+currentProduct.getSize());
    priceTextView.setText("Rs. "+String.valueOf(currentProduct.getPrice()));
    Glide.with(productImage.getContext())
            .load(currentProduct.getImage())
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(productImage);


    productImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(),ShowProduct.class);
            Bundle bundle = new Bundle();
            String photoUrl = currentProduct.getImage();
            bundle.putString("url",photoUrl);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        }
    });

    buttonBuyNow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getContext(),BuyNowActivity.class);
            Bundle bundle = new Bundle();
            String amount = String.valueOf(currentProduct.getPrice());
            String productModel = currentProduct.getModel();
            bundle.putString("amount",amount);
            bundle.putString("model",productModel);
            intent.putExtras(bundle);
            getContext().startActivity(intent);

        }
    });

    buttonCart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Cart cart = new Cart(FirebaseAuth.getInstance().getCurrentUser().getUid(),currentProduct.getBrand(),currentProduct.getModel(),currentProduct.getPrice(),currentProduct.getImage());
            FirebaseDatabase.getInstance().getReference().child("cart").push().setValue(cart);
            Toast.makeText(getContext(),"Item added to cart",Toast.LENGTH_SHORT).show();

        }
    });



    return listItemView;

}


}