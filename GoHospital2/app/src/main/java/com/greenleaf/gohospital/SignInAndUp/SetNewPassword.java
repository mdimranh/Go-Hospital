package com.greenleaf.gohospital.SignInAndUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.greenleaf.gohospital.R;

public class SetNewPassword extends AppCompatActivity {
    TextInputLayout new_password, confirm_password;
    Button okclick;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        okclick = findViewById(R.id.ok);

        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
    }

    public void okclick(View view) {
        progressBar.setVisibility(View.VISIBLE);

        String _new_password = new_password.getEditText().getText().toString().trim();
        String _confirm_new_password = confirm_password.getEditText().getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("phoneNo");

        if (_new_password.equals(_confirm_new_password)) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(_phoneNumber).child("password").setValue(_new_password);
            startActivity(new Intent(getApplicationContext(), ForgetSuccess.class));
            finish();
        } else {
            confirm_password.setError("Password doesn't match!");
            progressBar.setVisibility(View.GONE);
        }
    }
}