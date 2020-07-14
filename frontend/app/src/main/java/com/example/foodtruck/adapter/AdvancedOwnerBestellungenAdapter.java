package com.example.foodtruck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.order.Order;


public class AdvancedOwnerBestellungenAdapter extends ArrayAdapter<Order> {

    public AdvancedOwnerBestellungenAdapter(Context context, int textviewResourceId, Order[] objects) {
        super(context, textviewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_owner_bestellungen_item, null);
        }

        TextView id_textView5 = element.findViewById(R.id.id_textView5);
        TextView status_textView17 = element.findViewById(R.id.status_textView17);

        id_textView5.setText(getItem(position).getId().toString());
        status_textView17.setText(getItem(position).getStatus().toString());

        return element;
    }

}

