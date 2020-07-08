package com.example.foodtruck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;

public class AdvancedCustomerShowMenuAdapter extends ArrayAdapter<Dish> {

    public AdvancedCustomerShowMenuAdapter(Context context, int textviewResourceId, Dish[] objects){
        super(context, textviewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_customer_show_menu_item, null);
        }

        TextView dishName = element.findViewById(R.id.dish_menu_name_c);
        TextView dishPrice = element.findViewById(R.id.dish_menu_price_c);
        TextView dishRating = element.findViewById(R.id.dish_menu_rating_c);

        dishName.setText(getItem(position).getName());
        dishPrice.setText(Double.toString(getItem(position).getBasePrice()));
        dishRating.setText(Double.toString(getItem(position).getRating()));
        return element;
    }
}