package com.example.foodtruck.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.DishWrapper;
import com.example.foodtruck.model.order.PreOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdvancedOwnerLebensmittelbestellungAdapter extends ArrayAdapter<Dish> {

    PreOrder[] preOrders;
    public List<DishWrapper> dishWrappers = new ArrayList<>();

    public AdvancedOwnerLebensmittelbestellungAdapter(Context context, int resource, Dish[] dishes, PreOrder[] preOrders) {
        super(context, resource, dishes);
        this.preOrders = preOrders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            element = inflater.inflate(R.layout.activity_owner_lebensmittelbestellung_item, null);
        }
        TextView gericht = element.findViewById(R.id.Gericht_textView2);
        int amount = 0;
        for (PreOrder preOrder : preOrders) {
            for (DishWrapper dishWrapper : preOrder.getItems()) {
                Dish dish = getItem(position);
                if (dishWrapper.getDish().getId().equals(dish.getId())) {
                    amount = dishWrapper.getAmount();
                    dishWrappers.add(new DishWrapper(dish, amount));
                }
            }
        }
        TextView bestellungen = element.findViewById(R.id.bestellungen_textView);
        gericht.setText(Objects.requireNonNull(getItem(position)).getName());
        bestellungen.setText(Integer.toString(amount));

        ((EditText) element.findViewById(R.id.gericht_zuschlag_edittext)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    boolean found = false;
                    for (DishWrapper dishWrapper : dishWrappers) {
                        if (dishWrapper.getDish().getName().equals(Objects.requireNonNull(getItem(position)).getName())) {
                            found = true;
                            dishWrapper.setAmount(dishWrapper.getAmount() + Integer.parseInt(s.toString()));
                        }
                    }
                    if (!found) {
                        dishWrappers.add(new DishWrapper(getItem(position), Integer.parseInt(s.toString())));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return element;
    }
}
