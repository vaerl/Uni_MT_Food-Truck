package com.example.foodtruck.activities.operator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.foodtruck.R;

public class OperatorMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_menu);
    }

    public void openRoutenplanung(View v) {
        Intent in = new Intent(this, OperatorRoutebearbeitenActivity.class);
        startActivity(in);
    }

    public void openSpeisekarte(View v) {
        Intent in = new Intent(this, OperatorSpeisekarteActivity.class);
        startActivity(in);
    }

    public void openBestellungen(View v) {
        Intent in = new Intent(this, OperatorBestellungenActivity.class);
        startActivity(in);
    }

    public void openLebensmittelbestellung(View v) {
        Intent in = new Intent(this, OperatorLebensmittelbestellungActivity.class);
        startActivity(in);
    }

    public void openAktuellerstandort(View v) {
        Intent in = new Intent(this, OperatorAktuellerstandortActivity.class);
        startActivity(in);
    }


}
