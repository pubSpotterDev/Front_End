package com.example.profdev11;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etname;
    EditText etpassword;
    TextView info;
    Button login;
    Button newAccount;
    int counter = 5;

    String [] Name;
    String [] Gender;

    ArrayList<Pub> allUsers = new ArrayList<>();
    List<Map<String, String>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        etname = (EditText) findViewById(R.id.etPname);
        etpassword = (EditText) findViewById(R.id.etPassword);
        info = (TextView) findViewById(R.id.tvInfo);
        login = (Button) findViewById(R.id.btnLogin);
        newAccount = findViewById(R.id.btnAccount);

        etpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        info.setText("Remaining attempts: "+String.valueOf(counter));


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(etname.getText().toString(),etpassword.getText().toString());
            }
        });//login listener

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, newAccountActivity.class);
                startActivity(intent);
            }
        });//newAccount listener

        //Making a http call
        /*HttpURLConnection urlConnection;
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
            Gender = new String[jsonArray.length()];
            //Postcode = new String[jsonArray.length()];

            // use a for loop to iterate over the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                // the following line of code will get the name of the cheese from the
                // current JSON object and store it in a string variable called name
                Integer pub_id = jsonArray.getJSONObject(i).getInt("pub_id");
                String name = jsonArray.getJSONObject(i).get("name").toString();
                String streetname = jsonArray.getJSONObject(i).get("streetname").toString();
                String postcode = jsonArray.getJSONObject(i).get("postcode").toString();

                // print the name to log cat
                System.out.print("ID = " + id);
                System.out.print("password = " + gender);
                //System.out.println("Street Name = " + streetname);
                //System.out.println("Postcode = " + postcode);

                // add the name of the current vehicles to the vehicleNames array

                Name[i] = name;
                //Street_Name[i] = streetname;
                //Postcode[i] = postcode;

                String NS = String.format("%s %s",name, streetname);

                // print the name to log cat
                System.out.println("Name = " + name);

                User p = new User(id,email,name,dob,gender,points);
                allUsers.add(p);

                //Adapter Data For List View
                Map<String, String> datum = new HashMap<>(2);
                datum.put("Name_StreetN", NS);
                datum.put("Postcode", postcode);
                data.add(datum);


                final ListView PubList = findViewById(R.id.lsPubs);
                SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, new String[]{"Name_Street", "Postcode"}, new int[]{android.R.id.text1, android.R.id.text2}) {
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        return view;
                    }
                };
                PubList.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }//onCreate method

    void validate(String username, String userpassword){

        if(username.equals("admin") && userpassword.equals("kings123")){
            Intent intent = new Intent(MainActivity.this, NavActivity.class);
            intent.putExtra("USERNAME",username);
            startActivity(intent);
        }else{
            counter--;
            info.setText("Remaining attempts: "+String.valueOf(counter));
            Toast.makeText(getApplicationContext(),"Username or Password is wrong",Toast.LENGTH_LONG).show();

            if(counter==0){
                login.setEnabled(false);
            }//inner if
        }//outer if/else
    }//validate method

    public String convertStreamToString(InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}//MainActivity