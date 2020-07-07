package com.example.foodtruck;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;

public class OwnerBestellungenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_bestellungen);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

}