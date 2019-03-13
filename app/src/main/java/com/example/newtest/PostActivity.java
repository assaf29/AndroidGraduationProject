package com.example.newtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.newtest.LogInActivity.currentUser;

public class PostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference();

    public String category = null ;
    public String price = null ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);



        //        to show category spinner

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                             R.array.search, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //        to show price spinner

        Spinner priceSpinner = findViewById(R.id.priceSpinner);
        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(this,
                R.array.prices, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceSpinner.setAdapter(priceAdapter);
        priceSpinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (parent.getId()) {

            case R.id.spinner:

                if (position == 0){
                    category = "programing";

                } if (position == 1){
                category="designing";

                } if (position == 2){
                category="translating";

                } if (position == 3){
                category = "marketing";

                } if (position == 4){
                category = "all";
            }
            break;

            case R.id.priceSpinner:

            if (position == 0 ) {
                price = "0-500";
            }
            if (position == 1 ) {
                price = "500-1000";
            }
            if (position == 2 ) {
                price = "1000-5000";
            }
            if (position == 3 ) {
                price = "5000-10000";
            }
            if (position == 4 ) {
                price = "10000-15000";
            }
            if (position == 5 ) {
                price = "15000-20000";
            }
            if (position == 6 ) {
                price = "يحدد بالاتفاق";
                break;
            }

            default:
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void postClick(View view) {

        EditText titleEditText = (EditText) findViewById(R.id.titleEditText);
        EditText contentEditText = (EditText) findViewById(R.id.contentEditText);



        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();



        if (title.isEmpty() == false && content.isEmpty() == false) {

            String id = myRef.push().getKey();

            Post post = new Post(id, title, content , category , getCurrentDate(),currentUser.getUserName(),"posted", price);
            myRef.child("Posts").child(id).setValue(post);

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