package com.example.projekt_zaliczeniowy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.adapters.CartRecyclerViewAdapter;
import com.example.projekt_zaliczeniowy.constants.SharedPreferencesConstants;
import com.example.projekt_zaliczeniowy.database.DatabaseHelper;
import com.example.projekt_zaliczeniowy.models.ProductModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends Fragment {

    RecyclerView cartRecyclerView;
    CartRecyclerViewAdapter cartRecyclerViewAdapter;
    SharedPreferences sharedPreferences;

    MaterialTextView totalPrice;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingCartFragment newInstance(String param1, String param2) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.shopping_cart_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(SharedPreferencesConstants.SHARED_PREFS, Context.MODE_PRIVATE);
        totalPrice = view.findViewById(R.id.totalPriceCart);

        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        cartRecyclerViewAdapter = new CartRecyclerViewAdapter(generateProductsListFromSharedPreferance(), totalPrice, getActivity());
        cartRecyclerView.setAdapter(cartRecyclerViewAdapter);

    }

    private List<ProductModel> generateProductsListFromSharedPreferance() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        Set<String> productsIdSet = new TreeSet<>(sharedPreferences.getStringSet(SharedPreferencesConstants.CART_KEY, new TreeSet<>()));
        List<ProductModel> products = new ArrayList<>();

        for (String id : productsIdSet) {
            products.add(
                    databaseHelper.getProductByID(Integer.valueOf(id))
            );
        }

        return products;
    }
}