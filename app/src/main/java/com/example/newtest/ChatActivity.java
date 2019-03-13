package com.example.newtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import static com.example.newtest.LogInActivity.currentUser;
import static com.example.newtest.PostActivity.myRef;


public class ChatActivity extends AppCompatActivity {

    EditText input;
    String title , jobSeeker , employer;
    private ListView lvMessages;
    private MessageCustomAdapter adapter;
    private List<ChatMessage> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Intent intent = getIntent();
         title =  intent.getStringExtra("title");
         jobSeeker = intent.getStringExtra("jobSeeker");
         employer = intent.getStringExtra("employer");


        lvMessages = (ListView)findViewById(R.id.list_of_messages);
        mMessages =new ArrayList<>();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);




    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = myRef.child("Messages").child(employer+"_"+jobSeeker);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    ChatMessage message = singleSnapshot.getValue(ChatMessage.class);
                    mMessages.add(message);

                }

                adapter = new MessageCustomAdapter(getApplicationContext() , mMessages);
                lvMessages.setAdapter(adapter);
                scrollMyListViewToBottom();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void sendClick(View view) {

        input = (EditText)findViewById(R.id.input);

        String messageText = input.getText().toString();
        String messageUser = currentUser.getUserName();
        String messageID = myRef.push().getKey();

        ChatMessage message = new ChatMessage(messageText,messageUser,messageID);

        myRef.child("Messages").child(employer+"_"+jobSeeker).child(messageID).setValue(message);


        input.setText("");

        scrollMyListViewToBottom();
    }


    private void scrollMyListViewToBottom() {
        lvMessages.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lvMessages.setSelection(lvMessages.getCount() - 1);
            }
        });
    }
}
