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
import com.example.foodtruck.adapter.AdvancedOwnerSpeisekarteAdapter;
import com.example.foodtruck.model.Dish;

import java.util.HashMap;
import java.util.Map;

public class OwnerSpeisekarteActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    private static int RC_NEW_DISH = 1;
    private static String INTENT_NEW_DISH = "new_dish";
    private static String INTENT_EDIT_DISH = "edit_dish";

    Dish[] gerichte;
    ListView lv;
    AdvancedOwnerSpeisekarteAdapter advancedToDoAdapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_speisekarte);


        String EXTRA_PARAMETER = "gericht";
        queue = Volley.newRequestQueue(this);
        lv = findViewById(R.id.speisekarte_ListView);
        advancedToDoAdapter = new AdvancedOwnerSpeisekarteAdapter(this, 0, gerichte);
        lv.setAdapter(advancedToDoAdapter);

        loadDishes();
    }

    public void loadDishes() {
        // Gerichte laden
        Log.d(TAG, "show menu: try to get Speisekarte");
        // menu/preorder oder menu/reservatio?
        GsonRequest<Dish[], Dish[]> requestGerichte = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/menu/preorder", Dish[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                gerichte = response;
                lv.setOnItemClickListener((parent, view, lv_position, id) -> {
                    // get id von Gericht, übergebe id in nächste activity, lese in nächster activity mit id aus
                    Intent intent = new Intent(OwnerSpeisekarteActivity.this, OwnerSpeisebearbeitenActivity.class);
                    intent.putExtra(INTENT_EDIT_DISH, gerichte[lv_position]);
                    startActivity(intent);
                });
            }
        }, error -> {
            Log.e(TAG, "Could not get Speisekarte!", error);
        });
        queue.add(requestGerichte);
    }

    public void openSpeiseNeu(View v) {
        Intent in = new Intent(this, OwnerSpeiseneuActivity.class);
        startActivityForResult(in, RC_NEW_DISH);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            if (requestCode == RC_NEW_DISH && data.hasExtra(INTENT_NEW_DISH)) {
                Dish dish = (Dish) data.getSerializableExtra(INTENT_NEW_DISH);
                // save dish
                GsonRequest<Dish, Dish> requestGerichte = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/dishes", dish, Dish.class, DataService.getStandardHeader(), response -> {
                    if (response != null) {
                        Log.d(TAG, "onActivityResult: saved new dish.");
                    }
                }, error -> {
                    Log.e(TAG, "Could not save Dish!", error);
                });
                queue.add(requestGerichte);
                // update
                loadDishes();
            }
        } else {
            Log.e(TAG, "onActivityResult: resultCode != 0");
        }
    }
}

