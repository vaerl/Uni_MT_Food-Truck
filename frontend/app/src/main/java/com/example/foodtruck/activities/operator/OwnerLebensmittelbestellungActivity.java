package com.example.foodtruck.activities.operator;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.ListView;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.toolbox.Volley;
        import com.example.foodtruck.DataService;
        import com.example.foodtruck.GsonRequest;
        import com.example.foodtruck.R;
        import com.example.foodtruck.adapter.AdvancedOwnerLebensmittelbestellungAdapter;
        import com.example.foodtruck.adapter.AdvancedOwnerSpeisekarteAdapter;
        import com.example.foodtruck.model.Dish;

public class OwnerLebensmittelbestellungActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    Dish[] gerichte; //evtl orders

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_lebensmittelbestellung);

        ListView lv = findViewById(R.id.lebensmittelbestellung_ListView);

        RequestQueue queue = Volley.newRequestQueue(this);


        // bestellte Gerichte laden

        Log.d(TAG, "show menu: try to get bestellte Gerichte");
        //"Dish[]" & Gson muss wahrscheinlich geändert werden
        GsonRequest<Dish[], Dish[]> requestBestellteGerichte = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/menu/preorder", Dish[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                gerichte = response;
                AdvancedOwnerLebensmittelbestellungAdapter advancedToDoAdapter = new AdvancedOwnerLebensmittelbestellungAdapter(this, 0, gerichte);
                lv.setAdapter(advancedToDoAdapter);
            }
        }, error -> {
            Log.e(TAG, "Could not get bestellte Gerichte!", error);
        });
        queue.add(requestBestellteGerichte);
    }

    public void zeigeZutaten(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class); // <- Platzhalter
        startActivity(in);
    }

    public void bestelleLebensmittel(View v) {

        // Hier Logik für Zusammenrechnung aller Zutaten

        Intent in = new Intent(this, OwnerMenuActivity.class); // <- Platzhalter
        startActivity(in);
    }





    public void backButton(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

}
