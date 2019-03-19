package com.example.profdev11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String gender = intent.getStringExtra("GENDER");
        int points = intent.getIntExtra("POINTS",10);
        String age = intent.getStringExtra("DOB");


        tvName.setText("Hey "+username);
        tvAge.setText("You are "+age+" years old");
        tvPoints.setText("You have "+points+" pub points (pp)");

        tvGender.setText("Your gender is :"+gender);
        tvListLabel.setText("These are the pubs you have visited so far: ");

        tvGender.setText("Your gender is: "+gender);

    }//onCreate method

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.navigation_main:
                    Intent intentNav = new Intent(AccountActivity.this,NavActivity.class);
                    startActivity(intentNav);
                    return true;
                case R.id.navigation_map:
                    Intent intentMap = new Intent(AccountActivity.this, MapsActivity.class);
                    startActivity(intentMap);
                    return true;
                case R.id.navigation_account:
                    Toast.makeText(getApplicationContext(),"You are already on the account page",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_about:
                    Intent intentAbout = new Intent(AccountActivity.this,AboutActivity.class);
                    startActivity(intentAbout);
                    return true;
            }//switch
            return false;
        }//onNavigationItemSelected bool
    };//OnNavigationItemSelectedListener
}