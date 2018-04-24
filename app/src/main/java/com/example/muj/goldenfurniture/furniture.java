package com.example.muj.goldenfurniture;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.muj.goldenfurniture.authentication.MainActivity;
import com.example.muj.goldenfurniture.database.product;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class furniture extends AppCompatActivity {

    private ProductAdapter productAdapter;
    private AlertDialog alertDialog;
    private ListView listView;
    private ProgressDialog progressBar;
    ArrayList<product> productList;
    AlertDialog.Builder builder;

    private FirebaseAuth firebaseAuth;
    private GoogleSignInAccount account;

    private static int currentSort;
    private static int currentFilter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture);

        listView = findViewById(R.id.list_view_furniture);
        listView.setItemsCanFocus(true);

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, R.layout.list_item, productList);
        getProducts();

        firebaseAuth = FirebaseAuth.getInstance();
        account = GoogleSignIn.getLastSignedInAccount(this);

        if (firebaseAuth.getCurrentUser() == null && account == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
            progressBar = new ProgressDialog(this);
            progressBar.setMessage("Loading Products...");
            progressBar.setCancelable(true);
            progressBar.setIndeterminate(true);
        }
        return progressBar;
    }

    private void getProducts() {//sort value = 0
        currentSort = 0;
        currentFilter = 0;
        showDialog(1);
        productList.clear();
        productAdapter.clear();
        listView.setAdapter(productAdapter);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Query query;
                query = FirebaseDatabase.getInstance().getReference()
                            .child("furniture")
                            .orderByKey();

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                            productList.add(productSnapshot.getValue(product.class));
                        }
                        listView.setAdapter(productAdapter);
                        progressBar.cancel();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        thread.start();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_furniture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout)
        {
            firebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        else if (id == R.id.Sort) {
            sortProducts();
            alertDialog.show();
        }
        else if (id == R.id.filter)
        {
            filterProducts();
            alertDialog.show();
        }
        else if (id == R.id.action_cart)
        {
            startActivity(new Intent(this,CartActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortProducts() {
        String[] options = {"Default","Price Ascending", "Price Descending",};
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort By")
                .setSingleChoiceItems(options, currentSort, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0)
                        {
                            if (currentSort != 0)
                                getProducts();
                            alertDialog.dismiss();

                        }
                        else if (which == 1)
                        {
                            currentSort = 1;
                            Collections.sort(productList, new Comparator<product>() {
                                @Override
                                public int compare(product o2, product o1) {
                                    return o1.getPrice() > o2.getPrice() ? -1 : (o1.getPrice() < o2.getPrice()) ? 1 : 0;
                                }
                            });
                            listView.setAdapter(productAdapter);
                            alertDialog.dismiss();

                        }
                        else if (which == 2)
                        {
                            currentSort = 2;
                            Collections.sort(productList, new Comparator<product>() {
                                @Override
                                public int compare(product o1, product o2) {
                                    return o1.getPrice() > o2.getPrice() ? -1 : (o1.getPrice() < o2.getPrice()) ? 1 : 0;
                                }
                            });
                            listView.setAdapter(productAdapter);
                            alertDialog.dismiss();


                        }
                    }
                });

        alertDialog = builder.create();

    }

    private void filterProducts()
    {
        String[] options = {"All","Almirah", "Plastic Chair","Revolving Chair"};
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category")
                .setSingleChoiceItems(options, currentFilter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (which == 0)
                        {
                            if (currentFilter != 0)
                                getProducts();
                            Log.d("After", "getProducts call ");
                            alertDialog.dismiss();
                        }
                        else if(which == 1)
                        {
                            currentFilter = 1;
                            product p;
                            for (int i=0; i<productList.size();i++)
                            {
                                p = productList.get(i);
                                if (!p.getCategory().equals("Almirah"))
                                {
                                    productList.remove(i);
                                    i--;
                                }
                            }
                            listView.setAdapter(productAdapter);
                            alertDialog.dismiss();
                        }
                        else if (which == 2)
                        {
                            currentFilter = 2;
                            product p;
                            for (int i=0; i<productList.size();i++)
                            {
                                Log.d("After", "getProducts call ");
                                p = productList.get(i);
                                if (!p.getCategory().equals("Plastic Chair"))
                                {
                                    productList.remove(i);
                                    i--;
                                }
                            }
                            listView.setAdapter(productAdapter);
                            alertDialog.dismiss();
                        }
                        else if (which == 3)
                        {
                            currentFilter = 3;
                            product p;
                            for (int i=0; i<productList.size();i++)
                            {
                                p = productList.get(i);
                                if (!p.getCategory().equals("Revolving Chair"))
                                {
                                    productList.remove(i);
                                    i--;
                                }
                            }
                            listView.setAdapter(productAdapter);
                            alertDialog.dismiss();
                        }

                    }
                });
        alertDialog = builder.create();
    }






}

