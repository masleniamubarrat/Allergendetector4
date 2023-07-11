package com.example.allergendetector;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    TextView textView, listTextView,getChangePictureText,getNameTextView,
            getUserNameTextView,getEmailTextView,getDateOfBirthTextView,getYourListTextView;
    LinearLayout getProfileLinearLayout,getProfilePictureLinearLayout,
            getChangePictureLayOut,getPersonalInformationLayout,
            getAllergenAndFoodListLinearLayout;
    ImageView getProfilePictureImageView, getChangePictureIcon;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getNameTextView = findViewById(R.id.name_textview);
        getUserNameTextView = findViewById(R.id.user_name_textview);
        getEmailTextView = findViewById(R.id.email_textview);
        getDateOfBirthTextView = findViewById(R.id.date_of_birth_textview);
        getProfileLinearLayout = findViewById(R.id.profile_linearLayout);
        getPersonalInformationLayout = findViewById(R.id.personal_information_linearLayout);


        //adding border to the layouts



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            String uid = currentUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        User user = dataSnapshot.getValue(User.class);
                        if(user != null){
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

                }
            });
        }





    }

}