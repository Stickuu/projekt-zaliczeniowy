package com.example.projekt_zaliczeniowy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.models.ProductModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    List<ProductModel> products;

    public CartRecyclerViewAdapter(List<ProductModel> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_cart_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(products.get(position).getName());
        holder.productPrice.setText(String.valueOf(products.get(position).getPrice()) + " $");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView productName;
        MaterialTextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameCart);
            productPrice = itemView.findViewById(R.id.productPriceCart);
        }
    }
}
