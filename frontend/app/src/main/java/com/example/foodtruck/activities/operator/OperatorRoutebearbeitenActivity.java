package com.example.foodtruck.activities.operator;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.foodtruck.adapter.AdvancedOwnerRoutebearbeitenAdapter;
import com.example.foodtruck.model.Location;

public class OperatorRoutebearbeitenActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();
    private static int RC_EDIT_LOCATION = 1;
    public static String INTENT_EDIT_LOCATION = "edit_intent";

    private static int RC_ADD_LOCATION = 2;
    public static String INTENT_Add_LOCATION = "add_intent";

    private Location[] standorte;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_routebearbeiten);
        lv = findViewById(R.id.routebearbeiten_ListView);
        updateLocations();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_EDIT_LOCATION || requestCode == RC_ADD_LOCATION) {
                updateLocations();
            }
        }
    }

    public void updateLocations() {
        Log.d(TAG, "show route: try to get locations");
        RequestQueue queue = Volley.newRequestQueue(this);
        GsonRequest<Location[], Location[]> request = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/route", Location[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                standorte = response;
                AdvancedOwnerRoutebearbeitenAdapter advancedToDoAdapter = new AdvancedOwnerRoutebearbeitenAdapter(this, 0, standorte);
                lv.setAdapter(advancedToDoAdapter);
                lv.setOnItemClickListener((parent, view, lv_position, id) -> {
                    Intent intent = new Intent(this, OperatorStandortbearbeitenActivity.class);
                    intent.putExtra(INTENT_EDIT_LOCATION, standorte[lv_position]);
                    startActivityForResult(intent, RC_EDIT_LOCATION);
                });
            }
        }, error -> {
            Log.e(TAG, "Could not get locations!", error);
        });
        queue.add(request);
    }

    public void openAddstandort(View v) {
        startActivityForResult(new Intent(this, OperatorAddstandortActivity.class), RC_EDIT_LOCATION);
    }

    public void routeBearbAbschliessen(View v) {
        ownerHome(v);
    }

    public void backButton(View v) {
        super.onBackPressed();
    }

    public void ownerHome(View v) {
        startActivity(new Intent(this, OperatorMenuActivity.class));
    }


}