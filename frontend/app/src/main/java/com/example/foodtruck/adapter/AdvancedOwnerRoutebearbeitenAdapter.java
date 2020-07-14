package com.example.foodtruck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.foodtruck.FormattingHelper;
import com.example.foodtruck.R;
import com.example.foodtruck.model.Location;


public class AdvancedOwnerRoutebearbeitenAdapter extends ArrayAdapter<Location> {

    public AdvancedOwnerRoutebearbeitenAdapter(Context context, int textviewResourceId, Location[] objects) {
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
        ankunft_textView.setText(FormattingHelper.localDateTimeFormatter(getItem(position).getArrival()));
        abfahrt_textView.setText(FormattingHelper.localDateTimeFormatter(getItem(position).getDeparture()));

        return element;
    }

}
