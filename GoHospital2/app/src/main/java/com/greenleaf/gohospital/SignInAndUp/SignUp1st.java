package com.greenleaf.gohospital.SignInAndUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.greenleaf.gohospital.R;

public class SignUp1st extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;

    TextInputLayout fullName, username, email, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1st);


        //Hooks for animation
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        slideText = findViewById(R.id.signup_slide_text);

        fullName = findViewById(R.id.signup_fullname);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

    }


    public void callNextSigupScreen(View view) {

        if (!validateFullName() | !validateUserName() | !validateUserEmail() | !validatePassword()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUp2nd.class);

        String fname = fullName.getEditText().getText().toString().trim();
        String uname = username.getEditText().getText().toString().trim();
        String mail = email.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        intent.putExtra("fullName", fname);
        intent.putExtra("username", uname);
        intent.putExtra("email", mail);
        intent.putExtra("password", pass);

        startActivity(intent);

    }

    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), SignIn.class));
        finish();
    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError("Field can not be empty.");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field can not be empty.");
            return false;
        } else if (val.length() > 20) {
            username.setError("User name is too large.");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No white space are allowed.");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkemail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty.");
            return false;
        }
        else if (!val.matches(checkemail)) {
            email.setError("Invalid Email.");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        //String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" ;        //at least 1 upper case letter
                //"(?=.*[a-zA-Z])"; //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                //"(?=S+$)" ;           //no white spaces
                //".{4,}" +               //at least 4 characters
                //"$";

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } //else if (!val.matches(checkPassword)) {
            //password.setError("At least 1 digit, 1 upper and 1 lowercase letter required.");
            //return false;
        //}
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


}