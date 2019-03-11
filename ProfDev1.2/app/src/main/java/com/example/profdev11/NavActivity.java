package com.example.profdev11;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;



public class NavActivity extends AppCompatActivity {

     TextView tvGreeting,tvPoints,tvPubLabel;
     int points = 0;
     int age = 21;
     String gender = "male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        tvGreeting = findViewById(R.id.tvGreeting);
        tvPoints = findViewById(R.id.tvPoints);
        tvPubLabel = findViewById(R.id.tvPubLabel);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        tvGreeting.setText("Hello :"+username);
        tvPoints.setText("You have "+points+" points");
        tvPubLabel.setText(("you have been to the following pubs: "));


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    Intent intent = new Intent(NavActivity.this, MapsActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_account:
                    Intent intent2 = new Intent(NavActivity.this, AccountActivity.class);
                    Intent intent4 = getIntent();
                    String username = intent4.getStringExtra("USERNAME");
                    intent2.putExtra("NAME",username);
                    intent2.putExtra("AGE",age);
                    intent2.putExtra("POINTS",points);
                    intent2.putExtra("GENDER",gender);

                    startActivity(intent2);
                    return true;
                case R.id.navigation_about:
                    Intent intent3 = new Intent(NavActivity.this,AboutActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };


}