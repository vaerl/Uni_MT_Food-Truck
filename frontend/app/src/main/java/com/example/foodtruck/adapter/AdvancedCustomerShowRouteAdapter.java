package com.example.foodtruck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Location;

public class AdvancedCustomerShowRouteAdapter extends ArrayAdapter<Location> {

    public AdvancedCustomerShowRouteAdapter(Context context, int textviewResourceId, Location[] objects){
        super(context, textviewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_customer_route_item, null);
        }

        TextView locationName = element.findViewById(R.id.location_route_c_text);
        TextView locationArrival = element.findViewById(R.id.location_route_arrival_c_text);
        TextView locationDeparture = element.findViewById(R.id.location_route_departure_c_text);

        locationName.setText(getItem(position).getName());
        locationArrival.setText(getItem(position).getArrival().toString());
        locationDeparture.setText(getItem(position).getDeparture().toString());
        return element;
    }
}
