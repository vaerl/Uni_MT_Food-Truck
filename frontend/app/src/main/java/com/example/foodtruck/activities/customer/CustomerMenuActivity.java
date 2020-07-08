package com.example.foodtruck.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.foodtruck.R;

public class CustomerMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu);
    }

    public void openTruckInfos(View v){
        Intent in = new Intent(this, CustomerTruckInfoActivity.class);
        startActivity(in);
    }

    public void newOrder(View v){
        Intent in = new Intent(this, CustomerNewOrderActivityOne.class);
        startActivity(in);
    }

    public void showOrders(View v){
        Intent in = new Intent(this, CustomerShowOrdersActivity.class);
        startActivity(in);
    }

}
