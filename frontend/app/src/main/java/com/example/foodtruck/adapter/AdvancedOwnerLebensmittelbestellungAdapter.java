package com.example.foodtruck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;

public class AdvancedOwnerLebensmittelbestellungAdapter extends ArrayAdapter<Dish> {

    public AdvancedOwnerLebensmittelbestellungAdapter(Context context, int resource, Dish[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_owner_lebensmittelbestellung_item, null);
        }

        TextView gericht = element.findViewById(R.id.Gericht_textView2);
        TextView bestellungen = element.findViewById(R.id.bestellungen_textView);

        gericht.setText(getItem(position).getName());
        //bestellungen.setText(getItem(position).getBestellungen().toString());

        return element;
    }
}
