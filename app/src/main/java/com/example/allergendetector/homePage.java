package com.example.allergendetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class homePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    Button getLogOutButton, getProfileButton, getPopularBLogButton;
    Spinner dropdownSpinner;
    LinearLayout selectedItemsLayout, dropdownMenu;
    List<String> selectedItems;

    boolean isDropdownVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        dropdownMenu = findViewById(R.id.dropdown_menu_1);
        getProfileButton = findViewById(R.id.profile_button);
        getLogOutButton = findViewById(R.id.log_out_button);
        getPopularBLogButton = findViewById(R.id.popular_blog_button);

        getProfileButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homePage.this, Profile.class );
                startActivity(intent);

            }
        });


        getPopularBLogButton.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homePage.this, PopularBlog.class );
                startActivity(intent);

            }
        });

        getLogOutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.clearSession();


                Intent intent = new Intent(homePage.this, MainActivity.class );
                startActivity(intent);
                finish();

            }
        });

        dropdownMenu = findViewById(R.id.dropdown_menu_1);
        dropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isDropdownVisible) {
                   dropdownMenu.setVisibility(View.GONE);
                   isDropdownVisible = false;
               }else{
                   dropdownMenu.setVisibility(View.VISIBLE);
                   isDropdownVisible = true;
               }
            }
        });





    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation item clicks here
        int id = item.getItemId();

        // Close the drawer when an item is selected
        drawer.closeDrawer(GravityCompat.START);

        // Handle your navigation logic here
        // ...

        return true;
    }

    @Override
    public void onBackPressed() {
        // Close the drawer when the back button is pressed
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.homepage_menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
