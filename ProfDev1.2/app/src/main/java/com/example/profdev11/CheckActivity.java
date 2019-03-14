package com.example.profdev11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CheckActivity extends AppCompatActivity {

    int age = 21;
    String gender = "male";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Intent intent = getIntent();
        int points = intent.getIntExtra("USERNAME",0);
        points ++;

        Intent intentAccount = new Intent(CheckActivity.this,AccountActivity.class);
        Intent intentGet = getIntent();
        String username = intentGet.getStringExtra("USERNAME");
        intentAccount.putExtra("AGE",age);
        intentAccount.putExtra("POINTS",points);
        intentAccount.putExtra("GENDER",gender);
        startActivity(intentAccount);

    }
}
