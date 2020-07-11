package com.example.foodtruck.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Ingredient;

public class AdvancedStockAdapter extends ArrayAdapter<Ingredient> {

    public AdvancedStockAdapter(@NonNull Context context, int resource, Ingredient[] ingredients) {
        super(context, resource, ingredients);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_owner_lebensmittelbestellung_zutaten_item, null);
        }

        ((TextView) element.findViewById(R.id.zutat_textView2)).setText(getItem(position).getName());
        ((TextView) element.findViewById(R.id.menge_textView23)).setText(String.valueOf(getItem(position).getAmount()));

        return element;
    }
}
