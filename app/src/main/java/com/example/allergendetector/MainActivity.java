package com.example.allergendetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

     EditText signInEmailEditText, signInPasswordEditText;
     private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);





        webView.loadUrl("file:///android_asset/index.html");

        this.setTitle("Sign in");
        mAuth = FirebaseAuth.getInstance();
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

       mAuth.signInWithEmailAndPassword(signUpEmail, signUpPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful())
               {   finish();
                   Intent intent = new Intent(MainActivity.this, homePage.class );
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);

               }
               else{
                   Toast.makeText(getApplicationContext(), "Login unsuccessful", Toast.LENGTH_SHORT).show();
               }

           }
       });

    }
}