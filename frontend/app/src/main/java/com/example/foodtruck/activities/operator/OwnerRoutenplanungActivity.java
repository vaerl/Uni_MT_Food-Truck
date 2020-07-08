package com.example.foodtruck.activities.operator;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;

        import com.example.foodtruck.R;

public class OwnerRoutenplanungActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_routenplanung);
    }

    public void ownerHome(View v) {
        Intent in = new Intent(this, OwnerMenuActivity.class);
        startActivity(in);
    }

    public void openNeueroute(View v) {
        Intent in = new Intent(this, OwnerNeuerouteActivity.class);
        startActivity(in);
    }

    public void openRoutebearbeiten(View v) {
        Intent in = new Intent(this, OwnerRoutebearbeitenActivity.class);
        startActivity(in);
    }

}