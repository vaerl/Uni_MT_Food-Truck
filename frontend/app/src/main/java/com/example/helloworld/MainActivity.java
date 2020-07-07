package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View v) {
        setContentView(R.layout.activity_owner_menu);
    }

    public void registrieren(View v) {
        setContentView(R.layout.activity_registrieren);
    }

    public void reg_abbrechen(View v) {
        setContentView(R.layout.activity_login);
    }

    public void ownerHome(View v) {
        setContentView(R.layout.activity_owner_menu);
    }

    public void speisekarte(View v) {
        setContentView(R.layout.activity_owner_speisekarte);
    }


}
