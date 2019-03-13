package com.example.newtest;

import android.net.Uri;

public class User {
    //extends AppCompatActivity implements ValueEventListener

    String userName;
    String email;
    String descWork;
    String pass;
    String gender;
    String category;
    String userID;
    String userType;
    String image;


    public User(String userName, String email, String descWork, String pass, String gender, String category , String userID , String userType ,String image) {
        this.userName = userName;
        this.email = email;
        this.descWork = descWork;
        this.pass = pass;
        this.gender = gender;
        this.category = category;
        this.userID = userID;
        this.userType = userType;
        this.image = image;
    }
    public User(){
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescWork() {
        return descWork;
    }

    public void setDescWork(String descWork) {
        this.descWork = descWork;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //    @Override
//    public String toString() {
//        return "User{" +
//                "name='" + userName + '\'' +
//                ", email='" + email + '\'' +
//
//                ", descWork='" + descWork + '\'' +
//                ", pass='" + pass + '\'' +
//                ", gender='" + gender + '\'' +
//                '}';
//    }
}
