package com.example.allergendetector;

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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

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



    }












