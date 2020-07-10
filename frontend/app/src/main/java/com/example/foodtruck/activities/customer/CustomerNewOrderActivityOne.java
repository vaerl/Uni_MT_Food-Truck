package com.example.foodtruck.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.foodtruck.DataService;
import com.example.foodtruck.GsonRequest;
import com.example.foodtruck.R;
import com.example.foodtruck.adapter.AdvancedCustomerNewOrderMenuAdapter;
import com.example.foodtruck.adapter.RecyclerViewSelectedOrderItemsAdapter;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.DishWrapper;
import com.example.foodtruck.model.order.PreOrder;
import com.example.foodtruck.model.order.Reservation;

import java.util.ArrayList;
import java.util.List;

public class CustomerNewOrderActivityOne extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    String EXTRA_PARAMETER = "order";
    String EXTRA_PARAMETER2 = "type";

    Dish[] dishesReservation;
    Dish[] dishesPreorder;

    ListView lvReservation;
    ListView lvPreorder;

    ArrayList<Dish> selectedItems = new ArrayList<>();

    List<DishWrapper> selectedDishesReservation;
    List<DishWrapper> selectedDishesPreOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_menu);
        selectedDishesReservation = new ArrayList<>();
        selectedDishesPreOrder = new ArrayList<>();

        lvReservation = findViewById(R.id.new_order_list_reservation);
        lvPreorder = findViewById(R.id.new_order_list_preorder);

        RecyclerView selected_items = (RecyclerView) findViewById(R.id.selected_items);
        selected_items.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        selected_items.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        RecyclerViewSelectedOrderItemsAdapter  recyclerViewSelectedOrderItemsAdapter = new RecyclerViewSelectedOrderItemsAdapter(selectedItems);
        selected_items.setAdapter(recyclerViewSelectedOrderItemsAdapter);

        TabLayout tabs = findViewById(R.id.tabLayout2);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    setOrderMenuToReservation(tabs);
                } else {
                    setOrderMenuToPreorder(tabs);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        RequestQueue queue = Volley.newRequestQueue(this);

        // Reservation
        // ---------------------------------------------------------------
        Log.d(TAG, "show route: try to get reservation menu");
        GsonRequest<Dish[], Dish[]> requestReservation = new GsonRequest<>(Request.Method.GET, DataService.BACKEND_URL + "/operator/" + DataService.OPERATOR_ID + "/menu/reservation", Dish[].class, DataService.getStandardHeader(), response -> {
            if (response != null) {
                dishesReservation = response;
                AdvancedCustomerNewOrderMenuAdapter advancedCustomerNewOrderMenuAdapterReservation = new AdvancedCustomerNewOrderMenuAdapter(this, 0, dishesReservation, "reservation");
                lvReservation.setAdapter(advancedCustomerNewOrderMenuAdapterReservation);
                lvReservation.setOnItemClickListener((parent, view, position, id) -> {
                    selectedItems.add(dishesReservation[position]);
                    recyclerViewSelectedOrderItemsAdapter.notifyDataSetChanged();
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
                AdvancedCustomerNewOrderMenuAdapter advancedCustomerNewOrderMenuAdapterPreOrder = new AdvancedCustomerNewOrderMenuAdapter(this, 0, dishesPreorder, "preorder");
                lvPreorder.setAdapter(advancedCustomerNewOrderMenuAdapterPreOrder);
                lvPreorder.setOnItemClickListener((parent, view, position, id) -> {
                    selectedItems.add(dishesReservation[position]);
                    recyclerViewSelectedOrderItemsAdapter.notifyDataSetChanged();
                });
            }
        }, error -> {
            Log.e(TAG, "Could not get preorder menu!", error);
        });
        queue.add(requestPreorder);

    }

    public void setOrderMenuToPreorder(View v) {
        selectedItems.clear();
        lvReservation.setVisibility(View.GONE);
        lvPreorder.setVisibility(View.VISIBLE);
    }

    public void setOrderMenuToReservation(View v) {
        selectedItems.clear();
        lvPreorder.setVisibility(View.GONE);
        lvReservation.setVisibility(View.VISIBLE);
    }

    public void forwardNewOrderActivityOne(View v) {

        for (Dish dish: selectedItems) {
            if (lvReservation.getVisibility() == View.VISIBLE) {
                if (selectedDishesReservation.contains(dish)) {
                    selectedDishesReservation.add(new DishWrapper(dish, selectedDishesReservation.get(selectedDishesReservation.indexOf(dish)).getAmount() + 1));
                } else {
                    selectedDishesReservation.add(new DishWrapper(dish, 1));
                }
            } else {
                if (selectedDishesPreOrder.contains(dish)) {
                    selectedDishesPreOrder.add(new DishWrapper(dish, selectedDishesReservation.get(selectedDishesReservation.indexOf(dish)).getAmount() + 1));
                } else {
                    selectedDishesPreOrder.add(new DishWrapper(dish, 1));
                }
            }
        }

        Intent intent = new Intent(CustomerNewOrderActivityOne.this, CustomerNewOrderActivityTwo.class);
        if (lvReservation.getVisibility() == View.VISIBLE) {
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

    public void getToHome(View v){
        Intent in = new Intent(this, CustomerMenuActivity.class);
        startActivity(in);
    }

}
