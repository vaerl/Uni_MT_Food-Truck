package com.example.foodtruck.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.foodtruck.R;

public class CustomerLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_location);
    }

    public void continueWithoutSettingLocation(View v){
        Intent in = new Intent(this, CustomerMenuActivity.class);
        startActivity(in);
    }

}
