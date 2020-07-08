package com.example.foodtruck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.adapter.AdvancedCustomerOrderDetailsAdapter;
import com.example.foodtruck.adapter.AdvancedCustomerOrdersAdapter;
import com.example.foodtruck.adapter.AdvancedCustomerShowMenuAdapter;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerShowOrderDetailsActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "order";

    Dish[] dishesReservation;

    TextView orderNumber;
    TextView orderStatus;
    ListView lvReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_show_menu);

        orderNumber = findViewById(R.id.order_details_number_c);
        orderStatus = findViewById(R.id.order_details_status_c);
        lvReservation = findViewById(R.id.customer_show_menu_reservation_list);

        if(getIntent().hasExtra(EXTRA_PARAMETER)){
            Intent intent = getIntent();
            Order order = (Order) intent.getSerializableExtra(EXTRA_PARAMETER);
            orderNumber.setText(order.getId().toString());
            orderStatus.setText(order.getStatus().toString());
            //TODO: Mapping Dishes in ListAdapter
            AdvancedCustomerOrderDetailsAdapter advancedToDoAdapterReservation = new AdvancedCustomerOrderDetailsAdapter(this, 0, new ArrayList<Map.Entry<Dish, Integer>>(order.getItems().entrySet()));
            lvReservation.setAdapter(advancedToDoAdapterReservation);
        }

    }





}
