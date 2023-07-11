package com.example.allergendetector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {
    private RatingBar ratingBar;
    private Button saveButton;
    private TextView yourRatingTextView;
    private TextView avgRatingTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ratingBar = findViewById(R.id.ratingBar);
        saveButton = findViewById(R.id.rating_save_button);
        yourRatingTextView = findViewById(R.id.your_rating);
        avgRatingTextView = findViewById(R.id.avg_rating);

        ratingBar.setIsIndicator(true);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating((float) Math.ceil(rating));
                saveButton.setVisibility(View.VISIBLE);
            }
        });

        saveButton.setVisibility(View.GONE);
        float savedRating = RatingHelper.getRating(this);
        if (savedRating > 0) {
            ratingBar.setRating(savedRating);
            ratingBar.setIsIndicator(true);
            saveButton.setVisibility(View.GONE);
            yourRatingTextView.setText("Your rating: " + savedRating);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                RatingHelper.saveRating(AboutUs.this, rating);
                ratingBar.setIsIndicator(true);
                ratingBar.setRating(rating);
                saveButton.setVisibility(View.GONE);
                yourRatingTextView.setText("Your rating: " + rating);
            }
        });




    }
}