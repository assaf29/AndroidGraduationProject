package com.example.newtest;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import static com.example.newtest.PostActivity.myRef;

public class FirstPage extends AppCompatActivity {

    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        myDialog = new Dialog(this);

    }



    public void Click(View view) {


        String searchCategory = view.getTag().toString();

        Intent intent = new Intent(this , MainActivity.class);
        intent.putExtra("searchCategory",searchCategory);
        startActivity(intent);

    }

    public void searchBtn(View view) {

        EditText searchedWord;
        searchedWord = (EditText)findViewById(R.id.searchedWord1);

        String word = searchedWord.getText().toString();

        Intent intent = new Intent(this , MainActivity.class);
        intent.putExtra("searchedWord",word);
        Toast.makeText(this,"word : "+word,Toast.LENGTH_LONG).show();
        startActivity(intent);

    }
}
