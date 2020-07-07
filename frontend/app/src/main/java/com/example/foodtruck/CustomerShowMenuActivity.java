package com.example.foodtruck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.adapter.AdvancedCustomerShowMenuAdapter;
import com.example.foodtruck.adapter.AdvancedCustomerShowRouteAdapter;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.Location;

import java.util.HashMap;
import java.util.Map;

public class CustomerShowMenuActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    Dish[] dishesReservation;
    Dish[] dishesPreorder;

    ListView lv;

    AdvancedCustomerShowMenuAdapter advancedToDoAdapterReservation = new AdvancedCustomerShowMenuAdapter(this, 0, dishesReservation);
    AdvancedCustomerShowMenuAdapter advancedToDoAdapterPreorder = new AdvancedCustomerShowMenuAdapter(this, 0, dishesPreorder);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_show_menu);

        lv = findViewById(R.id.customer_show_menu_list);

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
                View v = null;
                setMenuToReservation(v);
            }
        }, error -> {
            Log.e(TAG, "Could not get reservation menu!", error);
        });
        queue.add(requestReservation);

        // Preorder
        // ---------------------------------------------------------------
        Log.d(TAG, "show menu: try to get preorder menu");
        GsonRequest<Dish[]> requestPreorder = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + operatorId + "/menu/preorder", Dish[].class, params, response -> {
            if (response != null) {
                dishesPreorder = response;
            }
        }, error -> {
            Log.e(TAG, "Could not get preorder menu!", error);
        });
        queue.add(requestPreorder);

    }

    public void setMenuToPreorder(View v){
        lv.setAdapter(advancedToDoAdapterPreorder);
    }

    public void setMenuToReservation(View v){
        lv.setAdapter(advancedToDoAdapterReservation);
    }

}
