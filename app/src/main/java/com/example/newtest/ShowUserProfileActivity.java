package com.example.newtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowUserProfileActivity extends AppCompatActivity {

    TextView userProfileName;
    TextView userProfileEmail ;
    TextView userProfileUserType ;
    TextView userProfilePreviousWork ;


    CircleImageView userImage ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_profile);

        userImage = (CircleImageView)findViewById(R.id.userImage);

        userProfileName = (TextView)findViewById(R.id.userProfileName);
        userProfileUserType = (TextView)findViewById(R.id.userProfileUserType);
        userProfilePreviousWork = (TextView)findViewById(R.id.userProfilePreviousWork);

        Intent intent = getIntent();

        String userName = intent.getStringExtra("userName");
        String userImage = intent.getStringExtra("userImage");
        String userCategory =intent.getStringExtra("userCategory");
        String userDescWork = intent.getStringExtra("userDescWork");
        String userGender = intent.getStringExtra("userGender");

        userProfileName.setText(userName);
        userProfileUserType.setText("التخصص :"+userCategory);
        userProfilePreviousWork.setText(userDescWork);



    }
}
