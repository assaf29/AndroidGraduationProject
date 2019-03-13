package com.example.newtest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myReff = db.getReference();

    private EditText inputEmail,
            inputPassword,
            inputUserName,

            inputDescWork;


    String strGender,
            userType,
            userName,
            email,
            descWork = "",
            password,
            category,
            profileImage="";

//    User Image
    ImageView imguserPhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImageUri;
//  End User Image


    private Button btnSignIn,
            btnSignUp,
            btnResetPassword;

    RadioGroup mGender , userTypeGroup;

    RadioButton mGenderOption , userTypeOption;

    RadioGroup field;

    RadioButton fieldOption;


    private FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();


        inputEmail = (EditText) findViewById(R.id.EmailTextView);
        inputPassword = (EditText) findViewById(R.id.passwordTextView);
        inputUserName = (EditText) findViewById(R.id.nameTextView);

        inputDescWork = (EditText) findViewById(R.id.descTextView);
        mGender = findViewById(R.id.radGroup);
        userTypeGroup = findViewById(R.id.radioUserType);
        field = findViewById(R.id.radioField);


//        click on user profile to chooose one
        imguserPhoto = (ImageView)findViewById(R.id.regUserPhoto);
        imguserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >=22){

                    checkAndRequestPermission();
                }else {
                    openGalary();
                }
            }
        });


        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mGenderOption = mGender.findViewById(i);

                switch (i) {
                    case R.id.Male:
                        strGender = "ذكر";

                        break;
                    case R.id.Female:
                        strGender = "انثى";
                        break;

                        default:
                    }
                }
            });


            final Spinner spinner = findViewById(R.id.spinner1);
            final TextView spinnetTextView = (TextView) findViewById(R.id.spinnerTextView);

            userTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    userTypeOption = userTypeGroup.findViewById(checkedId);

                    switch (checkedId){



                    case R.id.JobSeekersID:
                    userType="jobSeeker";


                        spinner.setVisibility(View.VISIBLE);
                        spinnetTextView.setVisibility(View.VISIBLE);

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(RegisterActivity.this,
                                R.array.search, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(RegisterActivity.this);
                        inputDescWork.setVisibility(View.VISIBLE);
                    break;

                    case R.id.EmployerID:
                    userType="employer";
                        spinner.setVisibility(View.GONE);
                        spinnetTextView.setVisibility(View.GONE);
                        inputDescWork.setVisibility(View.GONE);


                    default:
                }
            }
        });


    }

    private void openGalary() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/");
        startActivityForResult(galleryIntent,REQUESCODE);

    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(RegisterActivity.this,"Please accept for required permission",Toast.LENGTH_LONG).show();

            } else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                                    PReqCode);
            }


        }else {
            openGalary();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){

            pickedImageUri= data.getData();
            imguserPhoto.setImageURI(pickedImageUri);
            imguserPhoto.setMaxWidth(10);
            imguserPhoto.setMaxHeight(10);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position == 0) {

            category = "تطوير البرامج";

        }
        if (position == 1) {
            category = "التصميم والمونتاج";

        }
        if (position == 2) {
            category = "الترجمة";

        }
        if (position == 3) {
            category = "الدعاية والإعلان";

        }
        if (position == 4) {
            category = "عام";

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void registerClick(View view) {

        final ProgressBar registerProgressBar = (ProgressBar)findViewById(R.id.registerProgressBar);
        final Button btn = (Button)findViewById(R.id.btnReg);

        email = inputEmail.getText().toString();
        password = inputPassword.getText().toString();
        userName = inputUserName.getText().toString();
        category = category;
        descWork = inputDescWork.getText().toString();
        profileImage = getPhotoUri();

        Toast.makeText(getApplicationContext(), "image: "+profileImage,Toast.LENGTH_LONG).show();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
            return;
        }





        Query userUniqueNameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("userName").equalTo(userName);
        userUniqueNameQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){

                    Toast.makeText(getApplicationContext(),"Choose another name",Toast.LENGTH_LONG).show();

                }else {
                    btn.setVisibility(View.GONE);
                    registerProgressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {



                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "AutheFirebaseDatabasentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        User user = new User(userName, email, descWork, password, strGender, category,userID,userType , profileImage);


                                        myReff.child("Users").child(userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {


//                                                    Toast.makeText(RegisterActivity.this, "Done ", Toast.LENGTH_SHORT).show();
                                                    if(profileImage !=null || profileImage != "") {
                                                        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }

                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "fail", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });



                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    public String getPhotoUri(){

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImageUri.getLastPathSegment());

        imageFilePath.putFile(pickedImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return imageFilePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downUri = task.getResult();

                    profileImage = String.valueOf(downUri);

                } else {

                    profileImage ="https://firebasestorage.googleapis.com/v0/b/newtest-95c39.appspot.com/o/users_photos%2FuserImage.png?alt=media&token=ca1fc7d9-e92b-486c-9d35-79e1a418d7ee";
                }
            }
        });

        return profileImage ;
    }

}