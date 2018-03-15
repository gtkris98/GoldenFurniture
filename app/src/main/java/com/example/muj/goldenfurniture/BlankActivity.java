package com.example.muj.goldenfurniture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BlankActivity extends AppCompatActivity
{
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null)
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

        //Alternate method below

        /*GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(firebaseAuth.getCurrentUser() != null || account != null)
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
        }*/
    }
}
