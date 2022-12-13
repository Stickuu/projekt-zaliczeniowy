package com.example.projekt_zaliczeniowy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.database.DatabaseHelper;
import com.example.projekt_zaliczeniowy.models.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    TextInputEditText firstNameInput;
    TextInputEditText lastNameInput;
    TextInputEditText emailInput;
    TextInputEditText phoneNumberInput;
    TextInputEditText passwordInput;
    MaterialButton button;
    ViewPager2 viewPager2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
        return inflater.inflate(R.layout.signup_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstNameInput = getView().findViewById(R.id.signupFirstName);
        lastNameInput = getView().findViewById(R.id.signupLastName);
        emailInput = getView().findViewById(R.id.signupEmail);
        phoneNumberInput = getView().findViewById(R.id.signupPhone);
        passwordInput = getView().findViewById(R.id.signupPassword);
        viewPager2 = getView().findViewById(R.id.accountViewPager);

        button = getView().findViewById(R.id.signupButton);

        button.setOnClickListener(v -> {

            if(firstNameInput.length() != 0 &&
                    lastNameInput.length() != 0 &&
                    emailInput.length() != 0 &&
                    phoneNumberInput.length() != 0 &&
                    passwordInput.length() != 0
            ) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                boolean result = databaseHelper.addOne(new UserModel(
                        firstNameInput.getText().toString(),
                        lastNameInput.getText().toString(),
                        emailInput.getText().toString(),
                        passwordInput.getText().toString(),
                        phoneNumberInput.getText().toString()
                ));


                if(result) {
                    Toast.makeText(getContext(), "Zarejestrowano pomyslnie mozesz sie teraz zalogowac", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getContext(), "blad", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getContext(), "Wszystkie pola sa wymagane", Toast.LENGTH_SHORT).show();
        });
    }
}