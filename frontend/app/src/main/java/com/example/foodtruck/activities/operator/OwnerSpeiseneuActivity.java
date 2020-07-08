package com.example.foodtruck.activities.operator;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;

        import com.example.foodtruck.R;

public class OwnerSpeiseneuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_speiseneu);
    }

    public void speiseAnlegen(View v) {

        //hier Logik zum anlegen

        Intent in = new Intent(this, OwnerSpeisekarteActivity.class);
        startActivity(in);
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