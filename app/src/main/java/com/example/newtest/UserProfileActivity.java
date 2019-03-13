package com.example.newtest;

import android.annotation.SuppressLint;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.newtest.LogInActivity.currentUser;
import static com.example.newtest.PostActivity.myRef;

public class UserProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView userProfileName;
    TextView userProfileEmail ;
    TextView userProfileUserType ;
    TextView userProfilePreviousWork ;


    EditText EditUserProfileEmail ;
    EditText EditUserProfileUserType ;
    EditText EditUserProfilePreviousWork ;
    EditText EditUserProfilePass ;

    String category;
    Spinner spinner ;
    CircleImageView userImage ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userImage = (CircleImageView)findViewById(R.id.userImage);

        userProfileName = (TextView)findViewById(R.id.userProfileName);
        userProfileEmail = (TextView)findViewById(R.id.userProfileEmail);
        userProfileUserType = (TextView)findViewById(R.id.userProfileUserType);
        userProfilePreviousWork = (TextView)findViewById(R.id.userProfilePreviousWork);



        EditUserProfileEmail = (EditText) findViewById(R.id.EditUserProfileEmail);
        EditUserProfilePreviousWork = (EditText) findViewById(R.id.EditUserProfilePreviousWork);
        EditUserProfilePass = (EditText)findViewById(R.id.EditUserProfilePass);


//        Picasso.get().load(currentUser.getImage()).into(userImage);
        userProfileName.setText(currentUser.getUserName());
        userProfileEmail.setText(currentUser.getEmail());
        userProfileUserType.setText(currentUser.getUserType()+" : "+currentUser.getCategory());
        userProfilePreviousWork.setText(currentUser.getDescWork());


       spinner = findViewById(R.id.spinner1);
        final TextView spinnetTextView = (TextView) findViewById(R.id.spinnerTextView);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(UserProfileActivity.this,
                          R.array.search, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(UserProfileActivity.this);


        }


    @SuppressLint("RestrictedApi")
    public void editProfileClick(View view) {

        final FloatingActionButton saveBtn = (FloatingActionButton)findViewById(R.id.saveBtn);
        final Button btn = (Button)findViewById(R.id.btnEditUserProfile);

        btn.setVisibility(View.GONE);
        saveBtn.setVisibility(View.VISIBLE);

        userProfileEmail.setVisibility(View.GONE);
        EditUserProfileEmail.setVisibility(View.VISIBLE);
        EditUserProfileEmail.setText(userProfileEmail.getText().toString());

        userProfilePreviousWork.setVisibility(View.GONE);
        EditUserProfilePreviousWork.setVisibility(View.VISIBLE);
        EditUserProfilePreviousWork.setText(userProfilePreviousWork.getText().toString());

        final LinearLayout passLayout = (LinearLayout)findViewById(R.id.editUserProfilePasswordID);
        passLayout.setVisibility(View.VISIBLE);
        EditUserProfilePass.setVisibility(View.VISIBLE);
        EditUserProfilePass.setText(currentUser.getPass());

        userProfileUserType.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                String email = EditUserProfileEmail.getText().toString();
                String descWork = EditUserProfilePreviousWork.getText().toString();

                String pass = currentUser.getPass();

                firebaseUser.updateEmail(email);
                firebaseUser.updatePassword(pass);


                User user = new User(currentUser.getUserName(),email,descWork,pass,currentUser.getGender(),category,currentUser.getUserID(),currentUser.getUserType(),currentUser.getImage());
                myRef.child("Users").child(currentUser.getUserID()).setValue(user);


                passLayout.setVisibility(View.GONE);
                EditUserProfileEmail.setVisibility(View.GONE);
                EditUserProfilePreviousWork.setVisibility(View.GONE);
                EditUserProfilePass.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);

                userProfileEmail.setText(EditUserProfileEmail.getText().toString());
                userProfileEmail.setVisibility(View.VISIBLE);

                userProfilePreviousWork.setText(EditUserProfilePreviousWork.getText().toString());
                userProfilePreviousWork.setVisibility(View.VISIBLE);

                userProfileUserType.setText(category);
                userProfileUserType.setVisibility(View.VISIBLE);

                saveBtn.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {

            category = "programing";

        }
        if (position == 1) {
            category = "designing";

        }
        if (position == 2) {
            category = "translating";

        }
        if (position == 3) {
            category = "marketing";

        }
        if (position == 4) {
            category = "all";

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
