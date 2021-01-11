package com.greenleaf.gohospital.SignInAndUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.greenleaf.gohospital.Database.SessionManager;
import com.greenleaf.gohospital.Log;
import com.greenleaf.gohospital.R;
import com.greenleaf.gohospital.User.UserHome;
import com.hbb20.CountryCodePicker;

public class SignUp3rd extends AppCompatActivity {

    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    RelativeLayout progressBar;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3rd);

        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);
        progressBar = findViewById(R.id.progress);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.progress);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        progressBar.setVisibility(View.GONE);
    }
    public void callVerifyOTPScreen(View view){

        dialog.show();
        progressBar.setVisibility(View.GONE);

        final String fullName = getIntent().getStringExtra("fullName");
        final String email = getIntent().getStringExtra("email");
        final String username = getIntent().getStringExtra("username");
        final String password = getIntent().getStringExtra("password");
        final String date = getIntent().getStringExtra("date");
        final String gender = getIntent().getStringExtra("gender");
        final String whatToDo = "signup";

        String getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        final String phoneNo = "+"+countryCodePicker.getSelectedCountryCode()+getUserEnteredPhoneNumber;







        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(phoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);
                    dialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                    showCustomDialog();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

                    intent.putExtra("fullName", fullName);
                    intent.putExtra("email", email);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("date", date);
                    intent.putExtra("gender", gender);
                    intent.putExtra("phoneNo", phoneNo);
                    intent.putExtra("whatToDo", whatToDo);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void callLoginFromSignUp(View view){
        Intent i = new Intent(this, SignIn.class);
        startActivity(i);
    }

    private void go(){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp3rd.this);
        builder.setMessage("User alredy exist. Please sign in...")
                .setCancelable(true)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeContextMenu();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
