package com.example.foodtruck.activities.operator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.model.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.foodtruck.FormattingHelper.getHours;
import static com.example.foodtruck.FormattingHelper.getMinutes;
import static com.example.foodtruck.FormattingHelper.localDateTimeFormatter;

public class OperatorStandortbearbeitenActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private EditText standortName;
    private EditText standortX;
    private EditText standortY;
    private EditText standortAnkunft;
    private EditText standortAufenthaltsdauer;

    private Long id;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_ortbearbeiten);

        standortName = findViewById(R.id.standortname_editText);
        standortX = findViewById(R.id.breitengradx_editTextNumberDecimal);
        standortY = findViewById(R.id.laengengrady_editTextNumberDecimal);
        standortAnkunft = findViewById(R.id.ankunft_editTextTime);
        standortAufenthaltsdauer = findViewById(R.id.aufenthaltsdauer_editTextTime);

        if (getIntent().hasExtra(OperatorRoutebearbeitenActivity.INTENT_EDIT_LOCATION)) {
            Intent intent = getIntent();
            location = (Location) intent.getSerializableExtra(OperatorRoutebearbeitenActivity.INTENT_EDIT_LOCATION);
            standortName.setText(location.getName());
            standortX.setText(Double.toString(location.getX()));
            standortY.setText(Double.toString(location.getY()));
            standortAnkunft.setText(localDateTimeFormatter(location.getArrival()));
            standortAufenthaltsdauer.setText(localDateTimeFormatter(location.getDeparture()));
        }
    }


    public void speicherStandort(View v) {
        if (standortName.getText().toString().equals("")) {
            standortName.setError("Name fehlt!");
            return;
        }
        if (standortX.getText().toString().equals("")) {
            standortX.setError("X-Koordinate fehlt!");
            return;
        }
        if (standortY.getText().toString().equals("")) {
            standortY.setError("Y-Koordinate fehlt!");
            return;
        }
        if (standortAnkunft.getText().toString().equals("")) {
            standortAnkunft.setError("Ankunftzeit fehlt!");
            return;
        }
        if (standortAufenthaltsdauer.getText().toString().equals("")) {
            standortAufenthaltsdauer.setError("Abfahrtzeit fehlt!");
            return;
        }
        LocalDateTime arrival = LocalDate.now().atTime(getHours(standortAnkunft.getText().toString()), getMinutes(standortAnkunft.getText().toString()));
        LocalDateTime departure = LocalDate.now().atTime(getHours(standortAufenthaltsdauer.getText().toString()), getMinutes(standortAufenthaltsdauer.getText().toString()));
        if (departure.isBefore(arrival)) {
            standortAufenthaltsdauer.setError("Abfahrt muss nach Ankunft erfolgen!");
            return;
        }
        location.setName(standortName.getText().toString());
        location.setX(Double.parseDouble(standortX.getText().toString()));
        location.setY(Double.parseDouble(standortY.getText().toString()));
        location.setArrival(arrival);
        location.setDeparture(departure);

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "post location: try to update location");

        // TODO: Endpunkt muss im Backend noch erstellt werden
        GsonRequest<Location, Location> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/route/update", location, Location.class, DataService.getStandardHeader(), response -> {
            if (response == null) {
                Log.e(TAG, "speicherStandort: something went wrong!");
            }
            done();
        }, error -> {
            Log.e(TAG, "Could not update location!", error);
        });
        queue.add(request);

    }

    public void deleteLocation(View v) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "delete location: try to delete location");
        GsonRequest<Location[], Boolean> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/route/delete", new Location[]{location}, Boolean.class, DataService.getStandardHeader(), response -> {
            done();
        }, error -> {
            Log.e(TAG, "Could not delete location!", error);
        });
        queue.add(request);
    }

    public void done() {
        setResult(Activity.RESULT_OK, new Intent());
        finish();
    }

    public void backButton(View v) {
        startActivity(new Intent(this, OperatorRoutebearbeitenActivity.class));
    }

    public void ownerHome(View v) {
        startActivity(new Intent(this, OperatorMenuActivity.class));
    }

}
