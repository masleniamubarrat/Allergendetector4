package com.example.allergendetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    displayUserInfo(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log the error for debugging
                Log.e("ProfileActivity", "Error retrieving user information: " + error.getMessage());

                // Display an error message to the user
                Toast.makeText(Profile.this, "Failed to retrieve user information.", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void displayUserInfo(User user){

        Log.d("Profile", "User information: " + user.getFullName() + ", " + user.getUserName() + ", " + user.getEmail() + ", " + user.getBirthDate());
        LinearLayout personalInfoLayout = findViewById(R.id.personal_information_linearLayout);
        TextView textView = findViewById(R.id.name_textview);
        textView.setText(user.getFullName());



    }


}