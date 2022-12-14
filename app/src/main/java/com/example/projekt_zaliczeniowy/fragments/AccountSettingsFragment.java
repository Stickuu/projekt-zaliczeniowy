package com.example.projekt_zaliczeniowy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.example.projekt_zaliczeniowy.MainActivity;
import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.constants.SharedPreferencesConstants;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    SwitchMaterial darkModeSwitch;
    Spinner languageSpinner;
    SharedPreferences sharedPreferences;
    HashMap<String, String> languageCodes = new HashMap<String, String>() {{
        put("Polski", "PL");
        put("English", "US");
    }};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountSettingsFragment newInstance(String param1, String param2) {
        AccountSettingsFragment fragment = new AccountSettingsFragment();
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
        return inflater.inflate(R.layout.fragment_account_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        darkModeSwitch = getView().findViewById(R.id.accountSettingsDarkModeSwitch);
        languageSpinner = getView().findViewById(R.id.languageSpinner);
        sharedPreferences = getActivity().getSharedPreferences(SharedPreferencesConstants.SHARED_PREFS, Context.MODE_PRIVATE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.languages,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        // get and set spinner to actuall language
        int currentLanguageIndex = (sharedPreferences.getString(SharedPreferencesConstants.LANGUAGE_KEY, "PL").equals("PL")) ? 0 : 1;
        languageSpinner.setSelection(currentLanguageIndex, false);

        languageSpinner.setOnItemSelectedListener(this);

        darkModeSwitch.setChecked(sharedPreferences.getBoolean(SharedPreferencesConstants.DARK_THEME_KEY, false));

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                MainActivity mainActivity = ((MainActivity) getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (isChecked) {
                    mainActivity.setDarkTheme();
                    editor.putBoolean(SharedPreferencesConstants.DARK_THEME_KEY, true);
                    editor.apply();
                    return;
                }

                mainActivity.setLightTheme();
                editor.putBoolean(SharedPreferencesConstants.DARK_THEME_KEY, false);
                editor.apply();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        ((MainActivity) getActivity()).setLanguage(languageCodes.get(adapterView.getItemAtPosition(i).toString()));

        // save language to sharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharedPreferencesConstants.LANGUAGE_KEY, languageCodes.get(adapterView.getItemAtPosition(i).toString()));
        editor.apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}