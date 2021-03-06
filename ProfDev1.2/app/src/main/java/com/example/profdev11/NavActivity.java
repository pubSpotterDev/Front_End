package com.example.profdev11;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NavActivity extends AppCompatActivity {

     TextView tvGreeting,tvPoints,tvPubLabel;

     String [] Name;
     String [] Street_Name;
     String [] Postcode;
     int points,id;
     String email, dob, gender, username, password;

    ArrayList<Pub> allPubs = new ArrayList<>();
    ArrayList<Pub> yourPubs = new ArrayList<>();
    List<Map<String, String>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        tvGreeting = findViewById(R.id.tvGreeting);
        tvPoints = findViewById(R.id.tvPoints);
        tvPubLabel = findViewById(R.id.tvPubLabel);
        //pubList = findViewById(R.id.lsPubs);

        if( getIntent().getExtras() != null)
        {
            Intent intent = getIntent();
            if(intent.hasExtra("USER")) {
                User user = (User) intent.getSerializableExtra("USER");
                username = user.getName();
                points = user.getPoints();
                email = user.getEmail();
                dob = user.getDob();
                gender = user.getGender();
                password = user.getPassword();
                id = user.getId();
            }
            //String action = getIntent().getAction();
//            if(intent.hasExtra("MAKE"))
//            {
//                String pubName = intent.getStringExtra("PUB");
//                for(int i=0; i<allPubs.size();i++){
//                    if(pubName == allPubs.get(i).getName()){
//                        Pub n = new Pub(allPubs.get(i).getPub_id(),allPubs.get(i).getName(),allPubs.get(i).getStreet_Name(),allPubs.get(i).getPostCode());
//                        yourPubs.add(n);
//                    }
//                }
//            }
            if (intent.hasExtra("ID"))
            {
                username = intent.getStringExtra("USERNAME");
                gender = intent.getStringExtra("GENDER");
                points = intent.getIntExtra("POINTS",60);
                dob = intent.getStringExtra("DOB");
                password = intent.getStringExtra("PASSWORD");
                id = intent.getIntExtra("ID",10);
                email = intent.getStringExtra("EMAIL");
            }
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        tvGreeting.setText("Hello "+username);
        tvPoints.setText("You have "+points+" points");

        tvPubLabel.setText(("You are near the following pubs: "));
        //Making a http call
        HttpURLConnection urlConnection;
        InputStream in = null;
        try {
            // the url we wish to connect to
            URL url = new URL("http://10.0.2.2:8010/pubspotter/api");
            // open the connection to the specified URL
            urlConnection = (HttpURLConnection) url.openConnection();
            // get the response from the server in an input stream
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // covert the input stream to a string
        String response = convertStreamToString(in);
        // print the response to android monitor/log cat
        System.out.println("Server response = " + response);

        try {
            // declare a new json array and pass it the string response from the server
            // this will convert the string into a JSON array which we can then iterate
            // over using a loop
            JSONArray jsonArray = new JSONArray(response);
            // instantiate the cheeseNames array and set the size
            // to the amount of cheese object returned by the server
            Name = new String[jsonArray.length()];
            Street_Name = new String[jsonArray.length()];
            Postcode = new String[jsonArray.length()];

            // use a for loop to iterate over the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                // the following line of code will get the name of the cheese from the
                // current JSON object and store it in a string variable called name
                Integer pub_id = jsonArray.getJSONObject(i).getInt("pub_id");
                String name = jsonArray.getJSONObject(i).get("name").toString();
                String streetname = jsonArray.getJSONObject(i).get("streetname").toString();
                String postcode = jsonArray.getJSONObject(i).get("postcode").toString();

                // print the name to log cat
                System.out.print("ID = " + pub_id);
                System.out.print("Name = " + name);
                System.out.println("Street Name = " + streetname);
                System.out.println("Postcode = " + postcode);

                // add the name of the current vehicles to the vehicleNames array

                Name[i] = name;
                Street_Name[i] = streetname;
                Postcode[i] = postcode;

                String NS = String.format("%s %s",name, streetname);

                // print the name to log cat
                System.out.println("Name = " + name);

                Pub p = new Pub(pub_id,name,streetname,postcode);
                allPubs.add(p);
                if(points!=0) {
                    if (i % 4 == 0) {
                        yourPubs.add(p);
                    }
                }

                //Adapter Data For List View
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //yourPubs.add(allPubs.get(1));
        //System.out.print(allPubs.get(1).toString());
    }

    public String convertStreamToString(InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    Intent intentMap = new Intent(NavActivity.this, MapsActivity.class);
                    intentMap.putExtra("POINTS",points);
                    intentMap.putExtra("GENDER",gender);
                    intentMap.putExtra("DOB",dob);
                    intentMap.putExtra("EMAIL",email);
                    intentMap.putExtra("USERNAME",username);
                    intentMap.putExtra("PASSWORD",password);
                    intentMap.putExtra("ID",id);
                    startActivity(intentMap);
                    return true;
                case R.id.navigation_account:
                    Intent intentAccount = new Intent(NavActivity.this, AccountActivity.class);
                    intentAccount.putExtra("POINTS",points);
                    intentAccount.putExtra("GENDER",gender);
                    intentAccount.putExtra("DOB",dob);
                    intentAccount.putExtra("EMAIL",email);
                    intentAccount.putExtra("USERNAME",username);
                    Bundle b = new Bundle();
                    b.putSerializable("ARRAYLIST",yourPubs);
                    intentAccount.putExtra("BUNDLE",b);
                    startActivity(intentAccount);
                    return true;
                case R.id.navigation_about:
                    Intent intentAbout = new Intent(NavActivity.this,AboutActivity.class);
                    startActivity(intentAbout);
                    return true;
            }//switch
            return false;
        }
    };//OnNavigationItemSelectedListener
}
