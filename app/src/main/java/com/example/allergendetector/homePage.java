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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

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


                Intent intent = new Intent(homePage.this, demoML.class);
                startActivity(intent);


            }
        });








    }


    }












