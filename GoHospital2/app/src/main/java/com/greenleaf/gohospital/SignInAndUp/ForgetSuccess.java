package com.greenleaf.gohospital.SignInAndUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.greenleaf.gohospital.R;

public class ForgetSuccess extends AppCompatActivity {
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_success);

        signin = findViewById(R.id.signinbtn);
    }
    public void signinclick(View view){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
        finish();
    }
}