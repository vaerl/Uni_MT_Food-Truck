package com.example.foodtruck.activities.operator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;

public class OwnerAddzutatBearbActivity extends AppCompatActivity {

    String EXTRA_PARAMETER2 = "gericht";


    TextView gerichtId;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_addzutatbearb);

        gerichtId = findViewById(R.id.textView24);

        if(getIntent().hasExtra(EXTRA_PARAMETER2)){
            Intent intent = getIntent();
            Dish gericht = (Dish) intent.getSerializableExtra(EXTRA_PARAMETER2);
            id = gericht.getId();
            gerichtId.setText(Long.toString(id));
        }

    }

    public void zutatAnlegenBearb(View v) {

        //hier Logik zum anlegen


        Intent in = new Intent(this, OwnerSpeiseBearbeitenActivity.class);
        startActivity(in);
    }


    public void backButton(View v) {
        Intent in = new Intent(this, OwnerSpeiseBearbeitenActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }


}