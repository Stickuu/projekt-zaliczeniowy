package com.example.projekt_zaliczeniowy.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.constants.SharedPreferencesConstants;
import com.example.projekt_zaliczeniowy.models.ProductModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    List<ProductModel> products;
    MaterialTextView totalPrice;
    Context context;

    public CartRecyclerViewAdapter(List<ProductModel> products) {
        this.products = products;
    }

    public CartRecyclerViewAdapter(List<ProductModel> products, MaterialTextView price, Context context) {
        this.products = products;
        this.totalPrice = price;
        this.context = context;

        totalPrice.setText(String.valueOf(calcualteTotalPrice()) + " $");
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
        // set values data from product to textview
        holder.productName.setText(products.get(position).getName());
        holder.productPrice.setText(String.valueOf(products.get(position).getPrice()) + " $");

        holder.deleteButton.setOnClickListener(v -> {

            // remove product from shared preferences cart
            deleteProductFromSharedPreferencesCart(products.get(position).getId());

            // remove product from products list
            products.remove(position);

            // update recycler view
            notifyDataSetChanged();
            totalPrice.setText(String.valueOf(calcualteTotalPrice()) + " $");
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private int calcualteTotalPrice() {
        int totalPrice = 0;

        for (ProductModel product : products) {
            totalPrice += product.getPrice();
        }

        return totalPrice;
    }

    private void deleteProductFromSharedPreferencesCart(int id) {
        Log.d("DELETEBUTTON", "id: " + String.valueOf(id));
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesConstants.SHARED_PREFS, Context.MODE_PRIVATE);

        Set<String> productsIdSet = new TreeSet<>(sharedPreferences.getStringSet(SharedPreferencesConstants.CART_KEY, new TreeSet<>()));
        Log.d("DELETEBUTTON", "productsSet: " + productsIdSet.toString());
        productsIdSet.remove(String.valueOf(id));
        Log.d("DELETEBUTTON", "productSet2: " + productsIdSet.toString());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putStringSet(SharedPreferencesConstants.CART_KEY, productsIdSet);
        editor.apply();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView productName;
        MaterialTextView productPrice;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameCart);
            productPrice = itemView.findViewById(R.id.productPriceCart);
            deleteButton = itemView.findViewById(R.id.deleteButtonCart);
        }
    }
}
