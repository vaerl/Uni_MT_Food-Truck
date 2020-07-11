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
        import com.example.foodtruck.adapter.AdvancedOwnerBestellungenAdapter;
        import com.example.foodtruck.adapter.AdvancedOwnerSpeisekarteAdapter;
        import com.example.foodtruck.model.Dish;
        import com.example.foodtruck.model.order.Order;

        import java.util.HashMap;
        import java.util.Map;

public class OwnerBestellungenActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "order";

    Order[] orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_bestellungen);

        ListView lv = findViewById(R.id.bestellungen_ListView);
        RequestQueue queue = Volley.newRequestQueue(this);
        // Bestellungen laden

        Log.d(TAG, "show menu: try to get Bestellungen");

        GsonRequest<Order[], Order[]> requestOrders = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/orders", Order[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                orders = response;
                AdvancedOwnerBestellungenAdapter advancedToDoAdapter = new AdvancedOwnerBestellungenAdapter(this, 0, orders);
                lv.setAdapter(advancedToDoAdapter);

                lv.setOnItemClickListener((parent, view, lv_position, id) -> {
                    Intent intent = new Intent(OwnerBestellungenActivity.this, OwnerBestellungActivity.class);
                    intent.putExtra(EXTRA_PARAMETER, orders[lv_position]);
                    startActivity(intent);
                });
            }
        }, error -> {
            Log.e(TAG, "Could not get Bestellungen!", error);
        });
        queue.add(requestOrders);

    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }


}