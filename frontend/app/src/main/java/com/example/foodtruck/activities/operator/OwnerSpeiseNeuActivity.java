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

public class OwnerSpeiseNeuActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private List<Ingredient> ingredients = new ArrayList<>();
    private static int RC_NEW_INGREDIENT = 1;
    public static String INTENT_NEW_INGREDIENT = "new_ingredient";
    public static String INTENT_NEW_AMOUNT = "new_amount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_speiseneu);
    }

    public void speiseAnlegen(View v) {
        String name = ((EditText) findViewById(R.id.neu_name_input)).getText().toString();
        if (name.equals("")) {
            ((EditText) findViewById(R.id.neu_name_input)).setError("Name fehlt");
            return;
        }
        if (((EditText) findViewById(R.id.neu_preis_input)).getText().toString().equals("")) {
            ((EditText) findViewById(R.id.neu_preis_input)).setError("Preis fehlt");
            return;
        }
        double basePrice = Double.parseDouble(((EditText) findViewById(R.id.neu_preis_input)).getText().toString());
        Dish dish = new Dish(name, basePrice, ingredients);
        Intent returnIntent = new Intent();
        returnIntent.putExtra(OwnerSpeisekarteActivity.INTENT_NEW_DISH, dish);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void openAddzutat(View v) {
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