package com.example.foodtruck.activities.operator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class OwnerSpeiseBearbeitenActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    Dish dish;

    private List<Ingredient> ingredients = new ArrayList<>();
    private static int RC_NEW_INGREDIENT = 1;
    public static String INTENT_NEW_INGREDIENT = "new_ingredient";
    public static String INTENT_NEW_AMOUNT = "new_amount";

    EditText gerichtName;
    EditText gerichtPreis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_speisebearbeiten);

        gerichtName = findViewById(R.id.bearb_name_input);
        gerichtPreis = findViewById(R.id.bearb_preis_input);

        if (getIntent().hasExtra(OwnerSpeisekarteActivity.INTENT_EDIT_DISH)) {
            dish = (Dish) getIntent().getSerializableExtra(OwnerSpeisekarteActivity.INTENT_EDIT_DISH);
            gerichtName.setText(dish.getName());
            gerichtPreis.setText(Double.toString(dish.getBasePrice()));
        }
    }

    public void bearbAbschliessen(View v) {
        String name = gerichtName.getText().toString();
        String preisString = gerichtPreis.getText().toString();
        if (name.equals("")) {
            gerichtName.setError("Name fehlt");
            name = dish.getName();
        }
        double preis;
        if (preisString.equals("")) {
            gerichtPreis.setError("Preis fehlt");
            preis = dish.getBasePrice();
        } else {
            preis = Double.parseDouble(preisString);
        }
        ingredients.addAll(dish.getIngredients());
        Dish newDish = new Dish(name, preis, ingredients);
        newDish.setId(dish.getId());
        Intent returnIntent = new Intent();
        returnIntent.putExtra(OwnerSpeisekarteActivity.INTENT_EDIT_DISH, newDish);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    public void openAddzutatBearb(View v) {
        startActivityForResult(new Intent(this, OwnerAddzutatActivity.class), RC_NEW_INGREDIENT);
    }

    public void backButton(View v) {
        Intent in = new Intent(this, OwnerSpeisekarteActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_NEW_INGREDIENT && data.hasExtra(INTENT_NEW_INGREDIENT) && data.hasExtra(INTENT_NEW_AMOUNT)) {
                String ingredient = (String) data.getSerializableExtra(INTENT_NEW_INGREDIENT);
                int amount = data.getIntExtra(INTENT_NEW_AMOUNT, 1);
                ingredients.add(new Ingredient(ingredient, amount));

            }
        } else {
            Log.e(TAG, "onActivityResult: resultCode != 0");
        }
    }

}