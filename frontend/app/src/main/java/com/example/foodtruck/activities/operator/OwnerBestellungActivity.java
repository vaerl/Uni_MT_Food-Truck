package com.example.foodtruck.activities.operator;

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
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.model.order.Order;

public class OwnerBestellungActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "order";

    Order order;
    TextView orderTime;
    TextView orderNumber;
    TextView orderLocation;
    TextView orderPerson;
    TextView orderStatus;
    ListView gerichteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_bestellung);
        orderTime = findViewById(R.id.setUhrzeit);
        orderNumber = findViewById(R.id.setId);
        orderLocation = findViewById(R.id.setOrt);
        orderPerson = findViewById(R.id.setPerson);
        orderStatus = findViewById(R.id.setStatus);
        gerichteListView = findViewById(R.id.gerichte_bestellung_ListView);

        if (getIntent().hasExtra(EXTRA_PARAMETER)) {
            Intent intent = getIntent();
            order = (Order) intent.getSerializableExtra(EXTRA_PARAMETER);
            orderNumber.setText(order.getId().toString());
            orderLocation.setText(order.getLocation().getName());
            orderPerson.setText(order.getCustomer().getName());
            orderStatus.setText(order.getStatus().toString());
            updateStatus();
        }
    }

    public void startOrder(View v) {
        setStatus(Order.Status.STARTED);
    }

    public void completeOrder(View v) {
        setStatus(Order.Status.DONE);
    }

    public void setStatus(Order.Status status) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "post location: try to post status: " + status.toString());
        GsonRequest<Order.Status, Boolean> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/order/" + order.getId() + "/status", status, Boolean.class, DataService.getStandardHeader(), response -> {
            if (response) {
                order.setStatus(status);
                orderStatus.setText(status.toString());
                updateStatus();
            }
        }, error -> {
            Log.e(TAG, "Could not post location!", error);
        });
        queue.add(request);
    }

    public void backButton(View v) {
        Intent in = new Intent(this, OwnerBestellungenActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

    public void updateStatus() {
        if (order.getStatus() == Order.Status.STARTED) {
            findViewById(R.id.beginnen_button2).setVisibility(View.INVISIBLE);
        }
        if (order.getStatus() == Order.Status.DONE) {
            findViewById(R.id.beginnen_button2).setVisibility(View.INVISIBLE);
            findViewById(R.id.abschliessen_button2).setVisibility(View.INVISIBLE);
        }
    }

}