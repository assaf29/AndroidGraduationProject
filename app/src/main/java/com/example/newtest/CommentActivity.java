package com.example.newtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.newtest.LogInActivity.currentUser;
import static com.example.newtest.PostActivity.myRef;

public class CommentActivity extends AppCompatActivity {
    String currentPostID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    }


    public void commentClick(View view) {
        Intent intent = getIntent();
        currentPostID = intent.getStringExtra("currentPostID");

        EditText contentEditText = (EditText) findViewById(R.id.contentEditText);

        String content = contentEditText.getText().toString();

        if (content.isEmpty() == false) {

            String id = myRef.push().getKey();

            String createdUser = currentUser.getUserName();
            Comment comment = new Comment(id,content , getCurrentDate() ,createdUser );

            myRef.child("Posts").child(currentPostID).child("Comments").child(id).setValue(comment);


            finish();
        }else {

            Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_LONG).show();

        }

    }


    public String getCurrentDate(){

        String date = new SimpleDateFormat("HH:mm | yyyy/MM/dd", Locale.getDefault()).format(new Date());

        return date;
    }
}
