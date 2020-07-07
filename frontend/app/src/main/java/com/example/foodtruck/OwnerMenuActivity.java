package com.example.foodtruck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class OwnerMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_menu);
    }

    public void ownerHome(View v) {
        setContentView(R.layout.activity_owner_menu);
    }

    public void openRoutenplanung(View v)  {
        setContentView(R.layout.activity_owner_routenplanung);
    }

    public void openSpeisekarte(View v) {
        setContentView(R.layout.activity_owner_speisekarte);
    }

    public void openBestellungen(View v) {
        setContentView(R.layout.activity_owner_bestellungen);
    }

    public void openLebensmittelbestellung(View v) {
        setContentView(R.layout.activity_owner_speisekarte);
    }

    public void openAktuellerstandort(View v) {
        setContentView(R.layout.activity_owner_aktuellerstandort);
    }


}
