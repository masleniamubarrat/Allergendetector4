package com.example.allergendetector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ReviewPageFragment extends Fragment {

    public static ReviewPageFragment newInstance(List<String> reviewPage) {
        ReviewPageFragment fragment = new ReviewPageFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("reviewPage", new ArrayList<>(reviewPage));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_review_page, container, false);

        // Access the TextViews in the layout and update their text
        TextView review1TextView = rootView.findViewById(R.id.review1);
        TextView review2TextView = rootView.findViewById(R.id.review2);
        TextView review3TextView = rootView.findViewById(R.id.review3);

        List<String> reviewPage = getArguments().getStringArrayList("reviewPage");
        if (reviewPage != null && reviewPage.size() >= 3) {
            review1TextView.setText(reviewPage.get(0));
            review2TextView.setText(reviewPage.get(1));
            review3TextView.setText(reviewPage.get(2));
        }

        return rootView;
    }
}

