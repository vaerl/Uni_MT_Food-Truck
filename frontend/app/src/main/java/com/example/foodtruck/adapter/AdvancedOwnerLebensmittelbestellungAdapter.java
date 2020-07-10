package com.example.foodtruck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.DishWrapper;
import com.example.foodtruck.model.order.PreOrder;

import java.util.Objects;

public class AdvancedOwnerLebensmittelbestellungAdapter extends ArrayAdapter<Dish> {

    PreOrder[] preOrders;

    public AdvancedOwnerLebensmittelbestellungAdapter(Context context, int resource, Dish[] objects, PreOrder[] preOrders) {
        super(context, resource, objects);
        this.preOrders = preOrders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_owner_lebensmittelbestellung_item, null);
        }

        TextView gericht = element.findViewById(R.id.Gericht_textView2);
        int amount = 0;
        for (PreOrder preOrder: preOrders) {
            for (DishWrapper dish: preOrder.getItems()){
                if (dish.getId().equals(Objects.requireNonNull(getItem(position)).getId())) {
                    amount += dish.getAmount();
                }
            }
        }

        TextView bestellungen = element.findViewById(R.id.bestellungen_textView);

        gericht.setText(Objects.requireNonNull(getItem(position)).getName());
        bestellungen.setText(Integer.toString(amount));

        return element;
    }
}
