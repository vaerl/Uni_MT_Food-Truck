package com.example.foodtruck.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.DishWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdvancedCustomerOrderDetailsAdapter extends ArrayAdapter<DishWrapper> {

    private String TAG = getClass().getSimpleName();

    public AdvancedCustomerOrderDetailsAdapter(Context context, int textviewResourceId, DishWrapper[] dishWrappers) {
        super(context, textviewResourceId, dishWrappers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_customer_order_details_item, null);
        }

        TextView dishName = element.findViewById(R.id.order_details_dish_name_c);
        TextView dishPrice = element.findViewById(R.id.order_details_dish_price_c);
        Button rateButton = element.findViewById(R.id.order_details_dish_rating_button_c);
        dishName.setText(Objects.requireNonNull(getItem(position)).getDish().getName());
        dishPrice.setText(Double.toString(Objects.requireNonNull(getItem(position)).getDish().getBasePrice()));

        View finalElement = element;
        rateButton.setOnClickListener(view -> {
            String rating = ((EditText) finalElement.findViewById(R.id.order_details_dish_rating_c)).getText().toString();
            if(rating.equals("")){
                return;
            }
            postRating(rateButton.getContext(), Objects.requireNonNull(getItem(position)).getDish().getId(), Double.valueOf(((EditText) finalElement.findViewById(R.id.order_details_dish_rating_c)).getText().toString()));
        });
        return element;
    }

    public void postRating(Context context, Long id, Double rating) {
        RequestQueue queue = Volley.newRequestQueue(context);

        // Reservation
        // ---------------------------------------------------------------
        Log.d(TAG, "postRating: trying to rate dish.");
        GsonRequest<Double, Double> requestReservation = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/dishes/" + id + "/rate", rating, Double.class, DataService.getStandardHeader(), response -> {
            Log.d(TAG, "postRating: rated dish.");
        }, error -> {
            Log.e(TAG, "Could not rate dish!", error);
        });
        queue.add(requestReservation);
    }

}