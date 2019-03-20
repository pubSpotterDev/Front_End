package com.example.profdev11;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    //Hardcoded values to keep the navbar from breaking without dB integration
    int points = 0;
    int age = 21;
    String gender = "male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //Intent intent = getIntent();  // legacy from NacActivity navbar

        //BottomNavigationView navigation = findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }//onCreate method

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.navigation_main:
                    Intent intentNav = new Intent(AboutActivity.this,NavActivity.class);
                    startActivity(intentNav);
                    return true;
                case R.id.navigation_map:
                    Intent intentMap = new Intent(AboutActivity.this, MapsActivity.class);
                    startActivity(intentMap);
                    return true;
                case R.id.navigation_account:
                    Intent intentAccount = new Intent(AboutActivity.this,AccountActivity.class);
                    Intent intentGet = getIntent();
                    String username = intentGet.getStringExtra("USERNAME");
                    intentAccount.putExtra("NAME",username);
                    intentAccount.putExtra("AGE",age);
                    intentAccount.putExtra("POINTS",points);
                    intentAccount.putExtra("GENDER",gender);
                    startActivity(intentAccount);
                    return true;
                case R.id.navigation_about:
                    Toast.makeText(getApplicationContext(),"You are already on the about page",Toast.LENGTH_SHORT).show();
            }//switch
            return false;
        }//onNavigationItemSelected bool
    };//OnNavigationItemSelectedListener*/

}//AboutActivity class