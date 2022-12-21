package com.example.projekt_zaliczeniowy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projekt_zaliczeniowy.MainActivity;
import com.example.projekt_zaliczeniowy.constants.SharedPreferencesConstants;
import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.database.DatabaseHelper;
import com.example.projekt_zaliczeniowy.models.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    TextInputEditText emailInput;
    TextInputEditText passwordInput;
    MaterialButton button;
    SharedPreferences sharedPreferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.login_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailInput = getView().findViewById(R.id.loginEmail);
        passwordInput = getView().findViewById(R.id.loginPassword);
        button = getView().findViewById(R.id.loginButton);
        sharedPreferences = getActivity().getSharedPreferences(SharedPreferencesConstants.SHARED_PREFS, Context.MODE_PRIVATE);

        button.setOnClickListener(v -> {

            if(emailInput.length() != 0 && passwordInput.length() != 0) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                UserModel userModel = databaseHelper.getUserByEmailAndPassword(
                        emailInput.getText().toString(),
                        passwordInput.getText().toString()
                );

                if(userModel != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(SharedPreferencesConstants.USER_ID_KEY, userModel.getId());
                    editor.apply();

                    Toast.makeText(getContext(), getResources().getString(R.string.loginToast), Toast.LENGTH_SHORT).show();

                    ((MainActivity) getActivity()).getCorrectAccountFragment();

                    Log.d("USER", "currentUser: " + userModel);
                    return;
                }

                Toast.makeText(getContext(), getResources().getString(R.string.errorToast), Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getContext(), getResources().getString(R.string.fillAllFieldsToast), Toast.LENGTH_SHORT).show();
        });
    }
}