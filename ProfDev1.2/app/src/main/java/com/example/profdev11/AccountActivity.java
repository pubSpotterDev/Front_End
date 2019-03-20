package com.example.profdev11;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {

    TextView tvName, tvAge, tvPoints, tvGender, tvListLabel;
    ListView lsPubs;

    //String name, postcode, streetname;

    //ArrayList<Pub> yourPubs = new ArrayList<>();
    List<Map<String, String>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tvName = findViewById(R.id.tvName);
        tvAge =  findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvPoints = findViewById(R.id.tvPoints);
        tvListLabel = findViewById(R.id.tvListLabel);
        //lsPubs = findViewById(R.id.lsPubs);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("BUNDLE");
        ArrayList<Pub> yourPubs = new ArrayList<Pub>();

        if(b.containsKey("ARRAYLIST")) {
            yourPubs = (ArrayList<Pub>) b.getSerializable("ARRAYLIST");
        }

        String username = intent.getStringExtra("USERNAME");
        String gender = intent.getStringExtra("GENDER");
        int points = intent.getIntExtra("POINTS",10);
        String age = intent.getStringExtra("DOB");



        tvName.setText("Hey "+username);
        tvAge.setText("Your date of birth is "+age);
        tvPoints.setText("You have "+points+" points and are level "+(points/10));

        tvGender.setText("Your gender is :"+gender);
        tvListLabel.setText("These are the pubs you have visited so far: ");

        tvGender.setText("Your gender is: "+gender);

        for(int i = 0; i<yourPubs.size();i++) {

            int pub_id = yourPubs.get(i).getPub_id();
            String name = yourPubs.get(i).getName();
            String streetname = yourPubs.get(i).getStreet_Name();
            String postcode = yourPubs.get(i).getPostCode();

            String NS = String.format("%s %s", name, streetname);

            Map<String, String> datum = new HashMap<>(2);
            datum.put("Name_Street", NS);
            datum.put("Postcode", postcode);
            data.add(datum);

            final ListView PubList = findViewById(R.id.lsPubs);
            SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, new String[]{"Name_Street", "Postcode"}, new int[]{android.R.id.text1, android.R.id.text2}) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView=(TextView) view.findViewById(android.R.id.text1);
                    TextView textView2=(TextView) view.findViewById(android.R.id.text2);
                    textView.setTextColor(Color.WHITE);
                    textView2.setTextColor(Color.WHITE);
                    return view;
                }
            };
            PubList.setAdapter(adapter);
        }

    }//onCreate method

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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
    };//OnNavigationItemSelectedListener*/
}