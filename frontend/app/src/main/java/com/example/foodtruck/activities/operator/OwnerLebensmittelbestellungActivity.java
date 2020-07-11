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
import com.example.foodtruck.adapter.AdvancedOwnerLebensmittelbestellungAdapter;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.DishWrapper;
import com.example.foodtruck.model.Ingredient;
import com.example.foodtruck.model.order.PreOrder;

import java.util.ArrayList;

public class OwnerLebensmittelbestellungActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    Dish[] menu;
    PreOrder[] preOrders;
    AdvancedOwnerLebensmittelbestellungAdapter advancedToDoAdapter;
    Ingredient[] stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_lebensmittelbestellung);
        ListView lv = (ListView) findViewById(R.id.lebensmittelbestellung_ListView);
        RequestQueue queue = Volley.newRequestQueue(this);

        // bestellte Gerichte laden
        Log.d(TAG, "show menu: try to get bestellte Gerichte");
        GsonRequest<PreOrder[], PreOrder[]> requestPreOrders = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/preorders", PreOrder[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                preOrders = response;
                advancedToDoAdapter = new AdvancedOwnerLebensmittelbestellungAdapter(this, 0, menu, preOrders);
                lv.setAdapter(advancedToDoAdapter);
            }
        }, error -> {
            Log.e(TAG, "Could not get bestellte Gerichte!", error);
        });

        GsonRequest<Dish[], Dish[]> requestGerichte = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/menu/preorder", Dish[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                menu = response;
                queue.add(requestPreOrders);
            }
        }, error -> {
            Log.e(TAG, "Could not get bestellte Gerichte!", error);
        });
        queue.add(requestGerichte);
    }

    public void zeigeZutaten(View v) {
        Intent intent = new Intent(this, OwnerLBZutatenActivity.class);
        intent.putExtra(OwnerLBZutatenActivity.INTENT_STOCK, stock);
        startActivity(intent);
    }

    public void bestelleLebensmittel(View v) {
        // Hier Logik für Zusammenrechnung aller Zutaten
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (DishWrapper dishWrapper : advancedToDoAdapter.dishWrappers) {
            for (Ingredient ingredient : dishWrapper.getDish().getIngredients()) {
                if (ingredients.contains(ingredient)) {
                    ingredients.get(ingredients.indexOf(ingredient)).setAmount(ingredients.indexOf(ingredient) + ingredient.getAmount());
                } else {
                    ingredients.add(ingredient);
                }
            }
        }

        Ingredient[] ingredientsArray = new Ingredient[ingredients.size()];
        int counter = 0;
        for (Ingredient ingredient : ingredients) {
            ingredientsArray[counter++] = ingredient;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "post location: try to post needed ingredients");

        GsonRequest<Ingredient[], Ingredient[]> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/shopping", ingredientsArray, Ingredient[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                findViewById(R.id.speiseanlegen_button3).setVisibility(View.INVISIBLE);
                stock = response;
            }
            Log.d(TAG, "bestelleLebensmittel: something went wrong!");
        }, error -> {
            Log.e(TAG, "bestelleLebensmittel: something went wrong!", error);
        });
        queue.add(request);
    }


    public void backButton(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

}
