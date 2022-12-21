package com.example.projekt_zaliczeniowy.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_zaliczeniowy.MainActivity;
import com.example.projekt_zaliczeniowy.fragments.ProductFragment;
import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.models.ProductModel;

import java.util.List;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {

    List<ProductModel> products;
    Context context;

    public ProductsRecyclerViewAdapter(List<ProductModel> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_product_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(products.get(position).getName());
        holder.productDescription.setText(products.get(position).getDescription());
        holder.productPrice.setText(String.valueOf(products.get(position).getPrice()) + " $");

        holder.itemView.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putInt("productID", products.get(position).getId());
            ProductFragment productFragment = new ProductFragment();
            productFragment.setArguments(bundle);

            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.flFragment, productFragment)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        TextView productDescription;
        TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
