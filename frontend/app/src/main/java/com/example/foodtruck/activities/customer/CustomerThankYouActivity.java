package com.example.foodtruck.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.foodtruck.R;

public class CustomerThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_thanks);
    }


    public void showOrders(View v) {
        Intent in = new Intent(this, CustomerShowOrdersActivity.class);
        startActivity(in);
    }

    public void getToHome(View v) {
        Intent in = new Intent(this, CustomerMenuActivity.class);
        startActivity(in);
    }

}
