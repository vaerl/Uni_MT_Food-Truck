package com.example.foodtruck.activities.operator;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.EditText;

        import com.example.foodtruck.R;
        import com.example.foodtruck.model.Location;

        import java.util.Objects;

public class OwnerStandortbearbeitenActivity extends AppCompatActivity {

    String EXTRA_PARAMETER = "standort";

    EditText standortName;
    EditText standortX;
    EditText standortY;
    EditText standortAnkunft;
    EditText standortAbreise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_ortbearbeiten);

        standortName = findViewById(R.id.standortname_editText);
        standortX = findViewById(R.id.breitengradx_editTextNumberDecimal);
        standortY = findViewById(R.id.laengengrady_editTextNumberDecimal);
        standortAnkunft = findViewById(R.id.ankunft_editTextTime);
        standortAbreise = findViewById(R.id.abreise_editTextTime);

        if(getIntent().hasExtra(EXTRA_PARAMETER)){
            Intent intent = getIntent();
            Location standort = (Location) intent.getSerializableExtra(EXTRA_PARAMETER);
            standortName.setText(standort.getName());
            standortX.setText(Double.toString(standort.getX()));
            standortY.setText(Double.toString(standort.getY()));
            standortAnkunft.setText(standort.getArrival().getHour() + ":" + standort.getArrival().getMinute());
            standortAbreise.setText(standort.getDeparture().getHour() + ":" + standort.getDeparture().getMinute());
        }
    }



    public void speicherStandort(View v){

        String name = standortName.getText().toString();
        String X = standortX.getText().toString();
        String Y = standortY.getText().toString();
        String Ankunft = standortAnkunft.getText().toString();
        String Abreise = standortAbreise.getText().toString();

        //Todo: Logik zum Speichern


//        Intent in = new Intent(this, OwnerRoutebearbeitenActivity.class);
//        startActivity(in);
    }

    public void löscheStandort(View v){

        //Todo: Logik zum Löschen

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

}
