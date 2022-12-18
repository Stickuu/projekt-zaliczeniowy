package com.example.projekt_zaliczeniowy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.projekt_zaliczeniowy.constants.SharedPreferencesConstants;
import com.example.projekt_zaliczeniowy.database.DatabaseHelper;
import com.example.projekt_zaliczeniowy.fragments.AccountFragment;
import com.example.projekt_zaliczeniowy.fragments.HomeFragment;
import com.example.projekt_zaliczeniowy.fragments.LogedinAccountFragment;
import com.example.projekt_zaliczeniowy.fragments.ShoppingCartFragment;
import com.example.projekt_zaliczeniowy.models.ProductModel;
import com.example.projekt_zaliczeniowy.models.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    HomeFragment homeFragment = new HomeFragment();
    ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
    AccountFragment accountFragment = new AccountFragment();
    LogedinAccountFragment logedinAccountFragment = new LogedinAccountFragment();
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
        insertProducts();
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
                getCorrectAccountFragment();
                return true;
        }

        return false;
    }

    public void getCorrectAccountFragment() {
        Fragment correctAccountFragment = (getCurrentUserIdFromSession() == -1) ? accountFragment : logedinAccountFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, correctAccountFragment).commit();
    }

    public int getCurrentUserIdFromSession() {
        return sharedPreferences.getInt(SharedPreferencesConstants.USER_ID_KEY, -1);
    }

    public UserModel getCurrentUser(int id) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        UserModel currentUser = databaseHelper.getUserByID(id);

        return currentUser;
    }

    private void insertProducts() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        List<ProductModel> products = databaseHelper.getAllProducts();

        if(products.size() == 0) {
            Log.d("DATABASE", "insertnig products");
            for (int i = 1; i < 11; i++) {
                databaseHelper.addProduct(new ProductModel(
                        "Product Name " + String.valueOf(i),
                        "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use",
                        10 * i,
                        "image"
                ));
            }
        }
    }
}