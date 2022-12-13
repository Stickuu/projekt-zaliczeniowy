package com.example.projekt_zaliczeniowy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.database.DatabaseHelper;
import com.example.projekt_zaliczeniowy.models.UserModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends Fragment {

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

        // database
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
//        UserModel userModel = new UserModel(
//                "name",
//                "lastname",
//                "test3@google.com",
//                "12345",
//                "123456789"
//        );
//
//        boolean result = databaseHelper.addOne(userModel);

        List<UserModel> allUsers = databaseHelper.getAllUsers();
        Log.d("DATABASE", allUsers.toString());

//        if(result) {
//            Toast.makeText(getActivity(), "User created", Toast.LENGTH_SHORT).show();
//        }
//        else Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

        UserModel userModel = databaseHelper.getUserByEmailAndPassword(new UserModel(
                "name",
                "lastname",
                "test22@google.com",
                "1234",
                "123456789"
        ));

        if(userModel != null) Log.d("DATABASE", userModel.toString());
        else Log.d("DATABASE", "USER NOT FOUND");
    }
}