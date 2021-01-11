package com.greenleaf.gohospital.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.greenleaf.gohospital.Database.SessionManager;
import com.greenleaf.gohospital.R;

import java.util.HashMap;


public class UserProfile extends AppCompatActivity {
    Button profileupdate;
    TextView username;
    TextView phonetextview;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        profileupdate = findViewById(R.id.profileupdate);
        backbtn = findViewById(R.id.backbtn);

        username = findViewById(R.id.username);
        phonetextview = findViewById(R.id.phonetextview);

        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSESSION);
        HashMap<String, String> userDetails =  sessionManager.getUsersDetailFromSession();

        String fullname = userDetails.get(SessionManager.KEY_FULLNAME);
        String phoneNumber = userDetails.get(SessionManager.KEY_PHONENUMBER);
        username.setText(fullname);
        phonetextview.setText(phoneNumber);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UserHome.class);
                startActivity(i);
                finish();
            }
        });

    }


    public void profileclick(View view) {
        Intent intent = new Intent(this, ProfileUpdate.class);

        String fullName = getIntent().getStringExtra("fullName");
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phoneNo");
        String date = getIntent().getStringExtra("date");
        String gender = getIntent().getStringExtra("gender");
        String password = getIntent().getStringExtra("password");

        intent.putExtra("fullName", fullName);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("phoneNo", phone);
        intent.putExtra("date", date);
        intent.putExtra("gender", gender);
        intent.putExtra("password", password);

        startActivity(intent);
    }
}