package com.example.foodtruck.activities.operator;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.example.foodtruck.R;
        import com.example.foodtruck.adapter.AdvancedCustomerOrderDetailsAdapter;
        import com.example.foodtruck.model.Dish;
        import com.example.foodtruck.model.order.Order;

        import java.util.ArrayList;
        import java.util.Map;

public class OwnerBestellungActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "order";

    TextView orderTime;
    TextView orderNumber;
    TextView orderLocation;
    TextView orderPerson;
    TextView orderStatus;
    ListView gerichteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_menu);

        orderTime = findViewById(R.id.setUhrzeit);
        orderNumber = findViewById(R.id.setId);
        orderLocation = findViewById(R.id.setOrt);
        orderPerson  = findViewById(R.id.setPerson);
        orderStatus = findViewById(R.id.setStatus);
        gerichteListView = findViewById(R.id.gerichte_bestellung_ListView);

        if(getIntent().hasExtra(EXTRA_PARAMETER)){
            Intent intent = getIntent();
            Order order = (Order) intent.getSerializableExtra(EXTRA_PARAMETER);
//            orderTime.setText(order.getTime().toString());    // Wird ordertime in Datenbank gespeichert?
            orderNumber.setText(order.getId().toString());
            orderLocation.setText(order.getLocation().toString());
            orderPerson.setText(order.getCustomer().toString());
            orderStatus.setText(order.getStatus().toString());
            // TODO: Noch anzupassen
            //AdvancedCustomerOrderDetailsAdapter advancedToDoAdapter = new AdvancedCustomerOrderDetailsAdapter(this, 0, new ArrayList<Map.Entry<Dish, Integer>>(order.getItems().entrySet()));
            //gerichteListView.setAdapter(advancedToDoAdapter);
        }

    }

    public void backButton(View v) {
        Intent in = new Intent(this, OwnerBestellungenActivity.class);
        startActivity(in);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

}