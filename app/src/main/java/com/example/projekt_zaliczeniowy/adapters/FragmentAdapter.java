package com.example.projekt_zaliczeniowy.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projekt_zaliczeniowy.fragments.LoginFragment;
import com.example.projekt_zaliczeniowy.fragments.SignupFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new SignupFragment();
        }

        return new LoginFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
