package com.example.newtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.newtest.PostActivity.myRef;

public class JobSeekerActivity extends AppCompatActivity {


    private ListView lvUser;
    private userListCustomAdapter adapter;
    private List<User> mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker);


        lvUser = (ListView)findViewById(R.id.userListView);
        mUser = new ArrayList<>();


    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent searchCategory = getIntent();
        final String category = searchCategory.getStringExtra("searchCategory");

        Intent searchWord = getIntent();
        final String word = searchWord.getStringExtra("searchedWord");

        // Read from the database

        Query query = null;

        if(category == null || category ==""){

            query = myRef.child("Users").orderByChild("userName").equalTo(word);
        }
        else if (!category.equals("all")) {
            query = myRef.child("Users").orderByChild("category").equalTo(category);
        }else {
            query = myRef.child("Users");
        }
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                mUser.clear();

                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    User user = singleSnapshot.getValue(User.class);

                    mUser.add(user);
                }

                adapter = new userListCustomAdapter(getApplicationContext(), mUser);
                lvUser.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }

        });

        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String userName = mUser.get(position).getUserName();
                String userImage = mUser.get(position).getImage();
                String userCategory = mUser.get(position).getCategory();
                String userDescWork = mUser.get(position).getDescWork();
                String userGender = mUser.get(position).getGender();

                Intent userProfileIntent = new Intent(getApplicationContext(),ShowUserProfileActivity.class);

                userProfileIntent.putExtra("userName",userName);
                userProfileIntent.putExtra("userImage",userImage);
                userProfileIntent.putExtra("userCategory",userCategory);
                userProfileIntent.putExtra("userDescWork",userDescWork);
                userProfileIntent.putExtra("userGender",userGender);

                startActivity(userProfileIntent);

            }
        });

    }


    public void homeClick(View view) {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }
    public void searchClick(View view) {

        Intent intent = new Intent(this , JobSeekerSearchActivity.class);
        startActivity(intent);

    }
}
