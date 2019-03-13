package com.example.newtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class JobSeekerSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_search);
    }



    public void Click(View view) {


        String searchCategory = view.getTag().toString();

        Intent intent = new Intent(this , JobSeekerActivity.class);
        intent.putExtra("searchCategory",searchCategory);
        startActivity(intent);

    }

    public void searchBtn(View view) {

        EditText searchedWord;
        searchedWord = (EditText)findViewById(R.id.searchedWord1);

        String word = searchedWord.getText().toString();

        Intent intent = new Intent(this , JobSeekerActivity.class);
        intent.putExtra("searchedWord",word);
        Toast.makeText(this,"word : "+word,Toast.LENGTH_LONG).show();
        startActivity(intent);

    }
}
