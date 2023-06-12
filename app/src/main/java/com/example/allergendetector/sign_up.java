package com.example.allergendetector;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.allergendetector.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class sign_up extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText signUpNameEditText;
    EditText signUpEmailEditText;
    EditText signUpPasswordEditText;
    EditText signUpRetypePasswordEditText ;
    Button signUpButton ;
    TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        signUpNameEditText = findViewById(R.id.sign_up_name);
        signUpEmailEditText = findViewById(R.id.sign_up_email);
        signUpPasswordEditText = findViewById(R.id.sign_up_password);
        signUpRetypePasswordEditText = findViewById(R.id.sign_up_retype_password);
        signUpButton = findViewById(R.id.sign_up_button);
        textView = findViewById(R.id.sign_in_link);




        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();

            }
        });



        textView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_up.this, MainActivity.class );
                startActivity(intent);

            }
        });




    }

    private void userRegister() {
        String signUpEmail = signUpEmailEditText.getText().toString().trim();
        String signUpPassword = signUpPasswordEditText.getText().toString().trim();

        if(signUpEmail.isEmpty())
        {
            signUpEmailEditText.setError("Enter an email address");
            signUpEmailEditText.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(signUpEmail).matches())
        {
            signUpEmailEditText.setError("Enter a valid email address");
            signUpEmailEditText.requestFocus();
            return;
        }

        //checking the validity of the password
        if(signUpPassword.isEmpty())
        {
            signUpPasswordEditText.setError("Enter a password");
            signUpPasswordEditText.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(signUpEmail, signUpPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Registered successfully", Toast.LENGTH_SHORT).show();

                } else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)

                    {Toast.makeText(getApplicationContext(),"User is already registered", Toast.LENGTH_SHORT).show();}

                    else{
                        Toast.makeText(getApplicationContext(),"Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        Intent intent = new Intent(sign_up.this, homePage.class );
        startActivity(intent);



    }
}
