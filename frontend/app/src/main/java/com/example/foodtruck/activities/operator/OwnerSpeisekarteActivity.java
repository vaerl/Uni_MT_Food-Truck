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
        import com.example.foodtruck.activities.customer.CustomerShowOrderDetailsActivity;
        import com.example.foodtruck.activities.customer.CustomerShowOrdersActivity;
        import com.example.foodtruck.adapter.AdvancedOwnerSpeisekarteAdapter;
        import com.example.foodtruck.model.Dish;

        import java.util.HashMap;
        import java.util.Map;

public class OwnerSpeisekarteActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "gericht";

    Dish[] gerichte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_speisekarte);

        ListView lv = findViewById(R.id.speisekarte_ListView);

        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        RequestQueue queue = Volley.newRequestQueue(this);
        String operatorId = "1";


        // Gerichte laden

        Log.d(TAG, "show menu: try to get Speisekarte");
        GsonRequest<Dish[]> requestGerichte = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + operatorId + "/menu/preorder", Dish[].class, params, response -> {
            if (response != null) {
                gerichte = response;
                AdvancedOwnerSpeisekarteAdapter advancedToDoAdapter = new AdvancedOwnerSpeisekarteAdapter(this, 0, gerichte);
                lv.setAdapter(advancedToDoAdapter);

                lv.setOnItemClickListener((parent, view, lv_position, id) -> {
                    Intent intent = new Intent(OwnerSpeisekarteActivity.this, OwnerSpeisebearbeitenActivity.class);
                    intent.putExtra(EXTRA_PARAMETER, gerichte[lv_position]);
                    startActivity(intent);
                });
            }
        }, error -> {
            Log.e(TAG, "Could not get Speisekarte!", error);
        });
        queue.add(requestGerichte);

    }

    public void openSpeiseneu(View v) {
        Intent in = new Intent(this, OwnerSpeiseneuActivity.class);
        startActivity(in);
    }


    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }





}

