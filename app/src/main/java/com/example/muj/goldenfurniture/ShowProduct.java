package com.example.muj.goldenfurniture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by MUJ on 21-Apr-18.
 */

public class ShowProduct extends AppCompatActivity
{
    private ImageView productImage;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_product);

        productImage = findViewById(R.id.product_image);

        //get photo url from intent through bundle
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String photoUrl = bundle.getString("url");
        
        //load photo using url using Glide
        Glide.with(productImage.getContext())
                .load(photoUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(productImage);




    }

}
