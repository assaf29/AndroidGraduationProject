package com.example.newtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.newtest.PostActivity.myRef;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("الصفحة الرئيسية");

    }


    public void Click(View view) {

        Intent intent;
        String pressedTag = view.getTag().toString();

        switch (pressedTag){

            case "searchPosts":

                 intent = new Intent(this , FirstPage.class);
                startActivity(intent);

                break;
            case "searchJobSeeker":
                intent = new Intent(this , JobSeekerSearchActivity.class);
                startActivity(intent);
        }



    }

    public void userProfileClick(View view) {


        Intent userProfileIntent = new Intent(this , UserProfileActivity.class);
        startActivity(userProfileIntent);

    }


    public void offerListClick(View view) {


        Intent offerListIntent = new Intent(this , OfferListActivity.class);
        startActivity(offerListIntent);


    }

    public void acceptedClick(View view) {

        Intent offerListIntent = new Intent(this , AcceptedPostActivity.class);
        startActivity(offerListIntent);

    }
}
