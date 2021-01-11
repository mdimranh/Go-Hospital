package com.greenleaf.gohospital.Database;

public class UserHelperClass {

    String fullName, email, username, password, date, gender, phoneNo, adress;

    public UserHelperClass(){}

    public UserHelperClass(String fullName, String email, String username, String password, String date, String gender, String phoneNo, String adress) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.date = date;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.adress = adress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getadress() {
        return adress;
    }

    public void setadress(String adress) {
        this.adress = adress;
    }
}
