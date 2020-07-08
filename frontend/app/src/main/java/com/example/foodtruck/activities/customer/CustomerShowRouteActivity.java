package com.example.foodtruck.activities.customer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.adapter.AdvancedCustomerShowRouteAdapter;
import com.example.foodtruck.model.Location;

import java.util.HashMap;
import java.util.Map;

public class CustomerShowRouteActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    Location[] locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_route);
        ListView lv = findViewById(R.id.customer_show_route_list);

        Log.d(TAG, "show route: try to get locations");

        RequestQueue queue = Volley.newRequestQueue(this);

        GsonRequest<Location[], Location[]> request = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/route", Location[].class, DataService.getStandardHeader(), response -> {
            if (response!= null) {
                locations = response;
                AdvancedCustomerShowRouteAdapter advancedCustomerShowRouteAdapter = new AdvancedCustomerShowRouteAdapter(this, 0, locations);
                lv.setAdapter(advancedCustomerShowRouteAdapter);
            }
        }, error -> {
            Log.e(TAG, "Could not get locations!", error);
        });
        queue.add(request);

    }

}