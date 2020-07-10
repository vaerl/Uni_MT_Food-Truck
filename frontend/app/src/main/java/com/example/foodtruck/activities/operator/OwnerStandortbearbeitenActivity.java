package com.example.foodtruck.activities.operator;

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
        import static com.example.foodtruck.FormattingHelper.localDateTimeFormatter;

public class OwnerStandortbearbeitenActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "standort";

    EditText standortName;
    EditText standortX;
    EditText standortY;
    EditText standortAnkunft;
    EditText standortAufenthaltsdauer;

    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_ortbearbeiten);

        standortName = findViewById(R.id.standortname_editText);
        standortX = findViewById(R.id.breitengradx_editTextNumberDecimal);
        standortY = findViewById(R.id.laengengrady_editTextNumberDecimal);
        standortAnkunft = findViewById(R.id.ankunft_editTextTime);
        standortAufenthaltsdauer = findViewById(R.id.aufenthaltsdauer_editTextTime);

        if(getIntent().hasExtra(EXTRA_PARAMETER)){
            Intent intent = getIntent();
            Location standort = (Location) intent.getSerializableExtra(EXTRA_PARAMETER);
            standortName.setText(standort.getName());
            standortX.setText(Double.toString(standort.getX()));
            standortY.setText(Double.toString(standort.getY()));
            standortAnkunft.setText(localDateTimeFormatter(standort.getArrival()));
            standortAufenthaltsdauer.setText(String.valueOf(standort.getDuration().getSeconds() / 60));
            id = standort.getId();
        }
    }



    public void speicherStandort(View v){
        Location location = createLocation();

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "post location: try to update location");

        // TODO: Endpunkt muss im Backend noch erstellt werden
        GsonRequest<Location, Boolean> request = new GsonRequest<>(Request.Method.POST, DataService.BACKEND_URL + "/location/" + id , location, Boolean.class, DataService.getStandardHeader(), response -> {
            Intent in = new Intent(this, OwnerRoutebearbeitenActivity.class);
            startActivity(in);
        }, error -> {
            Log.e(TAG, "Could not update location!", error);
        });
        queue.add(request);

    }

    public void löscheStandort(View v){
        Location location = createLocation();

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "delete location: try to delete location");

        GsonRequest<Location[], Boolean> request = new GsonRequest<>(Request.Method.DELETE, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/route", new Location[]{location}, Boolean.class, DataService.getStandardHeader(), response -> {
            Intent in = new Intent(this, OwnerRoutebearbeitenActivity.class);
            startActivity(in);
        }, error -> {
            Log.e(TAG, "Could not delete location!", error);
        });
        queue.add(request);

        Intent in = new Intent(this, OwnerRoutebearbeitenActivity.class);
        startActivity(in);
    }

    public void backButton(View v) {
        Intent in = new Intent(this, OwnerRoutebearbeitenActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

    public Location createLocation(){
        String name = standortName.getText().toString();
        double X = Double.parseDouble(standortX.getText().toString());
        double Y = Double.parseDouble(standortY.getText().toString());

        LocalDateTime ankunft = LocalDate.now().atTime(Integer.getInteger(getHours(standortAnkunft.getText().toString())), Integer.getInteger(getMinutes(standortAnkunft.getText().toString())));
        long aufenthaltsdauer = Long.getLong(standortAufenthaltsdauer.getText().toString());
        Duration duration = Duration.ofMinutes(aufenthaltsdauer);

        return new Location(name, X, Y, ankunft, duration);
    }

}
