package com.example.muj.goldenfurniture;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MUJ on 09-Mar-18.
 */

public class DatabaseHandler extends SQLiteOpenHelper
{
    public static String name = "Furniture";
    public static int version = 1;

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String query="Create table customer (customer_id integer primary key,customer_name text, phone integer, email text, address text )";
        sqLiteDatabase.execSQL(query);
        query = "Create table orders (order_id integer primary key, product_id integer, order_date integer, customer_id integer)";
        sqLiteDatabase.execSQL(query);
        query = "Create table product (product_id integer primary key, price integer, category text, brand text, model text)";
        sqLiteDatabase.execSQL(query);
        query = "Create table returns (return_id integer primary key, order_id integer, product_id integer, return_date integer, customer_id integer)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    public void addCustomer(customer customer)
    {
        Log.d("Worked","Addfunction");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("customer_id",customer.getCustomer_id());
        values.put("customer_name",customer.getCustomer_name());
        values.put("phone",customer.getPhone());
        values.put("email",customer.getEmail());
        values.put("address",customer.getAddress());
        db.insert("customer", null, values);
        db.close();

    }
}
