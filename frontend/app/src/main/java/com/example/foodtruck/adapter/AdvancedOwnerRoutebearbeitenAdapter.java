package com.example.foodtruck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Location;

import java.util.Objects;


public class AdvancedOwnerRoutebearbeitenAdapter extends ArrayAdapter<Location> {

    public AdvancedOwnerRoutebearbeitenAdapter(Context context, int textviewResourceId, Location[] objects){
        super(context, textviewResourceId, objects);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View element = convertView;
            if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_owner_routebearbeiten_item, null);
            }

            TextView standort_textView = element.findViewById(R.id.standort_textView);
            TextView ankunft_textView = element.findViewById(R.id.ankunft_textView);
            TextView abfahrt_textView = element.findViewById(R.id.abfahrt_textView);

            standort_textView.setText(getItem(position).getName());
            String arrivalTime = Objects.requireNonNull(getItem(position)).getArrival().getHour() + ":" + Objects.requireNonNull(getItem(position)).getArrival().getMinute();
            ankunft_textView.setText(arrivalTime);
            String departureTime = Objects.requireNonNull(getItem(position)).getDeparture().getHour() + ":" + Objects.requireNonNull(getItem(position)).getDeparture().getMinute();
            abfahrt_textView.setText(departureTime);

            return element;
    }

}
