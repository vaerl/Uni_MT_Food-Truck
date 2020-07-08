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

        import java.time.Duration;
        import java.time.LocalDateTime;

public class OwnerAddstandortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_addstandort);
    }

    public void standortAnlegen(View v) {

        //hier Logik zum anlegen

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
