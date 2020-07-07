package com.example.foodtruck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.model.Location;

import java.util.HashMap;
import java.util.Map;

public class CustomerTruckInfoActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_truckinfos);
        getCurrentLocation();
    }

    public void getCurrentLocation() {
        Log.d(TAG, "truck-infos: try to get current location");

        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");

        RequestQueue queue = Volley.newRequestQueue(this);
        String operatorId = "1";

        TextView currentLocation =  findViewById(R.id.current_truck_location);

        GsonRequest<Location> request = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + operatorId + "/location", Location.class, params, response -> {
            if (response!= null) {
                currentLocation.setText(response.getName());
            }
        }, error -> {
            Log.e(TAG, "Could not get current location!", error);
        });
        queue.add(request);
    }

    public void showRoute(View v){
        Intent in = new Intent(this, CustomerShowRouteActivity.class);
        startActivity(in);
    }

    public void showMenu(View v){
        Intent in = new Intent(this, CustomerShowMenuActivity.class);
        startActivity(in);
    }
}
