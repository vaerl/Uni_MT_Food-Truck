package com.example.foodtruck.activities.operator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.foodtruck.R;
import com.example.foodtruck.adapter.AdvancedStockAdapter;
import com.example.foodtruck.model.Ingredient;

public class OperatorLebensmittelbestellungZutatenActivity extends AppCompatActivity {

    private Ingredient[] ingredients;
    public static String INTENT_STOCK = "stock";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_lebensmittelbestellung_zutaten);
        if (getIntent().hasExtra(INTENT_STOCK)) {
            ingredients = (Ingredient[]) getIntent().getSerializableExtra(INTENT_STOCK);
            ListView lv = findViewById(R.id.alleZutaten_ListView);
            AdvancedStockAdapter advancedStockAdapter = new AdvancedStockAdapter(this, 0, ingredients);
            lv.setAdapter(advancedStockAdapter);
        }
    }


    public void backButton(View v) {
        Intent in = new Intent(this, OperatorLebensmittelbestellungActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OperatorMenuActivity.class);
        startActivity(in);
    }

}