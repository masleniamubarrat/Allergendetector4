package com.example.allergendetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

     EditText signInEmailEditText, signInPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign in");

         signInEmailEditText = (EditText) findViewById(R.id.sign_in_email);
         signInPasswordEditText = (EditText) findViewById(R.id.sign_in_password);
        TextView textView = (TextView) findViewById(R.id.sign_up_link);
        Button signInButton  = (Button) findViewById(R.id.sign_in_button);
        textView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, sign_up.class );
                startActivity(intent);

            }
        });


        signInButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                userSignIn();
                /*Intent intent = new Intent(MainActivity.this, homePage.class );
                startActivity(intent);*/

            }
        });


    }

    private void userSignIn() {

        String signUpEmail = signInEmailEditText.getText().toString().trim();
        String signUpPassword = signInPasswordEditText.getText().toString().trim();

        if(signUpEmail.isEmpty())
        {
            signInEmailEditText.setError("Enter an email address");
            signInEmailEditText.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(signUpEmail).matches())
        {
            signInEmailEditText.setError("Enter a valid email address");
            signInEmailEditText.requestFocus();
            return;
        }

        //checking the validity of the password
        if(signUpPassword.isEmpty())
        {
            signInPasswordEditText.setError("Enter a password");
            signInPasswordEditText.requestFocus();
            return;
        }



    }
}