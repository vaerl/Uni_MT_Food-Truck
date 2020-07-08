
package com.example.foodtruck.activities.operator;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.example.foodtruck.R;
        import com.example.foodtruck.model.Dish;


        import java.util.ArrayList;
        import java.util.Map;

public class OwnerSpeisebearbeitenActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "gericht";

    String EXTRA_PARAMETER2 = "gericht2";

    Dish[] dish;

    EditText gerichtName;
    EditText gerichtPreis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_speisebearbeiten);

        gerichtName = findViewById(R.id.bearb_name_input);
        gerichtPreis = findViewById(R.id.bearb_preis_input);

        if(getIntent().hasExtra(EXTRA_PARAMETER)){
            Intent intent = getIntent();
            Dish gericht = (Dish) intent.getSerializableExtra(EXTRA_PARAMETER);
            gerichtName.setText(gericht.getName());
            gerichtPreis.setText(Double.toString(gericht.getPrice()));
        }
    }

    public void bearbAbschliessen(View v) {

        //Hier Logik für UPDATE auf Datenbank

        Intent in = new Intent(this, OwnerSpeisekarteActivity.class);
        startActivity(in);
    }


    public void openAddzutatBearb(View v) {

        Intent intent = new Intent(OwnerSpeisebearbeitenActivity.this, OwnerAddzutatBearbActivity.class);
        Dish dish = (Dish) intent.getSerializableExtra(EXTRA_PARAMETER);
        intent.putExtra(EXTRA_PARAMETER2, dish);
        startActivity(intent);
    }

    public void backButton(View v) {
        Intent in = new Intent(this, OwnerSpeisekarteActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

}