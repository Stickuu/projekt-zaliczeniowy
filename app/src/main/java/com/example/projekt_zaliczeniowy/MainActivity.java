package com.example.projekt_zaliczeniowy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.projekt_zaliczeniowy.constants.SharedPreferencesConstants;
import com.example.projekt_zaliczeniowy.fragments.AccountFragment;
import com.example.projekt_zaliczeniowy.fragments.HomeFragment;
import com.example.projekt_zaliczeniowy.fragments.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    HomeFragment homeFragment = new HomeFragment();
    ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
    AccountFragment accountFragment = new AccountFragment();
    BottomNavigationView bottomNavigationView;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide top bar
        getSupportActionBar().hide();

        // bottom navigation view setup
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

        sharedPreferences = getSharedPreferences(SharedPreferencesConstants.SHARED_PREFS, Context.MODE_PRIVATE);

        int id = sharedPreferences.getInt(SharedPreferencesConstants.USER_ID_KEY, -1);

        Log.d("USER", "currentUser: " + String.valueOf(id));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;
            case R.id.shoppingCart:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, shoppingCartFragment).commit();
                return true;
            case R.id.account:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, accountFragment).commit();
                return true;
        }

        return false;
    }
}