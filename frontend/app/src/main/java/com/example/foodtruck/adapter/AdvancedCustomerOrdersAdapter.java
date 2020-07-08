package com.example.foodtruck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.order.Order;

public class AdvancedCustomerOrdersAdapter extends ArrayAdapter<Order> {

    public AdvancedCustomerOrdersAdapter(Context context, int textviewResourceId, Order[] objects){
        super(context, textviewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_customer_show_menu_item, null);
        }

        TextView orderNumber = element.findViewById(R.id.show_orders_number_c);
        TextView orderStatus = element.findViewById(R.id.show_orders_status_c);

        orderNumber.setText(getItem(position).getId().toString());
        orderNumber.setText(getItem(position).getStatus().toString());
        return element;
    }
}
