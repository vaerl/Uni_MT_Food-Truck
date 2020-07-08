package com.example.foodtruck.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.adapter.AdvancedCustomerNewOrderMenuAdapter;
import com.example.foodtruck.adapter.AdvancedCustomerShowMenuAdapter;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.order.Order;
import com.example.foodtruck.model.order.PreOrder;
import com.example.foodtruck.model.order.Reservation;
import com.example.foodtruck.model.user.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CustomerNewOrderActivityOne extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "order";
    String EXTRA_PARAMETER2 = "type";

    Dish[] dishesReservation;
    Dish[] dishesPreorder;

    ListView lvReservation;
    ListView lvPreorder;

    Map<Dish, Integer> selectedDishesReservation;
    Map<Dish, Integer> selectedDishesPreOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_menu);

        lvReservation = findViewById(R.id.new_order_list_reservation);
        lvPreorder = findViewById(R.id.new_order_list_preorder);

        RequestQueue queue = Volley.newRequestQueue(this);

        // Reservation
        // ---------------------------------------------------------------
        Log.d(TAG, "show route: try to get reservation manu");
        GsonRequest<Dish[], Dish[]> requestReservation = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/menu/reservation", Dish[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                dishesReservation = response;
                AdvancedCustomerNewOrderMenuAdapter advancedToDoAdapterReservation = new AdvancedCustomerNewOrderMenuAdapter(this, 0, dishesReservation, "reservation");
                lvReservation.setAdapter(advancedToDoAdapterReservation);
                lvReservation.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CheckBox checkBox = view.findViewById(R.id.dish_new_order_menu_checkbox_c);
                        EditText dishAmount = view.findViewById(R.id.dish_new_order_menu_amount_c);
                        if (checkBox.isChecked()) {
                            selectedDishesReservation.put(dishesReservation[position], Integer.getInteger(dishAmount.getText().toString()));
                        } else {
                            selectedDishesReservation.remove(dishesReservation[position]);
                        }
                    }
                });
            }
        }, error -> {
            Log.e(TAG, "Could not get reservation menu!", error);
        });
        queue.add(requestReservation);

        // Preorder
        // ---------------------------------------------------------------
        Log.d(TAG, "show menu: try to get preorder menu");
        GsonRequest<Dish[], Dish[]> requestPreorder = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/menu/preorder", Dish[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                dishesPreorder = response;
                AdvancedCustomerNewOrderMenuAdapter advancedToDoAdapterPreorder = new AdvancedCustomerNewOrderMenuAdapter(this, 0, dishesPreorder, "preorder");
                lvPreorder.setAdapter(advancedToDoAdapterPreorder);
                lvPreorder.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CheckBox checkBox = view.findViewById(R.id.dish_new_order_menu_checkbox_c);
                        EditText dishAmount = view.findViewById(R.id.dish_new_order_menu_amount_c);
                        if (checkBox.isChecked()) {
                            selectedDishesPreOrder.put(dishesPreorder[position], Integer.getInteger(dishAmount.getText().toString()));
                        } else {
                            selectedDishesPreOrder.remove(dishesPreorder[position]);
                        }
                    }
                });
            }
        }, error -> {
            Log.e(TAG, "Could not get preorder menu!", error);
        });
        queue.add(requestPreorder);

    }

    public void setOrderMenuToPreorder(View v) {
        lvReservation.setVisibility(View.GONE);
        lvPreorder.setVisibility(View.VISIBLE);
    }

    public void setOrderMenuToReservation(View v) {
        lvPreorder.setVisibility(View.GONE);
        lvReservation.setVisibility(View.VISIBLE);
    }

    public void forwardNewOrderActivityOne(View v) {
        Intent intent = new Intent(CustomerNewOrderActivityOne.this, CustomerNewOrderActivityTwo.class);
        if (lvReservation.getVisibility() == View.VISIBLE){
            Reservation reservation = new Reservation(selectedDishesReservation);
            intent.putExtra(EXTRA_PARAMETER, reservation);
            intent.putExtra(EXTRA_PARAMETER2, "reservation");
        } else {
            PreOrder preOrder = new PreOrder(selectedDishesPreOrder);
            intent.putExtra(EXTRA_PARAMETER, preOrder);
            intent.putExtra(EXTRA_PARAMETER2, "preorder");
        }
        startActivity(intent);
    }

}
