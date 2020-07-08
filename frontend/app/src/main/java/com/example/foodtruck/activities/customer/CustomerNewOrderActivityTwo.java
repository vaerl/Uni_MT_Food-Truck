package com.example.foodtruck.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.adapter.AdvancedCustomerOrderDetailsAdapter;
import com.example.foodtruck.adapter.AdvancedCustomerShowMenuAdapter;
import com.example.foodtruck.adapter.AdvancedCustomerShowRouteAdapter;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.Location;
import com.example.foodtruck.model.order.Order;
import com.example.foodtruck.model.order.PreOrder;
import com.example.foodtruck.model.order.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class CustomerNewOrderActivityTwo  extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "order";
    String EXTRA_PARAMETER2 = "type";

    Spinner locationSpinner;
    TextView totalCost;

    Location[] locations;
    Location customerLocation;
    String type;

    Reservation reservation;
    PreOrder preOrder;

    ArrayList<Location> activeLocations = new ArrayList<>();
    ArrayList<String> activeLocationsNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_location);

        locationSpinner = findViewById(R.id.new_order_location_spinner);
        totalCost = findViewById(R.id.order_payment_price);

        if(getIntent().hasExtra(EXTRA_PARAMETER)){
            Intent intent = getIntent();
            type = intent.getStringExtra(EXTRA_PARAMETER2);
            if (type.equals("reservation")) {
                reservation = (Reservation) intent.getSerializableExtra(EXTRA_PARAMETER);
                totalCost.setText(Double.toString(reservation.getPrice()));
            } else {
                preOrder = (PreOrder) intent.getSerializableExtra(EXTRA_PARAMETER);
                totalCost.setText(Double.toString(preOrder.getPrice()));
            }
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        Log.d(TAG, "get locations: try to get route");
        GsonRequest<Location[], Location[]> requestRoute = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/route", Location[].class, DataService.getStandardHeader(), response -> {
            if (response!= null) {
                locations = response;

                for (Location location: locations) {
                    if (location.getStatus().equals(Location.Status.ARRIVING) || location.getStatus().equals(Location.Status.CURRENT) || location.getStatus().equals(Location.Status.OPEN)) {
                        activeLocations.add(location);
                        activeLocationsNames.add(location.getName());
                    }
                }
                ArrayAdapter<Object> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activeLocationsNames.toArray());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                locationSpinner.setAdapter(adapter);
                if(DataService.getInstance(this).isPresent(DataService.Location_ID_TAG)) {
                    locationSpinner.setSelection(adapter.getPosition(customerLocation.getName()));
                }
            }
        }, error -> {
            Log.e(TAG, "Could not get locations!", error);
        });

        String id = DataService.getInstance(this).getLocationId();
        if(DataService.getInstance(this).isPresent(DataService.Location_ID_TAG)) {
            Log.d(TAG, "get location: try to get customer location");
            GsonRequest<Location, Location> requestCustomerLocation = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/location/" + DataService.getInstance(this).getLocationId(), Location.class, DataService.getStandardHeader(), response -> {
                if (response != null) {
                    customerLocation = response;
                    queue.add(requestRoute);
                }
            }, error -> {
                Log.e(TAG, "Could not get location!", error);
            });
            queue.add(requestCustomerLocation);
        } else {
            queue.add(requestRoute);
        }




    }

    public void forwardNewOrderActivityTwo(View v) {
        String selectedLocationName = locationSpinner.getSelectedItem().toString();
        String selectedLocationId = "";

        for (Location location: activeLocations) {
            if (location.getName().equals(selectedLocationName)){
                selectedLocationId = location.getId().toString();
                break;
            }
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "post order: try to post order");
        if (type.equals("reservation")) {
            GsonRequest<Reservation[], Boolean> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID  + "/orders/" + selectedLocationId + "/" + DataService.getInstance(this).getUserId() + "/reservation", new Reservation[]{reservation}, Boolean.class, DataService.getStandardHeader(), response -> {
                Intent in = new Intent(this, CustomerThankYouActivity.class);
                startActivity(in);
            }, error -> {
                Log.e(TAG, "Could not post order!", error);
            });
            queue.add(request);
        } else {
            GsonRequest<PreOrder[], Boolean> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID  + "/orders/" + selectedLocationId + "/" + DataService.getInstance(this).getUserId() + "/preorders", new PreOrder[]{preOrder}, Boolean.class, DataService.getStandardHeader(), response -> {
                Intent in = new Intent(this, CustomerThankYouActivity.class);
                startActivity(in);
            }, error -> {
                Log.e(TAG, "Could not post order!", error);
            });
            queue.add(request);
        }
    }

}
