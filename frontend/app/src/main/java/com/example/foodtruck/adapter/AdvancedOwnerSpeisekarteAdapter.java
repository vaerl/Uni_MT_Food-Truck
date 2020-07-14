package com.example.foodtruck.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;

public class AdvancedOwnerSpeisekarteAdapter extends ArrayAdapter<Dish> {

    public AdvancedOwnerSpeisekarteAdapter(@NonNull Context context, int resource, Dish[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_owner_speisekarte_item, null);
        }

        TextView gericht = element.findViewById(R.id.gericht_textView3);
        TextView preis = element.findViewById(R.id.preis_textView3);

        gericht.setText(getItem(position).getName());
        preis.setText(Double.toString(getItem(position).getBasePrice()));

        return element;
    }
}

