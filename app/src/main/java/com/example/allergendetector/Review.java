package com.example.allergendetector;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Review extends AppCompatActivity {
    private ViewPager2 reviewViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review2);

        reviewViewPager = findViewById(R.id.reviewViewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Fetch reviews from Firebase Realtime Database
        DatabaseReference reviewsRef = FirebaseDatabase.getInstance().getReference().child("users");
        reviewsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<List<String>> reviewPages = new ArrayList<>();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String review = userSnapshot.child("review").getValue(String.class);
                    if (review != null) {
                        List<String> reviewPage = new ArrayList<>();
                        reviewPage.add(review);
                        reviewPages.add(reviewPage);
                    }
                }

                ReviewPagerAdapter pagerAdapter = new ReviewPagerAdapter(getSupportFragmentManager(), getLifecycle(), reviewPages);
                reviewViewPager.setAdapter(pagerAdapter);
                TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, reviewViewPager,
                        (tab, position) -> {
                            // Customize tab text or other properties if needed
                        });
                tabLayoutMediator.attach();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error if needed
            }
        });
    }
}

