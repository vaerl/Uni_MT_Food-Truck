package com.example.foodtruck.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.model.Location;
import com.example.foodtruck.model.user.Customer;

import java.util.List;

public class CustomerLocationActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_location);
    }

    public void continueWithoutSettingLocation(View v) {
        startActivity(new Intent(this, CustomerMenuActivity.class));
    }

    public void getNearestLocations(View v) {
        Double x = Double.valueOf(((EditText) findViewById(R.id.x_editText)).getText().toString());
        Double y = Double.valueOf(((EditText) findViewById(R.id.y_editText)).getText().toString());
        RequestQueue queue = Volley.newRequestQueue(this);
        GsonRequest<double[], Location[]> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/customer/" + DataService.getInstance(this).getUserId() + "/locations?operatorId=" + DataService.OPERATOR_ID, new double[]{x, y}, Location[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                findViewById(R.id.vorschlaege_button).setVisibility(View.INVISIBLE);
                for (int i = 0; i < response.length; i++) {
                    Log.d(TAG, "getNearestLocations: i: " + i);
                    Log.d(TAG, "getNearestLocations: Location: " + response[i].toString());
                    Log.d(TAG, "getNearestLocations: button-name: " + "standort" + (i + 1) + "_button");
                    int id = getResources().getIdentifier("standort" + (i + 1) + "_button", "id", this.getPackageName());
                    Log.d(TAG, "getNearestLocations: id: " + id);
                    ((Button) findViewById(id)).setText(response[i].getName());
                    findViewById(id).setVisibility(View.VISIBLE);
                    int finalI = i;
                    findViewById(id).setOnClickListener(view -> {
                        DataService.getInstance(this).setLocationId(response[finalI].getId());
                        // TODO set as standard in BE?
                        startActivity(new Intent(this, CustomerMenuActivity.class));
                    });
                }
                DataService.hideKeyboard(this);
            }
        }, error -> {
            Log.e(TAG, "getNearestLocations: Error: ", error);
        });
        queue.add(request);
    }

}
