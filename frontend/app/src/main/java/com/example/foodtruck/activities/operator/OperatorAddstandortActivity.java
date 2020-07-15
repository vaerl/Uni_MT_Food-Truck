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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.foodtruck.FormattingHelper.getHours;
import static com.example.foodtruck.FormattingHelper.getMinutes;

public class OperatorAddstandortActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_addstandort);
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
        LocalDateTime ankunft = LocalDate.now().atTime(getHours(editTextAnkunft.getText().toString()), getMinutes(editTextAnkunft.getText().toString()));
        EditText editTextAufenthaltsdauer = (EditText) findViewById(R.id.aufenthaltsdauer_editTextTime2);
        int aufenthaltsdauer = Integer.parseInt(editTextAufenthaltsdauer.getText().toString());
        Duration duration = Duration.ofMinutes(aufenthaltsdauer);

        double standortX = Double.parseDouble(X);
        double standortY = Double.parseDouble(Y);

        Location location = new Location(standortName, standortX, standortY, ankunft, duration);

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "post location: try to post location");

        GsonRequest<Location[], Location[]> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/route", new Location[]{location}, Location[].class, DataService.getStandardHeader(), response -> {
            setResult(Activity.RESULT_OK, new Intent());
            finish();
        }, error -> {
            Log.e(TAG, "Could not post location!", error);
        });
        queue.add(request);
    }

    public void backButton(View v) {
        super.onBackPressed();
    }

    public void ownerHome(View v) {
        startActivity(new Intent(this, OperatorMenuActivity.class));
    }


}
