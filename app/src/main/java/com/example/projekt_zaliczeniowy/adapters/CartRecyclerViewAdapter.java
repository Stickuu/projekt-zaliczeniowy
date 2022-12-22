package com.example.projekt_zaliczeniowy.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_zaliczeniowy.MainActivity;
import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.constants.SharedPreferencesConstants;
import com.example.projekt_zaliczeniowy.models.OrderModel;
import com.example.projekt_zaliczeniowy.models.ProductModel;
import com.google.android.material.textview.MaterialTextView;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    List<ProductModel> products;
    MaterialTextView totalPriceText;
    Context context;

    public CartRecyclerViewAdapter(List<ProductModel> products) {
        this.products = products;
    }

    public CartRecyclerViewAdapter(List<ProductModel> products, MaterialTextView price, Context context) {
        this.products = products;
        this.totalPriceText = price;
        this.context = context;

        totalPriceText.setText(String.valueOf(calcualteTotalPrice()) + " $");
    }

    // return ordered products and clear cart
    public OrderModel returnOrder() {
        String productsListString = returnProductsIdAsString();
        int userID = ((MainActivity) context).getCurrentUserIdFromSession();
        int date = (int) Instant.now().getEpochSecond();
        int totalPrice = calcualteTotalPrice();

        // delete products from shared preferences
        for(int i = 0; i < products.size(); i++) {
            deleteProductFromSharedPreferencesCart(products.get(i).getId());
        }

        // clear products list
        products.clear();

        totalPriceText.setText(String.valueOf(calcualteTotalPrice()) + " $");

        notifyDataSetChanged();

        return new OrderModel(
                productsListString,
                userID,
                date,
                totalPrice
        );
    }

    public String generateShareCartMessage() {
        String result = "";

        for (ProductModel product : products) {
            result += product.getName() + "\n";
        }

        result += "\nTotal price: " + String.valueOf(calcualteTotalPrice()) + " $";

        return result;
    }

    private String returnProductsIdAsString() {
        String result = "";

        for (ProductModel product : products) {
            result += product.getId() + ",";
        }

        return result;
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
            totalPriceText.setText(String.valueOf(calcualteTotalPrice()) + " $");
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
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesConstants.SHARED_PREFS, Context.MODE_PRIVATE);

        Set<String> productsIdSet = new TreeSet<>(sharedPreferences.getStringSet(SharedPreferencesConstants.CART_KEY, new TreeSet<>()));
        productsIdSet.remove(String.valueOf(id));

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
