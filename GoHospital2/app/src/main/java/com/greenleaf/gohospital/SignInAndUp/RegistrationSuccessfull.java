package com.greenleaf.gohospital.SignInAndUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.greenleaf.gohospital.R;

public class RegistrationSuccessfull extends AppCompatActivity {
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_successfull);

        ok = findViewById(R.id.signinbtn);
    }
    public void signinclick(View view){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
}