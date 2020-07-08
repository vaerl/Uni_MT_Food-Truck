package com.example.foodtruck.activities;

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
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.activities.customer.CustomerLocationActivity;
import com.example.foodtruck.activities.operator.OwnerMenuActivity;
import com.example.foodtruck.model.user.Customer;
import com.example.foodtruck.model.user.Operator;

public class RegisterActivity extends Activity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_registrieren);
        findViewById(R.id.registrieren_button).setOnClickListener(view -> {
            // get credentials
            String name = ((EditText) findViewById(R.id.name_editText)).getText().toString();
            String password = ((EditText) findViewById(R.id.passwort_editText)).getText().toString();
            String passwordRepeat = ((EditText) findViewById(R.id.passwort2_editText)).getText().toString();
            // check passwords
            if(!password.equals(passwordRepeat)){
                ((EditText) findViewById(R.id.passwort2_editText)).setError("Die Passwörter stimmen nicht überein!");
                return;
            }
            RequestQueue queue = Volley.newRequestQueue(this);
            if(DataService.getInstance(this).getUserType() == DataService.UserType.CUSTOMER){
                GsonRequest<Customer> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/customer", new Customer(name, password), Customer.class, DataService.getStandardHeader(), response -> {
                    if (response.getName().equalsIgnoreCase(name)) {
                        startActivity(new Intent(this, CustomerLocationActivity.class));
                    } else {
                        showRegistrationError();
                    }
                }, error -> {
                    Log.e(TAG, "login: user already present: ", error);
                    showRegistrationError();
                });
                queue.add(request);
            } else {
                GsonRequest<Operator> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator", new Operator(name, password), Operator.class, DataService.getStandardHeader(), response -> {
                    if (response.getName().equalsIgnoreCase(name)) {
                        startActivity(new Intent(this, OwnerMenuActivity.class));
                    } else {
                        showRegistrationError();
                    }
                }, error -> {
                    Log.e(TAG, "login: user already present: ", error);
                    showRegistrationError();
                });
                queue.add(request);
            }
        });
    }

    public void showRegistrationError() {
        ((EditText) findViewById(R.id.name_editText)).setError("Der Benutzername wird bereits verwendet.");
    }

    public void reg_abbrechen(View v) {
        setContentView(R.layout.activity_general_login);
        //clear fields

    }
}
