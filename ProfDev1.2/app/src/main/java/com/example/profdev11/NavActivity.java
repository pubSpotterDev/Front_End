package com.example.profdev11;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class NavActivity extends AppCompatActivity {

     TextView tvGreeting,tvPoints,tvPubLabel;
     ListView pubList;
     int points = 0;
     int age = 21;
     String gender = "male";
     String[] pubs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        tvGreeting = findViewById(R.id.tvGreeting);
        tvPoints = findViewById(R.id.tvPoints);
        tvPubLabel = findViewById(R.id.tvPubLabel);
        pubList = findViewById(R.id.lsPubs);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        tvGreeting.setText("Hello "+username);
        tvPoints.setText("You have "+points+" points");
        tvPubLabel.setText(("You are near the following pubs: "));

        tvGreeting.setText("Hello: "+username);
        tvPoints.setText("You have "+points+" points");
        tvPubLabel.setText(("You have been to the following pubs: "));



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

    public void showMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}
