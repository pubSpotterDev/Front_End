package com.example.profdev11;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;



public class NavActivity extends AppCompatActivity {

     TextView tvGreeting,tvPoints,tvPubLabel;
     int points = 0;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        tvGreeting = findViewById(R.id.tvGreeting);
        tvPoints = findViewById(R.id.tvPoints);
        tvPubLabel = findViewById(R.id.tvPubLabel);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

        tvGreeting.setText("Hello :"+username);
        tvPoints.setText("You have "+points+" points");
        tvPubLabel.setText(("you have been to the following pubs: "));




    }

}