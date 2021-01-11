package com.greenleaf.gohospital.SignInAndUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.greenleaf.gohospital.R;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {
    Button next;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumberTextField;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        next = findViewById(R.id.next);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumberTextField = findViewById(R.id.phonetextfield);
        progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.GONE);


    }
    public void nextclick(View view){

        progressBar.setVisibility(View.VISIBLE);

        if (!validatePhone()) {
            phoneNumberTextField.setError("Field can not be empty");
            progressBar.setVisibility(View.GONE);
            return;
        }

        String getUserEnteredPhoneNumber = phoneNumberTextField.getEditText().getText().toString().trim();
        final String _phoneNo = "+"+countryCodePicker.getSelectedCountryCode()+getUserEnteredPhoneNumber;

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_phoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    phoneNumberTextField.setError(null);
                    phoneNumberTextField.setErrorEnabled(false);

                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    intent.putExtra("phoneNo",_phoneNo);
                    String a = "updateData";
                    intent.putExtra("whatToDo", a);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(ForgetPassword.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ForgetPassword.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private boolean validatePhone() {
        String _phone = phoneNumberTextField.getEditText().getText().toString().trim();

        if (_phone.isEmpty()) {
            return false;
        } else if (!(_phone.isEmpty())) {
            return true;
        }
        return true;
    }
}