package com.example.foodtruck.activities.customer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.adapter.AdvancedCustomerShowMenuAdapter;
import com.example.foodtruck.model.Dish;

import java.util.HashMap;
import java.util.Map;

public class CustomerShowMenuActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    Dish[] dishesReservation;
    Dish[] dishesPreorder;

    ListView lvReservation;
    ListView lvPreorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_show_menu);

        lvReservation = findViewById(R.id.customer_show_menu_reservation_list);
        lvPreorder = findViewById(R.id.customer_show_menu_preorder_list);

        RequestQueue queue = Volley.newRequestQueue(this);

        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: position" + tab.getPosition());
               if(tab.getPosition() == 0){
                   setMenuToReservation(tabs);
               } else {
                   setMenuToPreorder(tabs);
               }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Reservation
        // ---------------------------------------------------------------
        Log.d(TAG, "show route: try to get reservation manu");
        GsonRequest<Dish[], Dish[]> requestReservation = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/menu/reservation", Dish[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                dishesReservation = response;
                AdvancedCustomerShowMenuAdapter advancedCustomerShowMenuAdapter = new AdvancedCustomerShowMenuAdapter(this, 0, dishesReservation, "reservation");
                lvReservation.setAdapter(advancedCustomerShowMenuAdapter);
            }
        }, error -> {
            Log.e(TAG, "Could not get reservation menu!", error);
        });
        queue.add(requestReservation);

        // Preorder
        // ---------------------------------------------------------------
        Log.d(TAG, "show menu: try to get preorder menu");
        GsonRequest<Dish[], Dish[]> requestPreorder = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/menu/preorder", Dish[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                dishesPreorder = response;
                AdvancedCustomerShowMenuAdapter advancedCustomerShowMenuAdapter = new AdvancedCustomerShowMenuAdapter(this, 0, dishesPreorder, "preorder");
                lvPreorder.setAdapter(advancedCustomerShowMenuAdapter);
            }
        }, error -> {
            Log.e(TAG, "Could not get preorder menu!", error);
        });
        queue.add(requestPreorder);

    }

    public void setMenuToPreorder(View v){
        Log.d(TAG, "setMenuToPreorder: changing to preorder");
        lvReservation.setVisibility(View.GONE);
        lvPreorder.setVisibility(View.VISIBLE);
    }

    public void setMenuToReservation(View v){
        Log.d(TAG, "setMenuToReservation: changing to reservation");
        lvPreorder.setVisibility(View.GONE);
        lvReservation.setVisibility(View.VISIBLE);
    }

}
