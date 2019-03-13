package com.example.newtest;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.newtest.LogInActivity.currentUser;
import static com.example.newtest.PostActivity.myRef;

public class AcceptedPostActivity extends AppCompatActivity {



    private ListView lvPost;
    private CustomAcceptedPostAdapter adapter;
    private List<AcceptedOffer> mPost;

    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_post);


        lvPost = (ListView)findViewById(R.id.acceptedPostID);
        mPost =new ArrayList<>();

        myDialog = new Dialog(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = null;
        if (currentUser.getUserType().equals("employer")){

             query = myRef.child("AcceptedOffers").orderByChild("employer").equalTo(currentUser.getUserName());

        } else {

            query = myRef.child("AcceptedOffers").orderByChild("jobSeeker").equalTo(currentUser.getUserName());

        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mPost.clear();

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    AcceptedOffer acceptedOffer = singleSnapshot.getValue(AcceptedOffer.class);

                    mPost.add(0,acceptedOffer);

                }
                adapter = new CustomAcceptedPostAdapter(getApplicationContext(), mPost);
                lvPost.setAdapter(adapter);

                lvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                        if (currentUser.getUserType().equals("employer")) {
                            TextView txtclose;
                            Button btnFinish, btnProfile, btnChat;
                            myDialog.setContentView(R.layout.accepted_offer_popup);

                            btnFinish = (Button) myDialog.findViewById(R.id.btnFinish);
                            btnChat = (Button) myDialog.findViewById(R.id.btnChat);
                            btnProfile = (Button) myDialog.findViewById(R.id.btnProfile);

                            txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
                            txtclose.setText("X");

                            txtclose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    myDialog.dismiss();
                                }
                            });

                            btnChat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    String title = mPost.get(position).getPostTitle();
                                    String jobSeeker = mPost.get(position).jobSeeker;
                                    String employer = mPost.get(position).employer;

                                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

                                    intent.putExtra("title", title);
                                    intent.putExtra("jobSeeker", jobSeeker);
                                    intent.putExtra("employer", employer);

                                    startActivity(intent);
                                }

                            });

                            btnFinish.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    String postID = mPost.get(position).getPostID();
                                    changePostState(postID);



                                }
                            });


                            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            myDialog.show();
                        } else {

//                            for job seeker

                            String title = mPost.get(position).getPostTitle();
                            String jobSeeker = mPost.get(position).jobSeeker;
                            String employer = mPost.get(position).employer;

                            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

                            intent.putExtra("title", title);
                            intent.putExtra("jobSeeker", jobSeeker);
                            intent.putExtra("employer", employer);

                            startActivity(intent);
                        }
                    }
            });
        }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



    }

    public void changePostState(final String postID){


        myRef.child("Posts").orderByChild("id").equalTo(postID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Post post = singleSnapshot.getValue(Post.class);

                    myRef.child("Posts").child(postID).child("state").setValue("finished");

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }

}