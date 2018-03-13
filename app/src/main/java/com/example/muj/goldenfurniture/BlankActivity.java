package com.example.muj.goldenfurniture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class BlankActivity extends AppCompatActivity
{
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            Intent intent = new Intent(this,furniture.class);
            startActivity(intent);
        }
        else
        {
            finish();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }
}
