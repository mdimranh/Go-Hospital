package com.greenleaf.gohospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.greenleaf.gohospital.Database.SessionManager;
import com.greenleaf.gohospital.SignInAndUp.SignIn;
import com.greenleaf.gohospital.SignInAndUp.SignUp1st;
import com.greenleaf.gohospital.User.ProfileUpdate;
import com.greenleaf.gohospital.User.UserHome;

import java.util.HashMap;

public class Log extends AppCompatActivity {
    Button signinbtn, signupbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);


        signinbtn = findViewById(R.id.button_signin);
        signupbtn = findViewById(R.id.button_signup);
    }

    public void signinclick(View v) {


        if (!isConnected(this)) {
            showCustomDialog();
        } else {
            Intent i = new Intent(this, SignIn.class);
            startActivity(i);
            finish();
        }
    }

    private boolean isConnected(Log log) {

        ConnectivityManager connectivityManager = (ConnectivityManager) log.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }

    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Log.this);
        builder.setMessage("Please connect to the internet.")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeContextMenu();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void signupclick(View v) {
        if (!isConnected(this)) {
            showCustomDialog();
        } else {
            Intent i = new Intent(this, SignUp1st.class);
            startActivity(i);
        }
    }
}