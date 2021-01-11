package com.greenleaf.gohospital.SignInAndUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.greenleaf.gohospital.Database.SessionManager;
import com.greenleaf.gohospital.R;
import com.greenleaf.gohospital.User.UserHome;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class SignIn extends AppCompatActivity {
    Button signinbtn, signupbtn;
    TextView forgotbtn;
    TextInputLayout phone, password;
    RelativeLayout progress;
    CountryCodePicker countryCodePicker;
    CheckBox checkBox;
    TextInputEditText phoneNoEditText, passwordEditText;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        forgotbtn = findViewById(R.id.forgot);
        signinbtn = findViewById(R.id.signin_button);
        signupbtn = findViewById(R.id.signup_button);

        phone = findViewById(R.id.signin_phone);
        password = findViewById(R.id.signin_password);

        progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);

        countryCodePicker = findViewById(R.id.country_code_picker);

        checkBox = findViewById(R.id.checkbox);

        phoneNoEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.password);


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.progress);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);


        SessionManager sessionManager = new SessionManager(SignIn.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()){
            HashMap<String, String> rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            phoneNoEditText.setText(rememberMeDetails.get(SessionManager.KEY_SSSIONPHONENUMBER));
            passwordEditText.setText(rememberMeDetails.get(SessionManager.KEY_SSSIONPASSWORD));
        }
    }

    public void forgotclick(View view) {
        Intent i = new Intent(this, ForgetPassword.class);
        startActivity(i);
    }

    public void signinclick(View view) {
        if (!validatePassword() && !validatePhone()) {
            password.setError("Field can not be empty");
            phone.setError("Field can not be empty");
            return;
        } else if (!validatePhone()) {
            phone.setError("Field can not be empty");
            password.setError("");
            return;
        } else if (!validatePassword()) {
            password.setError("Field can not be empty");
            phone.setError("");
            return;
        }

        dialog.show();

        final String getUserEnteredPhoneNumber = phone.getEditText().getText().toString().trim();
        final String phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + getUserEnteredPhoneNumber;
        final String _password = password.getEditText().getText().toString().trim();

        if (checkBox.isChecked()){
            SessionManager sessionManager = new SessionManager(SignIn.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(getUserEnteredPhoneNumber, _password);
        }

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(phoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phone.setError(null);
                    phone.setErrorEnabled(false);

                    String systemPassword = dataSnapshot.child(phoneNo).child("password").getValue(String.class);

                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _password = dataSnapshot.child(phoneNo).child("password").getValue(String.class);
                        String _phoneNo = dataSnapshot.child(phoneNo).child("phoneNo").getValue(String.class);
                        String _email = dataSnapshot.child(phoneNo).child("email").getValue(String.class);
                        String _username = dataSnapshot.child(phoneNo).child("username").getValue(String.class);
                        String _fullName = dataSnapshot.child(phoneNo).child("fullName").getValue(String.class);
                        String _dateOfBirth = dataSnapshot.child(phoneNo).child("date").getValue(String.class);
                        String _gender = dataSnapshot.child(phoneNo).child("gender").getValue(String.class);
                        String _address = dataSnapshot.child(phoneNo).child("adress").getValue(String.class);

                        SessionManager sessionManager = new SessionManager(SignIn.this, SessionManager.SESSION_USERSESSION);
                        sessionManager.createLoginSession(_password, _phoneNo, _email, _username, _fullName, _dateOfBirth, _gender, _address);

                        Intent intent = new Intent(getApplicationContext(), UserHome.class);
                        intent.putExtra("fullName", _fullName);
                        intent.putExtra("username", _username);
                        intent.putExtra("email", _email);
                        intent.putExtra("phoneNo", getUserEnteredPhoneNumber);
                        intent.putExtra("date", _dateOfBirth);
                        intent.putExtra("gender", _gender);
                        intent.putExtra("password", _password);
                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    } else {
                        dialog.dismiss();
                        progress.setVisibility(View.GONE);
                        Toast.makeText(SignIn.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    dialog.dismiss();
                    progress.setVisibility(View.GONE);
                    Toast.makeText(SignIn.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
                progress.setVisibility(View.GONE);
                Toast.makeText(SignIn.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void signupclick(View v) {
        Intent i = new Intent(this, SignUp1st.class);
        startActivity(i);
    }

    private boolean validatePassword() {
        String pass = password.getEditText().getText().toString().trim();

        if (pass.isEmpty()) {
            return false;
        } else if (!(pass.isEmpty())) {
            return true;
        }
        return true;
    }

    private boolean validatePhone() {
        String _phone = phone.getEditText().getText().toString().trim();

        if (_phone.isEmpty()) {
            return false;
        } else if (!(_phone.isEmpty())) {
            return true;
        }
        return true;
    }
}