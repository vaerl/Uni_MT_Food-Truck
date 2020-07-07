package com.example.foodtruck.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdvancedCustomerOrderDetailsAdapter extends ArrayAdapter<Dish> {

    public AdvancedCustomerOrderDetailsAdapter(Context context, int textviewResourceId, Dish[] objects){
        super(context, textviewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_customer_show_menu_item, null);
        }

        TextView dishName = element.findViewById(R.id.order_details_dish_name_c);
        TextView dishPrice = element.findViewById(R.id.order_details_dish_price_c);
        Button rateButton = element.findViewById(R.id.order_details_dish_rating_button_c);

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: POST Request für speichern des rating - Wohin Methode?
                postRating();
            }
        });

        dishName.setText(Objects.requireNonNull(getItem(position)).getName());
        dishPrice.setText(Double.toString(Objects.requireNonNull(getItem(position)).getPrice()));
        return element;
    }

    public void postRating(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        RequestQueue queue = Volley.newRequestQueue(this);
        String operatorId = "1";

        // Reservation
        // ---------------------------------------------------------------
        Log.d(TAG, "show route: try to get reservation manu");
        GsonRequest<Dish[]> requestReservation = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + operatorId + "/menu/reservation", Dish[].class, params, response -> {
            if (response != null) {
                dishesReservation = response;
                AdvancedCustomerShowMenuAdapter advancedToDoAdapterReservation = new AdvancedCustomerShowMenuAdapter(this, 0, dishesReservation);
                lvReservation.setAdapter(advancedToDoAdapterReservation);
            }
        }, error -> {
            Log.e(TAG, "Could not get reservation menu!", error);
        });
        queue.add(requestReservation);
    }

}
