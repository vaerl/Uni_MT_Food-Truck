package com.example.foodtruck.activities.operator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.model.Location;

import java.time.Duration;

public class OperatorAktuellerstandortActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    RequestQueue queue;
    Location location;
    EditText annahmeEditText;
    EditText schlussEditText;
    TextView locationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_aktuellerstandort);
        annahmeEditText = findViewById(R.id.reservierungsannahme_number);
        schlussEditText = findViewById(R.id.reservierungsschluss_number);
        locationStatus = findViewById(R.id.location_status_textview);
        queue = Volley.newRequestQueue(this);

        Log.d(TAG, "post location: try to get current location");
        GsonRequest<Location, Location> request = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/location", Location.class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                this.location = response;
                if (this.location.getDuration() != null) {
                    // hide textboxes
                    annahmeEditText.setVisibility(View.INVISIBLE);
                    schlussEditText.setVisibility(View.INVISIBLE);
                    // show text: leaving in <minutes>
                    locationStatus.setText("Der Foodtruck verlässt " + location.getName() + " in " + location.getDuration().toMinutes() + " Minuten.");
                    locationStatus.setVisibility(View.VISIBLE);
                }
            }
        }, error -> Log.e(TAG, "Could not post annahme!", error));
        queue.add(request);
    }

    public void annahmeAusloesen(View v) {
        String annahmeString = annahmeEditText.getText().toString();
        if (annahmeString.equals("")) {
            annahmeEditText.setError("Zeit fehlt!");
            return;
        }
        Duration duration = Duration.ofMinutes(Long.parseLong(annahmeString));
        Log.d(TAG, "post location: try to post annahme");
        GsonRequest<Duration, Duration> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/location/" + location.getId() + "/arrive", duration, Duration.class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                location.setDuration(response);
            }
        }, error -> Log.e(TAG, "Could not post annahme!", error));
        queue.add(request);
    }

    public void schlussAusloesen(View v) {
        String schlussString = schlussEditText.getText().toString();
        if (schlussString.equals("")) {
            schlussEditText.setError("Zeit fehlt!");
            return;
        }
        Duration duration = Duration.ofMinutes(Long.parseLong(schlussString));
        Log.d(TAG, "post location: try to post schluss");
        GsonRequest<Duration, Duration> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/location/" + location.getId() + "/leave", duration, Duration.class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                location.setDuration(response);
            }
        }, error -> Log.e(TAG, "Could not post schluss!", error));
        queue.add(request);
    }

    public void backButton(View v) {
        Intent in = new Intent(this, OperatorMenuActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OperatorMenuActivity.class);
        startActivity(in);
    }

}
