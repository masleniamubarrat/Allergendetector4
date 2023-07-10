package com.example.allergendetector;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 2;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Return the corresponding fragment based on the position
        if (position == 0) {
            return new AllergenListFragment();
        } else {
            return new FoodListFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}


