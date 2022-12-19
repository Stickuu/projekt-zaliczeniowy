package com.example.projekt_zaliczeniowy.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.constants.SharedPreferencesConstants;
import com.example.projekt_zaliczeniowy.database.DatabaseHelper;
import com.example.projekt_zaliczeniowy.models.ProductModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    MaterialTextView productName;
    MaterialTextView productDescription;
    MaterialTextView productPrice;
    ImageButton backArrowButton;
    MaterialButton addProductToCartButton;
    SharedPreferences sharedPreferences;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_product_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productName = view.findViewById(R.id.productNameSingleProduct);
        productDescription = view.findViewById(R.id.productDescriptionSingleProduct);
        productPrice = view.findViewById(R.id.productPriceSingleProduct);
        backArrowButton = view.findViewById(R.id.backButton);
        addProductToCartButton = view.findViewById(R.id.addProductToCartButton);
        sharedPreferences = getActivity().getSharedPreferences(SharedPreferencesConstants.SHARED_PREFS, Context.MODE_PRIVATE);

        // get product id
        int productID = getArguments().getInt("productID");

        ProductModel productModel = getProduct(productID);

        // set informations about product to text view
        productName.setText(productModel.getName());
        productDescription.setText(productModel.getDescription());
        productPrice.setText(String.valueOf(productModel.getPrice()) + " $");

        // back arrow button listener
        backArrowButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.flFragment, new HomeFragment())
                    .commit();
        });

        // add product to cart
        addProductToCartButton.setOnClickListener(v -> {
            addProductToCart(productID);
            Toast.makeText(getContext(), "Dodano " + productModel.getName() + " do koszyka", Toast.LENGTH_SHORT).show();
        });
    }

    private ProductModel getProduct(int id) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        return databaseHelper.getProductByID(id);
    }

    private void addProductToCart(int id) {
        Set<String> productsIdSet = new HashSet<>(sharedPreferences.getStringSet(SharedPreferencesConstants.CART_KEY, new HashSet<String>()));
        Log.d("CART", "set: " + productsIdSet.toString());
        productsIdSet.add(String.valueOf(id));

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putStringSet(SharedPreferencesConstants.CART_KEY, productsIdSet);
        editor.apply();
        Log.d("CART", "set2: " + productsIdSet.toString());
    }
}