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
import com.example.foodtruck.activities.customer.CustomerThankYouActivity;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.Location;
import com.example.foodtruck.model.order.Reservation;
import com.example.foodtruck.model.user.Operator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.foodtruck.FormattingHelper.getHours;
import static com.example.foodtruck.FormattingHelper.getMinutes;

public class OwnerAddstandortActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_addstandort);
    }

    public void standortAnlegen(View v) {

        String standortName = ((EditText) findViewById(R.id.standortname_editText9)).getText().toString();
        if (standortName.equals("")) {
            ((EditText) findViewById(R.id.standortname_editText9)).setError("Name fehlt");
            return;
        }
        String X = ((EditText) findViewById(R.id.breitengradx_editTextNumberDecimal2)).getText().toString();
        if (X.equals("")) {
            ((EditText) findViewById(R.id.breitengradx_editTextNumberDecimal2)).setError("Name fehlt");
            return;
        }
        String Y = ((EditText) findViewById(R.id.laengengrady_editTextNumberDecimal2)).getText().toString();
        if (Y.equals("")) {
            ((EditText) findViewById(R.id.laengengrady_editTextNumberDecimal2)).setError("Name fehlt");
            return;
        }

        EditText editTextAnkunft = (EditText) findViewById(R.id.ankunft_editTextTime2);
        LocalDateTime ankunft = LocalDate.now().atTime(Integer.getInteger(getHours(editTextAnkunft.getText().toString())), Integer.getInteger(getMinutes(editTextAnkunft.getText().toString())));
        EditText editTextAufenthaltsdauer = (EditText) findViewById(R.id.aufenthaltsdauer_editTextTime2);
        long aufenthaltsdauer = Long.getLong(editTextAufenthaltsdauer.getText().toString());
        Duration duration = Duration.ofMinutes(aufenthaltsdauer);

        double standortX = Double.parseDouble(X);
        double standortY = Double.parseDouble(Y);

        Location location = new Location(standortName, standortX, standortY, ankunft, duration);

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "post location: try to post location");

        GsonRequest<Location[], Boolean> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/route", new Location[]{location}, Boolean.class, DataService.getStandardHeader(), response -> {
            Intent in = new Intent(this, OwnerRoutebearbeitenActivity.class);
            startActivity(in);
        }, error -> {
            Log.e(TAG, "Could not post location!", error);
        });
        queue.add(request);

    }


    public void backButton(View v) {
        Intent in = new Intent(this, OwnerRoutebearbeitenActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }


}
