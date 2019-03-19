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
    int [] Points;
    String [] Name;
    String [] Gender;
    String [] Email;
    String [] Dob;
    String [] Password;

    ArrayList<User> allUsers = new ArrayList<>();
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
        HttpURLConnection urlConnection;
        InputStream in = null;
        try {
            // the url we wish to connect to
            URL url = new URL("http://10.0.2.2:8010/pubspotter/userapi");
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
            // instantiate the Users array and set the size
            // to the amount of user object returned by the server
            Name = new String[jsonArray.length()];
            Gender = new String[jsonArray.length()];
            Email = new String[jsonArray.length()];
            Dob = new String[jsonArray.length()];
            Password = new String[jsonArray.length()];
            Points = new int[jsonArray.length()];


            // use a for loop to iterate over the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                // the following line of code will get the name of the user from the
                // current JSON object and store it in a string variable called name
                Integer id = jsonArray.getJSONObject(i).getInt("id");
                Integer points = jsonArray.getJSONObject(i).getInt("points");
                String name = jsonArray.getJSONObject(i).get("name").toString();
                String email = jsonArray.getJSONObject(i).get("email").toString();
                String dob = jsonArray.getJSONObject(i).get("dob").toString();
                String gender = jsonArray.getJSONObject(i).get("gender").toString();
                String password = jsonArray.getJSONObject(i).get("password").toString();

                // print the name to log cat
                System.out.println("ID = " + id);
                System.out.println("email = " + email);
                System.out.println("name = " + name);
                System.out.println("dob = " + dob);
                System.out.println("gender = " + gender);
                System.out.println("points = " + points);
                System.out.println("password = " + password);

                // add the name of the current vehicles to the array

                //Id[i] = id;
                Email[i] = email;
                Name[i] = name;
                Dob[i] = dob;
                Gender[i] = gender;
                Points[i] = points;
                Password[i] = password;


                String NS = String.format("%s %s",name, password);

                // print the name to log cat
                System.out.println("Name = " + name);

                User p = new User(id,email,name,dob,gender,points,password);
                allUsers.add(p);

                //Adapter Data For List View

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }//onCreate method

     boolean validate(String username, String userpassword){

        //JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < allUsers.size(); i++) {
            if((username.equals(allUsers.get(i).getEmail())) && (userpassword.equals(allUsers.get(i).getPassword()))){

                Intent intent = new Intent(MainActivity.this, NavActivity.class);
                //intent.putExtra("USERNAME",username);
                intent.putExtra("USER",allUsers.get(i));
                startActivity(intent);
                return true;
            }
        }
         counter--;
         info.setText("Remaining attempts: "+String.valueOf(counter));
         Toast.makeText(getApplicationContext(),"Username or Password is wrong",Toast.LENGTH_LONG).show();
         if(counter==0){
             login.setEnabled(false);
         }//inner if

        return false;
    }//validate method

    public String convertStreamToString(InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}//MainActivity