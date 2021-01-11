package com.greenleaf.gohospital.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.greenleaf.gohospital.Database.SessionManager;
import com.greenleaf.gohospital.Database.UserHelperClass;
import com.greenleaf.gohospital.R;
import com.greenleaf.gohospital.SignInAndUp.SignIn;

import java.util.Calendar;
import java.util.HashMap;

public class ProfileUpdate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextInputLayout dateofbirth;
    TextInputLayout fullname;
    TextInputLayout email;
    TextInputLayout password;
    TextInputLayout address;
    TextInputLayout uname;
    TextInputEditText _date, fname, mail, pass, adress, _uname;
    RadioGroup radioGroup;
    TextView name, phone;
    ImageView backbtn;
    RadioButton selectedGender;
    Button updateprofile;
    ImageButton datebtn;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        updateprofile = findViewById(R.id.updateprofile);
        backbtn = findViewById(R.id.backbtn);

        radioGroup = findViewById(R.id.radio_group);

        name = findViewById(R.id.name);
        uname = findViewById(R.id.username);
        dateofbirth = findViewById(R.id.dateofbirth);
        fullname = findViewById(R.id.fullname);
        phone = findViewById(R.id._phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        datebtn = findViewById(R.id.datebtn);

        _date= findViewById(R.id._dateofbirth);
        _uname = findViewById(R.id._username);
        fname= findViewById(R.id._fullname);
        mail= findViewById(R.id._email);
        pass= findViewById(R.id._password);
        adress = findViewById(R.id.adress);

        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSESSION);
        HashMap<String, String> userDetails =  sessionManager.getUsersDetailFromSession();

        String _fullName = userDetails.get(SessionManager.KEY_FULLNAME);
        final String phoneNumber = userDetails.get(SessionManager.KEY_PHONENUMBER);
        String username = userDetails.get(SessionManager.KEY_USERNAME);
        String date = userDetails.get(SessionManager.KEY_DATE);
        String _email = userDetails.get(SessionManager.KEY_EMAIL);
        String _password = userDetails.get(SessionManager.KEY_PASSWORD);
        final String address = userDetails.get(SessionManager.KEY_ADDRESS);

        name.setText(_fullName);
        fname.setText(_fullName);
        _uname.setText(username);
        _date.setText(date);
        phone.setText(phoneNumber);
        mail.setText(_email);
        pass.setText(_password);
        adress.setText(address);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.progress);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());

        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateGender()){
                    return;
                }
                dialog.show();
                String fname = fullname.getEditText().getText().toString().trim();
                String mail = email.getEditText().getText().toString().trim();
                String _uname = uname.getEditText().getText().toString().trim();
                String _password = password.getEditText().getText().toString().trim();
                String date = dateofbirth.getEditText().getText().toString().trim();
                selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
                String gender = selectedGender.getText().toString();
                String _phone = phoneNumber;
                String _address = adress.getText().toString().trim();
                if (selectedGender.getText() == null ){
                    Toast.makeText(ProfileUpdate.this, "Please select gender.", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                    DatabaseReference reference = rootNode.getReference("Users");
                    UserHelperClass addNewUser = new UserHelperClass(fname, mail, _uname, _password, date, gender, _phone, _address);
                    reference.child(_phone).setValue(addNewUser);
                    dialog.dismiss();
                    Toast.makeText(ProfileUpdate.this, "Profile update successfull.", Toast.LENGTH_SHORT).show();
                    SessionManager sessionManager = new SessionManager(ProfileUpdate.this, SessionManager.SESSION_USERSESSION);
                    sessionManager.createLoginSession(_password, _phone, mail, _uname, fname, date, gender, _address);
                    Intent i = new Intent(getApplicationContext(), UserProfile.class);
                    startActivity(i);

                }
            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UserHome.class);
                startActivity(i);
                finish();
            }
        });


    }

    public void dateclick(View view){
        shoeDatePickerDialog();
    }

    private void shoeDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month+1;
        String date = month+ "/" +dayOfMonth+ "/" +year;
        _date.setText(date);
    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}