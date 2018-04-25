package com.example.muj.goldenfurniture;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.muj.goldenfurniture.database.orders;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class OrderAdapter extends ArrayAdapter<orders>
{
    public OrderAdapter(Context context, int resource, ArrayList<orders> ordersArrayList)
    {
        super(context, resource, ordersArrayList);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;
        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.order_list_item, parent, false);

        }
        final orders currentOrder = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.order_tv_furniture_name);
        TextView statusTextView =  listItemView.findViewById(R.id.order_tv_furniture_status);
        TextView priceTextView = listItemView.findViewById(R.id.order_tv_furniture_price);
        TextView dateTextView = listItemView.findViewById(R.id.order_tv_furniture_date);

        nameTextView.setText(currentOrder.getProductModel());
        statusTextView.setText("Status: "+currentOrder.getStatus());
        priceTextView.setText("Rs. "+String.valueOf(currentOrder.getAmount()));

        //format unix time to readable date
        Date dateObject = new Date(currentOrder.getUnixTimeStamp());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String formattedDate = dateFormat.format(dateObject);
        Log.d("Time",formattedDate);
        dateTextView.setText(formattedDate);

        //decide color of status text view according to status
        if (currentOrder.getStatus().equals("Pending"))
        {
            statusTextView.setTextColor(Color.RED);
        }
        else if (currentOrder.getStatus().equals("Confirmed"))
        {
            statusTextView.setTextColor(Color.BLUE);
        }
        else
        {
            statusTextView.setTextColor(Color.GREEN);

        }
        return listItemView;

    }
}
