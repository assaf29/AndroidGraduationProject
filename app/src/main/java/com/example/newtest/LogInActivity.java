package com.example.newtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.newtest.PostActivity.myRef;

public class LogInActivity extends AppCompatActivity {

    static User currentUser;

    private EditText inputEmail,
            inputPassword;

    private FirebaseAuth mAuth = null;

    //private ProgressBar progressBar;

    private Button btnSignup,
            btnLogin,
            btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.EmailLogIn);
        inputPassword = (EditText) findViewById(R.id.passwordLogIn);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    public void LoginClicked(View view) {

        final ProgressBar logInProgressBar = (ProgressBar)findViewById(R.id.logInProgressBar);
        final Button btn = (Button)findViewById(R.id.btnLogIn);



        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);
        btn.setVisibility(View.GONE);
        logInProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        //progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                inputPassword.setError("Password too short, enter minimum 6 characters!");
                            } else {
                                Toast.makeText(LogInActivity.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                            }
                        } else {


                            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Query query = myRef.child("Users").orderByChild("userID").equalTo(userID);

                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.

                                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                                        User user = singleSnapshot.getValue(User.class);

                                        currentUser = user;


                                    }

                                    if (currentUser.getUserType().equals("employer")) {

                                        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(LogInActivity.this, "Success to log in", Toast.LENGTH_LONG).show();

                                        finish();

                                    } else {
                                        Intent intent = new Intent(LogInActivity.this, JobSeekerHomeActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(LogInActivity.this, "Success to log in", Toast.LENGTH_LONG).show();

                                        finish();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });



                        }
                    }
                });
    }

    public void newAcountClick(View view) {

        Intent registerActivity = new Intent(this,RegisterActivity.class);
        startActivity(registerActivity);
    }
}
