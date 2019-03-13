package com.example.newtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.newtest.LogInActivity.currentUser;
import static com.example.newtest.PostActivity.myRef;

public class MainActivity extends AppCompatActivity {


    private ListView lvPost;
    private customAdapter adapter;
    private List<Post> mPost;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lvPost = (ListView)findViewById(R.id.listView);
        mPost =new ArrayList<>();


        FloatingActionButton addBtn = (FloatingActionButton)findViewById(R.id.addButton);
        if (currentUser.getUserType().equals("jobSeeker")){
            addBtn.setVisibility(View.GONE);
        }

    }

    public void addClick(View view) {

        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);

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

        if (category == null || category == ""){

            query = myRef.child("Posts").orderByChild("title").equalTo(word);

        }
         else if (!category.equals("all")) {
          query = myRef.child("Posts").orderByChild("category").equalTo(category);
        }else {
            query = myRef.child("Posts");
        }
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                mPost.clear();

                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Post post = singleSnapshot.getValue(Post.class);

                    mPost.add(0,post);

                }
                adapter = new customAdapter(getApplicationContext(), mPost);
                lvPost.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        lvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String title = mPost.get(position).getTitle();
                String content = mPost.get(position).getContent();
                String date = mPost.get(position).getDate();
                String createdUser = mPost.get(position).getCreatedUser();
                String currentPostID = mPost.get(position).getId();
                String postCategory = mPost.get(position).getCategory();
                String postState = mPost.get(position).getState();

                Intent intent = new Intent(getApplicationContext() , ShowPostActivity.class);

                intent.putExtra("title",title);
                intent.putExtra("content",content);
                intent.putExtra("date",date);
                intent.putExtra("createdUser",createdUser);
                intent.putExtra("currentPostID",currentPostID);
                intent.putExtra("objectPosition",position);
                intent.putExtra("postCategory",postCategory);
                intent.putExtra("postState",postState);
                startActivity(intent);
            }
        });

    }


    public void searchClick(View view) {

        Intent intent = new Intent(this , FirstPage.class);
        startActivity(intent);

    }


}