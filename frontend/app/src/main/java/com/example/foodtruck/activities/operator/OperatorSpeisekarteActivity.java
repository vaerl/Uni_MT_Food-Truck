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

public class OperatorSpeisekarteActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    private static int RC_NEW_DISH = 1;
    private static int RC_EDIT_DISH = 2;
    public static String INTENT_NEW_DISH = "new_dish";
    public static String INTENT_EDIT_DISH = "edit_dish";

    Dish[] gerichte = {};
    ListView lv;
    AdvancedOwnerSpeisekarteAdapter advancedToDoAdapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_speisekarte);
        queue = Volley.newRequestQueue(this);
        loadDishes();
    }

    public void loadDishes() {
        // Gerichte laden
        Log.d(TAG, "show menu: try to get menu.");
        // menu/preorder oder menu/reservatio?
        GsonRequest<Dish[], Dish[]> requestGerichte = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/menu/preorder", Dish[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                gerichte = response;
                lv = findViewById(R.id.speisekarte_ListView);
                advancedToDoAdapter = new AdvancedOwnerSpeisekarteAdapter(this, 0, gerichte);
                lv.setAdapter(advancedToDoAdapter);
                lv.setOnItemClickListener((parent, view, lv_position, id) -> {
                    // get id von Gericht, übergebe id in nächste activity, lese in nächster activity mit id aus
                    Intent intent = new Intent(this, OperatorSpeiseBearbeitenActivity.class);
                    intent.putExtra(INTENT_EDIT_DISH, gerichte[lv_position]);
                    startActivityForResult(intent, RC_EDIT_DISH);
                });
            }
        }, error -> {
            Log.e(TAG, "Could not get Speisekarte!", error);
        });
        queue.add(requestGerichte);
    }

    public void openSpeiseNeu(View v) {
        startActivityForResult(new Intent(this, OperatorSpeiseNeuActivity.class), RC_NEW_DISH);
    }

    public void backButton(View v) {
        Intent in = new Intent(this, OperatorMenuActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        startActivity(new Intent(this, OperatorMenuActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_NEW_DISH && data.hasExtra(INTENT_NEW_DISH)) {
                Dish dish = (Dish) data.getSerializableExtra(INTENT_NEW_DISH);
                // save dish
                GsonRequest<Dish, Dish> requestGerichte = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/dishes", dish, Dish.class, DataService.getStandardHeader(), response -> {
                    if (response != null) {
                        Log.d(TAG, "onActivityResult: saved new dish.");
                        loadDishes();
                    }
                }, error -> {
                    Log.e(TAG, "Could not save Dish!", error);
                });
                queue.add(requestGerichte);
            } else if (requestCode == RC_EDIT_DISH && data.hasExtra(INTENT_EDIT_DISH)) {
                Dish dish = (Dish) data.getSerializableExtra(INTENT_EDIT_DISH);
                // save dish
                GsonRequest<Dish, Dish> requestGerichte = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/dishes/" + dish.getId() + "/update", dish, Dish.class, DataService.getStandardHeader(), response -> {
                    if (response != null) {
                        Log.d(TAG, "onActivityResult: updated dish.");
                        loadDishes();
                    }
                }, error -> {
                    Log.e(TAG, "Could not update Dish!", error);
                });
                queue.add(requestGerichte);
            }
        } else {
            Log.e(TAG, "onActivityResult: resultCode != OK");
        }
    }
}

