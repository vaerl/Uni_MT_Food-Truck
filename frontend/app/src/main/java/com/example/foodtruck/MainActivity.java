package com.example.foodtruck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.model.user.Customer;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_chooser_layout);
        VolleyLog.DEBUG = true;

        findViewById(R.id.type_customer_button).setOnClickListener(view -> {
            Log.d(TAG, "onCreate: user is customer, saving.");
            DataService.getInstance(this).setEntry(DataService.USER_TYPE, DataService.UserType.CUSTOMER.toString());
            setContentView(R.layout.activity_login);
        });

        findViewById(R.id.type_operator_button).setOnClickListener(view -> {
            Log.d(TAG, "onCreate: user is operator, saving.");
            DataService.getInstance(this).setEntry(DataService.USER_TYPE, DataService.UserType.OPERATOR.toString());
            setContentView(R.layout.activity_login);
        });
    }

    public void login(View v) {
        Log.d(TAG, "login: trying to find user.");

        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json");
        // check user_type make request
        RequestQueue queue = Volley.newRequestQueue(this);
        String name = ((EditText) findViewById(R.id.name_editText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwort_editText)).getText().toString();
        GsonRequest<Customer> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/user/login", new Customer(name, password), Customer.class, params, response -> {
            if (response.getName().equalsIgnoreCase(name)) {
                if (DataService.getInstance(this).getUserType() == DataService.UserType.CUSTOMER) {
                    startActivity(new Intent(this, CustomerMenuActivity.class));
                } else {
                    startActivity(new Intent(this, OwnerMenuActivity.class));
                }
            } else {
                showLoginError();
            }
        }, error -> {
            Log.e(TAG, "login: user is not registered!", error);
            showLoginError();
        });
        queue.add(request);
    }

    public void showLoginError() {
        ((EditText) findViewById(R.id.name_editText)).setError("");
        ((EditText) findViewById(R.id.passwort_editText)).setError("");
    }

//    public void registrieren(View v) {
//        setContentView(R.layout.activity_registrieren);
//    }
//
//    public void reg_abbrechen(View v) {
//        setContentView(R.layout.activity_login);
//    }
//
//    public void ownerHome(View v) {
//        setContentView(R.layout.activity_owner_menu);
//    }
//
//    public void speisekarte(View v) {
//        setContentView(R.layout.activity_speisekarte);
//    }
}
