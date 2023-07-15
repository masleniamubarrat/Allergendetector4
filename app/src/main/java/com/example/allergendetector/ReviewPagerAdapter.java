package com.example.allergendetector;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReviewPagerAdapter extends FragmentStateAdapter {
    private final List<List<String>> reviewPages;

    public ReviewPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, List<List<String>> reviewPages) {
        super(fragmentManager, lifecycle);
        this.reviewPages = reviewPages;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        List<String> reviewPage = reviewPages.get(position);
        return ReviewPageFragment.newInstance(reviewPage);
    }

    @Override
    public int getItemCount() {
        return reviewPages.size();
    }
}
