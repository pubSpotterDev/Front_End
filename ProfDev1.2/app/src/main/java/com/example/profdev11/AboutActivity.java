package com.example.profdev11;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    int points = 0;
    int age = 21;
    String gender = "male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Intent intent = getIntent();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }//onCreate method

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.navigation_map:
                    Intent intent = new Intent(AboutActivity.this, MapsActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_account:
                        Intent intent2 = new Intent(AboutActivity.this,AccountActivity.class);
                        Intent intent4 = getIntent();
                        String username = intent4.getStringExtra("USERNAME");
                        intent2.putExtra("NAME",username);
                        intent2.putExtra("AGE",age);
                        intent2.putExtra("POINTS",points);
                        intent2.putExtra("GENDER",gender);
                        startActivity(intent2);
                        return true;
            }//switch
            return false;
        }//onNavigationItemSelected bool
    };//OnNavigationItemSelectedListener


}//AboutActivity class