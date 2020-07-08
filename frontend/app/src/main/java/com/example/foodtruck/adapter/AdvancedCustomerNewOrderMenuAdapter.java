package com.example.foodtruck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;

public class AdvancedCustomerNewOrderMenuAdapter extends ArrayAdapter<Dish> {

    String type;

    TextView dishName;
    TextView dishPrice;

    public AdvancedCustomerNewOrderMenuAdapter(Context context, int textviewResourceId, Dish[] objects, String type){
        super(context, textviewResourceId, objects);
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_customer_order_menu_item, null);
        }

        dishName = element.findViewById(R.id.dish_new_order_menu_name_c);
        dishPrice = element.findViewById(R.id.dish_new_order_menu_price_c);

        dishName.setText(getItem(position).getName());
        if (type.equals("reservation")) {
            dishPrice.setText(Double.toString(getItem(position).getAdjustedPrice()));
        } else {
            dishPrice.setText(Double.toString(getItem(position).getBasePrice()));
        }
        return element;
    }
}