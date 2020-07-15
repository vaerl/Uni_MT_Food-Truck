package com.example.foodtruck.activities.operator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.foodtruck.R;

public class OperatorAddzutatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_addzutat);
    }

    public void zutatAnlegen(View v) {
        String ingredient = ((EditText) findViewById(R.id.zutatname_editText2)).getText().toString();
        String amount = ((EditText) findViewById(R.id.menge_editTextNumber2)).getText().toString();
        if (ingredient.equals("")) {
            ((EditText) findViewById(R.id.zutatname_editText2)).setError("Name fehlt");
            return;
        }
        if (amount.equals("")) {
            ((EditText) findViewById(R.id.menge_editTextNumber2)).setError("Menge fehlt");
            return;
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(OperatorSpeiseNeuActivity.INTENT_NEW_INGREDIENT, ingredient);
        returnIntent.putExtra(OperatorSpeiseNeuActivity.INTENT_NEW_AMOUNT, Integer.valueOf(amount));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    public void backButton(View v) {
        Intent in = new Intent(this, OperatorSpeiseNeuActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OperatorMenuActivity.class);
        startActivity(in);
    }


}