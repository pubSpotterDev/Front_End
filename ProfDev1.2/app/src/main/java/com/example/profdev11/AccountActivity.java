package com.example.profdev11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

    TextView tvName, tvAge, tvPoints, tvGender, tvListLabel;
    ListView lsPubs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tvName = findViewById(R.id.tvName);
        tvAge =  findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvPoints = findViewById(R.id.tvPoints);
        tvListLabel = findViewById(R.id.tvListLabel);
        lsPubs = findViewById(R.id.lsPubs);


        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String gender = intent.getStringExtra("GENDER");
        int points = intent.getIntExtra("POINTS",0);
        int age = intent.getIntExtra("AGE",18);


        tvName.setText("Here are you account details");
        tvAge.setText("You are "+age+" years old");
        tvPoints.setText("You have "+points+" pub points (pp)");

        tvGender.setText("Your gender is :"+gender);
        tvListLabel.setText("These are the pubs you have visited so far: ");




        tvGender.setText("Your gender is: "+gender);

    }
}