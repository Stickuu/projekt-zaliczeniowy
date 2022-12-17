package com.example.projekt_zaliczeniowy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projekt_zaliczeniowy.MainActivity;
import com.example.projekt_zaliczeniowy.R;
import com.example.projekt_zaliczeniowy.adapters.FragmentAdapter;
import com.example.projekt_zaliczeniowy.constants.SharedPreferencesConstants;
import com.example.projekt_zaliczeniowy.database.DatabaseHelper;
import com.example.projekt_zaliczeniowy.models.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogedinAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogedinAccountFragment extends Fragment {

    MaterialButton logoutButton;
    SharedPreferences sharedPreferences;
    MaterialTextView userFullName;
    MaterialTextView userEmail;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FragmentAdapter fragmentAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogedinAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogedinAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogedinAccountFragment newInstance(String param1, String param2) {
        LogedinAccountFragment fragment = new LogedinAccountFragment();
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
        return inflater.inflate(R.layout.logedin_account_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences(SharedPreferencesConstants.SHARED_PREFS, Context.MODE_PRIVATE);
        logoutButton = getView().findViewById(R.id.logoutButton);
        userFullName = getView().findViewById(R.id.userFullName);
        userEmail = getView().findViewById(R.id.userEmail);

//         tab layout with viewPager2
        viewPager2 = getView().findViewById(R.id.logedinAccountViewPager);
        tabLayout = getView().findViewById(R.id.logedinAccountTabLayout);
        FragmentManager fragmentManager = getChildFragmentManager();

        fragmentAdapter = new FragmentAdapter(fragmentManager, getLifecycle(), Arrays.asList(new AccountSettingsFragment(), new AccountOrdersFragment()));
        viewPager2.setAdapter(fragmentAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("Settings"));
        tabLayout.addTab(tabLayout.newTab().setText("Your Orders"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

//        get current user
        UserModel currentUser = ((MainActivity) getActivity()).getCurrentUser(
                ((MainActivity) getActivity()).getCurrentUserIdFromSession()
        );

//        set current user data to layout
        userFullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        userEmail.setText(currentUser.getEmail());

//        logout user
        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SharedPreferencesConstants.USER_ID_KEY, -1);
            editor.apply();

            ((MainActivity) getActivity()).getCorrectAccountFragment();

            Toast.makeText(getContext(), "Wylogowano pomyslnie", Toast.LENGTH_SHORT).show();
        });
    }
}