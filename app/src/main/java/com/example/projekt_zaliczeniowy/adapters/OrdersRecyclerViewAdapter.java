package com.example.projekt_zaliczeniowy.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.models.OrderModel;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<OrdersRecyclerViewAdapter.ViewHolder> {

    List<OrderModel> orders;

    public OrdersRecyclerViewAdapter(List<OrderModel> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_order_item, parent, false);
        OrdersRecyclerViewAdapter.ViewHolder viewHolder = new OrdersRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.orderNumber.setText(String.valueOf(orders.get(position).getOrderUniqueNumber()));
        holder.orderDate.setText(returnFormattedDate(orders.get(position).getConvertedUnixDate()));
        holder.orderTotalPrice.setText(String.valueOf(orders.get(position).getTotalPrice()) + " $");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private String returnFormattedDate(Instant instant) {
        Date date = Date.from(instant);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

        return formatter.format(date);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView orderNumber;
        MaterialTextView orderDate;
        MaterialTextView orderTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.orderNumber);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderTotalPrice = itemView.findViewById(R.id.orderTotalPrice);
        }
    }
}
