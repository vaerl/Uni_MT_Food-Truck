package com.example.foodtruck.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.adapter.AdvancedCustomerOrderDetailsAdapter;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.DishWrapper;
import com.example.foodtruck.model.order.Order;

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
        setContentView(R.layout.activity_customer_order_details);

        orderNumber = findViewById(R.id.order_details_number_c);
        orderStatus = findViewById(R.id.order_details_status_c);
        lvReservation = findViewById(R.id.customer_order_details_list);

        if (getIntent().hasExtra(EXTRA_PARAMETER)) {
            Intent intent = getIntent();
            Order order = (Order) intent.getSerializableExtra(EXTRA_PARAMETER);
            orderNumber.setText(order.getId().toString());
            orderStatus.setText(order.getStatus().toString());
            DishWrapper[] dishWrappers = new DishWrapper[order.getItems().size()];
            for (int i = 0; i < order.getItems().size(); i++) {
                dishWrappers[i] = order.getItems().get(i);
            }
            AdvancedCustomerOrderDetailsAdapter advancedCustomerOrderDetailsAdapter = new AdvancedCustomerOrderDetailsAdapter(this, 0, dishWrappers);
            lvReservation.setAdapter(advancedCustomerOrderDetailsAdapter);
        }

    }

    public void getToHome(View v) {
        Intent in = new Intent(this, CustomerMenuActivity.class);
        startActivity(in);
    }


}
