package com.example.foodtruck.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.model.Dish;

import java.util.ArrayList;

public class RecyclerViewSelectedOrderItemsAdapter extends RecyclerView.Adapter<RecyclerViewSelectedOrderItemsAdapter.MyViewHolder> implements View.OnClickListener {

    private ArrayList<Dish> selectedItems;

    @Override
    public void onClick(View v) {

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout cl;
        public TextView name;
        public ImageView deleteButton;

        public MyViewHolder(LinearLayout v) {
            super(v);
            cl = v;
            name = cl.findViewById(R.id.new_order_selected_item_name);
            deleteButton = cl.findViewById(R.id.delete_image);
        }

        public TextView getName() {
            return name;
        }

        public ImageView getDeleteButton() {
            return deleteButton;
        }

    }

    public RecyclerViewSelectedOrderItemsAdapter(ArrayList<Dish> selectedItems) {
        super();
        this.selectedItems = selectedItems;
    }

    @Override
    public RecyclerViewSelectedOrderItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_customer_order_selection_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.getName().setText(selectedItems.get(position).getName());
        holder.getDeleteButton().setOnClickListener(view -> {
            selectedItems.remove(holder.getAdapterPosition());
            this.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return selectedItems.size() - 1;
    }
}