package com.example.profdev11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Intent intent = getIntent();
        int points = intent.getIntExtra("USERNAME",0);
        points ++;

        Intent intentAccount = new Intent(CheckActivity.this,NavActivity.class);
        Intent intentGet = getIntent();


        startActivity(intentAccount);

    }
}
