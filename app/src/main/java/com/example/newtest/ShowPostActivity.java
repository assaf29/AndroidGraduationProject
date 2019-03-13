package com.example.newtest;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.newtest.LogInActivity.currentUser;
import static com.example.newtest.PostActivity.myRef;

public class ShowPostActivity extends AppCompatActivity {
    String currentPostID;

    private ListView lvComment;
    private CommentCustomAdapter adapter;
    private List<Comment> mComment;

    Button editBtn,deleteBtn;

    String title ;
    String content ;
    String date ;
    String postCategory;
    String createdUser;
    String postState;


    Dialog myDialog;


    TextView textView1 , textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        Intent intent = getIntent();
         title = intent.getStringExtra("title");
         content = intent.getStringExtra("content");
         date = intent.getStringExtra("date");
         createdUser= intent.getStringExtra("createdUser");
         postCategory= intent.getStringExtra("postCategory");
         postState= intent.getStringExtra("postState");

         textView1 = (TextView)findViewById(R.id.textView);
         textView2 = (TextView)findViewById(R.id.textView2);

         TextView postUserName = (TextView)findViewById(R.id.postUsername);
         TextView postDate = (TextView)findViewById(R.id.postDate);


        textView1.setText(title);
        textView2.setText(content);
        postUserName.setText(createdUser);
        postDate.setText(date);


        lvComment = (ListView)findViewById(R.id.commentListView);
        mComment =new ArrayList<>();


        editBtn = (Button)findViewById(R.id.editBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        if (createdUser.equals(currentUser.getUserName())){

            Toast.makeText(this,"condition true",Toast.LENGTH_LONG).show();
            editBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);

        }

        myDialog = new Dialog(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        currentPostID = intent.getStringExtra("currentPostID");

        // Read comments from the database
        myRef.child("Posts").child(currentPostID).child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              mComment.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Comment comment = singleSnapshot.getValue(Comment.class);
                   mComment.add(comment);
                }

                adapter = new CommentCustomAdapter(getApplicationContext() , mComment);
                lvComment.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



    }

    public void commentClick(View view) {

        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("currentPostID",currentPostID);
        startActivity(intent);
    }


    @SuppressLint("RestrictedApi")
    public void editClick(View view) {
        final FloatingActionButton saveBtn = (FloatingActionButton)findViewById(R.id.saveBtn);

        EditText editPostTitle = (EditText)findViewById(R.id.editPostTitleID);
        EditText editPostContent = (EditText)findViewById(R.id.editPostContentID);

        editPostTitle.setVisibility(View.VISIBLE);
        editPostTitle.setText(textView1.getText().toString());
        editPostContent.setVisibility(View.VISIBLE);
        editPostContent.setText(textView2.getText().toString());

        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);

        editBtn.setVisibility(View.GONE);
        deleteBtn.setVisibility(View.GONE);

        saveBtn.setVisibility(View.VISIBLE);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText editPostTitle = (EditText)findViewById(R.id.editPostTitleID);
                final EditText editPostContent = (EditText)findViewById(R.id.editPostContentID);

                final String title = editPostTitle.getText().toString();
                final String content = editPostContent.getText().toString();

//                Post updatedPost = new Post(currentPostID,title,content,postCategory,getCurrentDate(),createdUser,postState,"100");

                myRef.child("Posts").child(currentPostID).child("title").setValue(title).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        textView1.setText(title);

                        textView1.setVisibility(View.VISIBLE);

                        editBtn.setVisibility(View.VISIBLE);
                        deleteBtn.setVisibility(View.VISIBLE);

                        saveBtn.setVisibility(View.GONE);
                        editPostTitle.setVisibility(View.GONE);
                        editPostContent.setVisibility(View.GONE);

                    }
                });

       myRef.child("Posts").child(currentPostID).child("contet").setValue(content).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        textView2.setText(content);

                        textView2.setVisibility(View.VISIBLE);


                    }
                });

            }
        });



    }

    public void deleteClick(View view) {

        myRef.child("Posts").child(currentPostID).removeValue();
        finish();

    }


    public void offerClick(View v) {
        TextView txtclose;
        Button btnSend ;
        myDialog.setContentView(R.layout.offer_layout);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btnSend = (Button) myDialog.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = myRef.push().getKey();
                String offerSender = currentUser.getUserName();
                String offerReceiver = createdUser;
                String postID = currentPostID;
                String postTitle = title;

                Offer offer = new Offer(id, postID,postTitle, offerSender , offerReceiver , getCurrentDate() );

//                myRef.child("Posts").child(currentPostID).child("Offers").child(id).setValue(offer);

                myRef.child("offers").child(id).setValue(offer);
                myDialog.dismiss();
                Toast.makeText(getApplicationContext(),"تم تقديم العرض بنجاح",Toast.LENGTH_LONG).show();

            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public String getCurrentDate(){

        String date = new SimpleDateFormat("HH:mm | yyyy/MM/dd", Locale.getDefault()).format(new Date());

        return date;
    }
}
