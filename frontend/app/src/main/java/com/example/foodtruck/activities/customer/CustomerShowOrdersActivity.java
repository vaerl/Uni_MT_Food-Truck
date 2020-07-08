package com.example.foodtruck.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.adapter.AdvancedCustomerOrdersAdapter;
import com.example.foodtruck.model.order.Order;

import java.util.HashMap;
import java.util.Map;

public class CustomerShowOrdersActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "order";

    Order[] orders;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);

        lv = findViewById(R.id.customer_orders_list);

        RequestQueue queue = Volley.newRequestQueue(this);

        Log.d(TAG, "show orders: try to get orders");

        GsonRequest<Order[], Order[]> requestReservation = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/customer/" + DataService.getInstance(this).getUserId() + "/orders", Order[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                orders = response;
                AdvancedCustomerOrdersAdapter advancedToDoAdapterReservation = new AdvancedCustomerOrdersAdapter(this, 0, orders);
                lv.setAdapter(advancedToDoAdapterReservation);
                lv.setOnItemClickListener((parent, view, position, id) -> {
                    Intent intent = new Intent(CustomerShowOrdersActivity.this, CustomerShowOrderDetailsActivity.class);
                    intent.putExtra(EXTRA_PARAMETER, orders[position]);
                    startActivity(intent);
                });
            }
        }, error -> {
            Log.e(TAG, "Could not get orders!", error);
        });
        queue.add(requestReservation);
    }

}
