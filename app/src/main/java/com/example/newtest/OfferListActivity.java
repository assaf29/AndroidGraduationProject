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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.newtest.LogInActivity.currentUser;
import static com.example.newtest.PostActivity.myRef;

public class OfferListActivity extends AppCompatActivity {


    private ListView lvOffers;
    private offerListCustomAdapter adapter;
    private List<Offer> mOffer;

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);


        lvOffers = (ListView)findViewById(R.id.offerListView);
        mOffer = new ArrayList<>();

        myDialog = new Dialog(this);

    }

    @Override
    protected void onStart() {
        super.onStart();




        Query query = null;

        if (currentUser.getUserType().equals("employer")) {
             query = myRef.child("offers").orderByChild("offerReceiver").equalTo(currentUser.getUserName());

        }else {
             query = myRef.child("offers").orderByChild("offerSender").equalTo(currentUser.getUserName());

        }
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                mOffer.clear();

                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Offer offer = singleSnapshot.getValue(Offer.class);

                    mOffer.add(offer);
                    Toast.makeText(getApplicationContext(),"found: "+offer.getOfferSender(), Toast.LENGTH_LONG).show();
                }

                adapter = new offerListCustomAdapter(getApplicationContext(), mOffer);
                lvOffers.setAdapter(adapter);


                lvOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                        if (currentUser.getUserType().equals("employer")) {
                            TextView txtclose;
                            Button btnAccept, btnReject, btnProfile, btnChat;
                            myDialog.setContentView(R.layout.search_layout);

                            btnAccept = (Button) myDialog.findViewById(R.id.btnAccept);
                            btnReject = (Button) myDialog.findViewById(R.id.btnReject);
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
                                    String title = mOffer.get(position).getPostTitle();
                                    String jobSeeker = mOffer.get(position).getOfferSender();
                                    String employer = mOffer.get(position).getOfferReceiver();

                                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

                                    intent.putExtra("title", title);
                                    intent.putExtra("jobSeeker", jobSeeker);
                                    intent.putExtra("employer", employer);

                                    startActivity(intent);
                                }
                            });

                            btnProfile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    myRef.child("Users").orderByChild("userName").equalTo(mOffer.get(position).getOfferSender()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {


                                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                                                User user = singleSnapshot.getValue(User.class);

                                                String userName = user.getUserName();
                                                String userImage = user.getImage();
                                                String userCategory = user.getCategory();
                                                String userDescWork = user.getDescWork();
                                                String userGender = user.getGender();

                                                Intent userProfileIntent = new Intent(getApplicationContext(),ShowUserProfileActivity.class);

                                                userProfileIntent.putExtra("userName",userName);
                                                userProfileIntent.putExtra("userImage",userImage);
                                                userProfileIntent.putExtra("userCategory",userCategory);
                                                userProfileIntent.putExtra("userDescWork",userDescWork);
                                                userProfileIntent.putExtra("userGender",userGender);

                                                startActivity(userProfileIntent);


                                            }


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                        }
                                    });

                                }
                            });


                            btnReject.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String id = mOffer.get(position).id;
                                    Toast.makeText(getApplicationContext(),"id: "+id,Toast.LENGTH_LONG).show();
                                    myRef.child("offers").child(id).removeValue();
                                    myDialog.dismiss();


                                }
                            });

                            btnAccept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final String postID = mOffer.get(position).getPostID();

                                    // Read comments from the database
                                    myRef.child("Posts").orderByChild("id").equalTo(postID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {


                                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                                                Post post = singleSnapshot.getValue(Post.class);

                                                myRef.child("Posts").child(postID).child("state").setValue("accepted");

                                            }




                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                        }
                                    });

                                    String employer = mOffer.get(position).getOfferReceiver();
                                    String jobSeeker = mOffer.get(position).getOfferSender();
                                    String postTitle = mOffer.get(position).getPostTitle();
                                    String offerID = mOffer.get(position).id;
                                    String id =  myRef.push().getKey();

                                    AcceptedOffer acceptedOffer = new AcceptedOffer(id , employer , jobSeeker , postID , postTitle);
                                    myRef.child("AcceptedOffers").child(id).setValue(acceptedOffer);

                                    myRef.child("offers").child(offerID).removeValue();
                                    myDialog.dismiss();

                                }
                            });

                            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            myDialog.show();
                        } else {

//                            for job seeker

                            String title = mOffer.get(position).getPostTitle();
                            String jobSeeker = mOffer.get(position).getOfferSender();
                            String employer = mOffer.get(position).getOfferReceiver();

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
                Toast.makeText(getApplicationContext(),"something wrong",Toast.LENGTH_LONG).show();
            }

        });
    }


    public void homeClick(View view) {
        Intent intent = new Intent(this , HomeActivity.class);
        startActivity(intent);

    }


    public void userInfo(String userName){




    }
}
