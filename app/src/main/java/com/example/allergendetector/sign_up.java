package com.example.allergendetector;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.w3c.dom.Text;

import java.util.Calendar;

public class sign_up extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText signUpNameEditText, signUpEmailEditText,signUpPasswordEditText,signUpRetypePasswordEditText,datePickerEditText;
    Button signUpButton ;
    TextView textView ;
    LinearLayout passwordRulesLayout;
    private boolean isPasswordFieldSelected = false;
    private boolean isPasswordValid = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        datePickerEditText = findViewById(R.id.date_picker_edit_text);

        signUpNameEditText = findViewById(R.id.sign_up_name);
        signUpEmailEditText = findViewById(R.id.sign_up_email);
        signUpPasswordEditText = findViewById(R.id.sign_up_password);
        signUpRetypePasswordEditText = findViewById(R.id.sign_up_retype_password);
        signUpButton = findViewById(R.id.sign_up_button);
        textView = findViewById(R.id.sign_in_link);
        passwordRulesLayout = findViewById(R.id.password_rules_layout);
        passwordRulesLayout.setVisibility(View.GONE);



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


        signUpPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isPasswordFieldSelected = hasFocus;
                passwordRulesLayout.setVisibility(hasFocus ? View.VISIBLE: View.GONE);

            }

        });

        signUpPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  validatePassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void validatePassword(String password){
        boolean isLengthValid = password.length()>=7;
        boolean hasNoSpace = !password.contains(" ");
        boolean hasAtLeastOneDigit = password.matches(".*\\d.*");
        boolean hasOnlySpecialCharacter = password.matches("^(?=.*[@#$%^&+=])[^\\s]*$");

        isPasswordValid = isLengthValid && hasNoSpace && hasAtLeastOneDigit && hasOnlySpecialCharacter;

        TextView lengthTextView = findViewById(R.id.password_length);
        TextView spaceTextView = findViewById(R.id.password_no_space);
        TextView digitTextView = findViewById(R.id.password_at_least_one_digit);
        TextView specialCharTextView = findViewById(R.id.password_only_one_special_char);

        lengthTextView.setCompoundDrawablesWithIntrinsicBounds(
                isLengthValid ? R.drawable.ic_check : R.drawable.ic_cross,
                0,0,0
        );
        spaceTextView.setCompoundDrawablesWithIntrinsicBounds(
                hasNoSpace ? R.drawable.ic_check : R.drawable.ic_cross,
                0, 0, 0
        );

        digitTextView.setCompoundDrawablesWithIntrinsicBounds(
                hasAtLeastOneDigit ? R.drawable.ic_check : R.drawable.ic_cross,
                0, 0, 0
        );

        specialCharTextView.setCompoundDrawablesWithIntrinsicBounds(
                hasOnlySpecialCharacter ? R.drawable.ic_check : R.drawable.ic_cross,
                0, 0, 0
        );
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

    public void showDatePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String birthdate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        datePickerEditText.setText(birthdate);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
}

