package com.example.foodtruck.activities.operator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.foodtruck.R;

public class OwnerMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_menu);
    }

    public void openRoutenplanung(View v) {
        Intent in = new Intent(this, OwnerRoutenplanungActivity.class);
        startActivity(in);
    }

    public void openSpeisekarte(View v) {
        Intent in = new Intent(this, OwnerSpeisekarteActivity.class);
        startActivity(in);
    }

    public void openBestellungen(View v) {
        Intent in = new Intent(this, OwnerBestellungenActivity.class);
        startActivity(in);
    }

    public void openLebensmittelbestellung(View v) {
        Intent in = new Intent(this, OwnerLebensmittelbestellungActivity.class);
        startActivity(in);
    }

    public void openAktuellerstandort(View v) {
        Intent in = new Intent(this, OwnerAktuellerstandortActivity.class);
        startActivity(in);
    }


}
