package com.example.allergendetector;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    TextView textView, listTextView, getChangePictureText, getNameTextView, getUserNameTextView,
            getEmailTextView, getDateOfBirthTextView, getYourListTextView;
    LinearLayout getProfileLinearLayout, getProfilePictureLinearLayout, getChangePictureLayOut,
            getPersonalInformationLayout, getAllergenAndFoodListLinearLayout;
    ImageView getProfilePictureImageView, getChangePictureIcon;
    Button saveButton;
    Uri selectedImageUri;
    boolean isImageSelected = false;
    User user;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getProfilePictureImageView = findViewById(R.id.profile_picture_imageView);
        getNameTextView = findViewById(R.id.name_textview);
        getUserNameTextView = findViewById(R.id.user_name_textview);
        getEmailTextView = findViewById(R.id.email_textview);
        getDateOfBirthTextView = findViewById(R.id.date_of_birth_textview);
        getProfileLinearLayout = findViewById(R.id.profile_linearLayout);
        getPersonalInformationLayout = findViewById(R.id.personal_information_linearLayout);
        saveButton = findViewById(R.id.save_button);

        // Initialize the User object
        user = new User();

        saveButton.setOnClickListener(v -> {
            uploadImage();
            saveButton.setVisibility(View.GONE);
            isImageSelected = false;
        });

        // Set an onClickListener for "change profile picture" text
        TextView changePictureText = findViewById(R.id.change_picture_text);
        changePictureText.setOnClickListener(v -> openImagePicker());

        // Retrieve user data from Firebase and update UI
        retrieveUserData();

        // Load the user's profile picture if available
        loadProfilePicture();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            isImageSelected = true;
            getProfilePictureImageView.setImageURI(selectedImageUri);
            saveButton.setVisibility(View.VISIBLE);
        }
    }

    private void uploadImage() {
        if (selectedImageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference imageRef = storageRef.child("profile_pictures/" + user.getProfilePictureUrl() + ".jpg");

            imageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String downloadUrl = uri.toString();
                            user.setProfilePictureUrl(downloadUrl);

                            // Update the user's profile in the Firebase Realtime Database
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getProfilePictureUrl());
                            userRef.setValue(user)
                                    .addOnSuccessListener(aVoid -> {
                                        // Profile update successful
                                        Toast.makeText(this, "Profile picture uploaded successfully!", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Profile update failed, handle the error
                                        Toast.makeText(this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void retrieveUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User userData = dataSnapshot.getValue(User.class);
                        if (userData != null) {
                            user = userData; // Update the user object with retrieved data

                            String fullName = user.getFullName();
                            String userName = user.getUserName();
                            String email = user.getEmail();
                            String dateOfBirth = user.getBirthDate();

                            getNameTextView.setText(fullName);
                            getUserNameTextView.setText(userName);
                            getEmailTextView.setText(email);
                            getDateOfBirthTextView.setText(dateOfBirth);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        }
    }

    private void loadProfilePicture() {
        if (user != null && user.getProfilePictureUrl() != null && !user.getProfilePictureUrl().isEmpty()) {
            Glide.with(this)
                    .load(user.getProfilePictureUrl())
                    .placeholder(R.drawable.profile)
                    .into(getProfilePictureImageView);
        }
    }
}
