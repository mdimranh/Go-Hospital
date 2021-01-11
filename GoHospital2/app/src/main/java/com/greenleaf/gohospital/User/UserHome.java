package com.greenleaf.gohospital.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.greenleaf.gohospital.Database.SessionManager;
import com.greenleaf.gohospital.Log;
import com.greenleaf.gohospital.R;

import org.w3c.dom.Text;

import java.util.HashMap;


public class UserHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    public TableLayout tableLayout;
    public ViewPager viewPager;
    TextView textView;
    TextView fullname, phone;
    View nav_view;
    Dialog dialog;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mAuth = FirebaseAuth.getInstance();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.about_apps);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        ImageButton cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        textView = findViewById(R.id.textview);

        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSESSION);
        HashMap<String, String> userDetails =  sessionManager.getUsersDetailFromSession();

        String _fullName = userDetails.get(SessionManager.KEY_FULLNAME);
        String phoneNumber = userDetails.get(SessionManager.KEY_PHONENUMBER);

        textView.setText(_fullName+"\n"+phoneNumber);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");
        //getSupportActionBar().setDisplayShowTitleEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        nav_view = navigationView.getHeaderView(0);
        fullname = (TextView)nav_view.findViewById(R.id.fullname);
        phone = (TextView)nav_view.findViewById(R.id.phone);
        fullname.setText(_fullName);
        phone.setText(phoneNumber);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id == R.id.nav_home){
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_card){
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_profile){
            goprofile();
        }
        else if (id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent i = new Intent(this, Log.class);
            startActivity(i);
        }
        else if (id == R.id.nav_about){
            dialog.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void goprofile() {
        Intent intent = new Intent(this, UserProfile.class);


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

    @Override
    public void onBackPressed() {
    }
}