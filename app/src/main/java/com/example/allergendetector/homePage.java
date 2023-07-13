package com.example.allergendetector;

import static com.example.allergendetector.R.id.like;
import static com.example.allergendetector.R.id.menu_item_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.ObsoleteCoroutinesApi;

public class homePage extends AppCompatActivity  {
    //private static final int REQUEST_IMAGE_CAPTURE = 1;
    Button getLogOutButton;
    private LinearLayout dropdownMenu;

    private ImageButton cameraIcon;
    private LinearLayout imageLayout;
    private ImageView imageView;
    private TextView foodNameTextView, allergenTextView;
    LinearLayout getImageLayout;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private DatabaseReference databaseRef;
    private List<FoodItem> foodItemList;
    private TextView textSearchResult;
    private SearchView textSearchView;
    private ImageButton likeButton, dislikeButton;
    private int likeCount = 0;
    private DatabaseReference currentUserRef;

     private  TextView likeText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        likeText = findViewById(R.id.like);

        likeButton = findViewById(R.id.like_button);
        dislikeButton = findViewById(R.id.dislike_button);

        // Disable image buttons initially
        likeButton.setEnabled(false);
        dislikeButton.setEnabled(false);

        // Set initial image resource for likeButton and dislikeButton
        likeButton.setImageResource(R.drawable.like);
        dislikeButton.setImageResource(R.drawable.dislike);

        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeButton.setEnabled(true);
                dislikeButton.setEnabled(true);

                String query = textSearchView.getQuery().toString().trim();
                searchFoodItem(query);

            }
        });


        // Retrieve the current user's data from Firebase Realtime Database
        retrieveUserData();

        // Set click listeners for likeButton and dislikeButton
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeButton.setImageResource(R.drawable.liked);
                likeCount++;
                // Update like count in Firebase Realtime Database for the current user
                updateLikeCount(likeCount);
                // Update image resource for likeButton

                // Disable dislikeButton
                dislikeButton.setEnabled(false);
                // Disable both likeButton and dislikeButton
                likeButton.setEnabled(false);
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislikeButton.setImageResource(R.drawable.disliked);
                if (likeCount > 0) {
                    likeCount--;
                    // Update like count in Firebase Realtime Database for the current user
                    updateLikeCount(likeCount);
                }
                // Update image resource for likeButton
                likeButton.setImageResource(R.drawable.like);
                // Disable dislikeButton
                dislikeButton.setEnabled(false);
                // Disable both likeButton and dislikeButton
                likeButton.setEnabled(false);
            }
        });





        databaseRef = FirebaseDatabase.getInstance().getReference("List");
        textSearchResult = findViewById(R.id.text_search_result);
        textSearchView = findViewById(R.id.text_search_search_view);

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = textSearchView.getQuery().toString().trim();
                searchFoodItem(query);
            }
        });

        // Retrieve data from Firebase Realtime Database
        retrieveData();



        findViewById(R.id.logOut_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.clearSession();


                Intent intent = new Intent(homePage.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        findViewById(R.id.profile_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(homePage.this, Profile.class);
                startActivity(intent);


            }
        });

        findViewById(R.id.ml_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(homePage.this,demoMl.class);
                startActivity(intent);


            }
        });

        findViewById(R.id.about_us_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(homePage.this,AboutUs.class);
                startActivity(intent);


            }
        });

        findViewById(R.id.demoProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(homePage.this,demoProfile.class);
                startActivity(intent);


            }
        });


    }
    private void retrieveData() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodItemList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String foodItemName = snapshot.child("Food Item").getValue(String.class);
                    String allergens = snapshot.child("Allergen(s)").getValue(String.class);

                    FoodItem foodItem = new FoodItem(foodItemName, allergens);
                    foodItemList.add(foodItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
    private void searchFoodItem(String query) {
        for (FoodItem foodItem : foodItemList) {
            if (foodItem.getFoodItemName().equalsIgnoreCase(query)) {
                textSearchResult.setText(foodItem.getAllergens());
                return; // Exit the loop once a match is found
            }
        }

        // If no match is found
        textSearchResult.setText("No allergen, safe to eat.");
    }

    private void retrieveUserData() {
        // Get the current user's ID (replace with the actual method to retrieve the user ID)
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String userId = currentUser.getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        currentUserRef = usersRef.child(userId);

        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Integer userLikeCount = dataSnapshot.child("like").getValue(Integer.class);
                    if (userLikeCount != null) {
                        likeCount = userLikeCount;
                        likeText.setText(String.valueOf(likeCount));;


                    }
                } else{ String noData = " no data ";
                    likeText.setText(noData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

    }

    private void updateLikeCount(int likeCount) {
        // Update the "like" attribute value in the user's data
        currentUserRef.child("like").setValue(likeCount);
    }




}












