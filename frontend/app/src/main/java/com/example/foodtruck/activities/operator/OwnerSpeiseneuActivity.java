package com.example.foodtruck.activities.operator;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.EditText;

        import com.example.foodtruck.R;
        import com.example.foodtruck.model.Dish;

        import java.util.HashMap;

public class OwnerSpeiseneuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_speiseneu);
    }

    public void speiseAnlegen(View v) {
        String name = ((EditText) findViewById(R.id.neu_name_input)).getText().toString();
        double basePrice = Double.parseDouble(((EditText) findViewById(R.id.neu_preis_input)).getText().toString());
        Dish dish = new Dish(name, basePrice, new HashMap<>());
        Intent returnIntent = new Intent();
        returnIntent.putExtra(OwnerSpeisekarteActivity.INTENT_NEW_DISH, dish);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    public void openAddzutat(View v) {

        // richtige ID für neues Gericht ermitteln und übergeben??

        Intent in = new Intent(this, OwnerAddzutatActivity.class);
        startActivity(in);
    }

    public void backButton(View v) {
        Intent in = new Intent(this, OwnerSpeisekarteActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }



}