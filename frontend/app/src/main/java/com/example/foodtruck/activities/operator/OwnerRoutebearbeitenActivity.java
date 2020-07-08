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

        import java.util.HashMap;
        import java.util.Map;

public class OwnerRoutebearbeitenActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    Location[] standorte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_routebearbeiten);

        ListView lv = findViewById(R.id.routebearbeiten_ListView);

        Log.d(TAG, "show route: try to get Standorte");

        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");

        RequestQueue queue = Volley.newRequestQueue(this);
        String operatorId = "1";

        GsonRequest<Location[]> request = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + operatorId + "/route", Location[].class, params, response -> {
            if (response!= null) {
                standorte = response;
                AdvancedOwnerRoutebearbeitenAdapter advancedToDoAdapter = new AdvancedOwnerRoutebearbeitenAdapter(this, 0, standorte);
                lv.setAdapter(advancedToDoAdapter);
            }
        }, error -> {
            Log.e(TAG, "Could not get locations!", error);
        });
        queue.add(request);

    }



    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }


}