package com.example.allergendetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutUs extends AppCompatActivity {
    private RatingBar ratingBar;
    private Button saveButton;
    private TextView yourRatingTextView;
    private TextView avgRatingTextView;

    private DatabaseReference userRef;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ratingBar = findViewById(R.id.ratingBar);
        saveButton = findViewById(R.id.rating_save_button);
        yourRatingTextView = findViewById(R.id.your_rating);
        avgRatingTextView = findViewById(R.id.avg_rating);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
            userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        } else {
            // User not authenticated, handle this case accordingly
        }




        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating((float) Math.ceil(rating));
                saveButton.setVisibility(View.VISIBLE);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                saveRatingToDatabase(rating);

                saveButton.setVisibility(View.GONE);
                yourRatingTextView.setText("Your rating: " + rating);
            }
        });

        loadRatingFromDatabase();




    }
    private void loadRatingFromDatabase() {
        if (userRef != null) {
            userRef.child("rating").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        float rating = dataSnapshot.getValue(Float.class);
                        ratingBar.setRating(rating);

                        yourRatingTextView.setText("Your rating: " + rating);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }
    }

    private void saveRatingToDatabase(float rating) {
        if (userRef != null) {
            userRef.child("rating").setValue(rating)
                    .addOnSuccessListener(aVoid -> {
                        // Rating saved successfully
                        Toast.makeText(AboutUs.this, "Rating saved!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Failed to save rating, handle the error
                        Toast.makeText(AboutUs.this, "Failed to save rating: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

}