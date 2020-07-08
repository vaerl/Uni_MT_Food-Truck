package com.example.foodtruck.activities.operator;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;

        import com.example.foodtruck.R;

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
