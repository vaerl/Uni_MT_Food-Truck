package com.example.foodtruck.activities.operator;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.EditText;

        import com.example.foodtruck.DataService;
        import com.example.foodtruck.R;
        import com.example.foodtruck.model.Dish;
        import com.example.foodtruck.model.Location;
        import com.example.foodtruck.model.user.Operator;

        import java.time.Duration;
        import java.time.LocalDateTime;

public class OwnerAddstandortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_addstandort);
    }

    public void standortAnlegen(View v) {

        String standortName = ((EditText) findViewById(R.id.standortname_editText9)).getText().toString();
        if(standortName.equals("")){
            ((EditText) findViewById(R.id.standortname_editText9)).setError("Name fehlt");
            return;
        }
        String X = ((EditText) findViewById(R.id.breitengradx_editTextNumberDecimal2)).getText().toString();
        if(X.equals("")){
            ((EditText) findViewById(R.id.breitengradx_editTextNumberDecimal2)).setError("Name fehlt");
            return;
        }
        String Y = ((EditText) findViewById(R.id.laengengrady_editTextNumberDecimal2)).getText().toString();
        if(Y.equals("")){
            ((EditText) findViewById(R.id.laengengrady_editTextNumberDecimal2)).setError("Name fehlt");
            return;
        }

        //Todo: AnkunftZeit aus "ankunft_editTextTime2"

        //Todo: AbreiseZeit/Duration "abreise_editTextTime2"

        double standortX = Double.parseDouble(((EditText) findViewById(R.id.breitengradx_editTextNumberDecimal2)).getText().toString());
        double standortY = Double.parseDouble(((EditText) findViewById(R.id.laengengrady_editTextNumberDecimal2)).getText().toString());

        // Location location = new Location(standortName, 1, standortX, standortY, (LocalDateTime), (Duration)); //Todo
        //Todo: Weitere Logik




        //Intent in = new Intent(this, OwnerRoutebearbeitenActivity.class);
        //startActivity(in);
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
