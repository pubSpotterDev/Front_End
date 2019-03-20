package com.example.profdev11;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class CheckActivity extends AppCompatActivity {

    EditText pName, pStreetname, pPostcode;
    Button checkIn;

    ArrayList<Pub> yourPubs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        //Intent intent = getIntent();

        final TextView checkpName = findViewById(R.id.checkPname);
        final TextView checkpStreetname = findViewById(R.id.checkPstreetname);
        final TextView checkpPostcode = findViewById(R.id.checkPpostcode);
        checkIn = findViewById(R.id.btnCheck);

        Intent intentCheckIn = getIntent();
        final Float userLatitude = intentCheckIn.getFloatExtra("userLatitude", 0);
        final Float userLongitude = intentCheckIn.getFloatExtra("userLongitude", 0);
        final LatLng userLocation = new LatLng(userLatitude, userLongitude);

        final HashMap<String, String> params = new HashMap<>();

        final String pId = "10";
        final TextView pName = findViewById(R.id.etPname);
        final TextView pStreetname = findViewById(R.id.etPstreetname);
        final TextView pPostcode = findViewById(R.id.etPpostcode);

        final String username = intentCheckIn.getStringExtra("USERNAME");
        final String gender = intentCheckIn.getStringExtra("GENDER");
        final String dob = intentCheckIn.getStringExtra("DOB");
        int id = intentCheckIn.getIntExtra("ID",10);
        final String password = intentCheckIn.getStringExtra("PASSWORD");
        final String email = intentCheckIn.getStringExtra("EMAIL");
        System.out.println("id"+id);
        final String ID = Integer.toString(id);
        int points = intentCheckIn.getIntExtra("POINTS",5);
        System.out.println("points"+points);
        System.out.println("email"+email);


        //final String id = Integer.toString(intent.getIntExtra("ID",10));
        points = points +10;
        final int points3 = points;
        final String points2 =(Integer.toString(points));

        //final HashMap<String, String> params = new HashMap<>();
        final HashMap<String, String> params1 = new HashMap<>();

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.put("name", checkpName.getText().toString());
                params.put("streetname", checkpStreetname.getText().toString());
                params.put("postcode", checkpPostcode.getText().toString());
                String url = "http://10.0.2.2:8010/pubspotter/api";

                params1.put("points" ,points2);
                params1.put("name",username);
                params1.put("email",email);
                params1.put("gender",gender);
                params1.put("dob",dob);
                params1.put("password",password);
                params1.put("id",ID);

                Intent intent = new Intent(CheckActivity.this, NavActivity.class);

                intent.putExtra("MAKE","MAKE");
                intent.putExtra("USERNAME",username);
                intent.putExtra("EMAIL",email);
                intent.putExtra("DOB",dob);
                intent.putExtra("GENDER",gender);
                intent.putExtra("PASSWORD",password);
                intent.putExtra("ID",ID);
                //int points = intent.getIntExtra("POINTS",10);
                intent.putExtra("POINTS",points3);

                String url2 = "http://10.0.2.2:8010/pubspotter/userapi";
                PerformPutCall(url2,params1);

                CapturePub(url, params);
                checkPubCoordinates(checkpName, checkpStreetname, checkpPostcode, userLatitude, userLongitude, userLocation);
                String pubName = checkpName.getText().toString();

                intent.putExtra("PUB",pubName);
                startActivity(intent);
            }
        });
    }//end of oncreate

    public String PerformPutCall(String requestURL, HashMap<String, String> putDataParams) {
        URL url2;
        String response = "";
        try {
            url2 = new URL(requestURL);
            //Create the connection object
            HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //Write/send/POST dara to the connection using output stream and buffered writer
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            //Write/send/Post key/value data (url encoded) to the server
            System.out.println(getPutDataString(putDataParams));
            writer.write(getPutDataString(putDataParams));

            //clear the writer
            writer.flush();
            writer.close();

            //close the output stream
            os.close();

            //get the server response code to determine what to do next (ie success/error)
            int responseCode = conn.getResponseCode();
            System.out.println("Response code = " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Intent Return = new Intent(CheckActivity.this, MainActivity.class);
                CheckActivity.this.startActivity(Return);

                Toast.makeText(this, "Points Updated!", Toast.LENGTH_LONG).show();
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                Toast.makeText(this, "Error failed to update Points", Toast.LENGTH_LONG).show();
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response = " + response);
        return response;
    }

    private String getPutDataString(HashMap<String, String> params1) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params1.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");


            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }


    //This method takes the address details input by the user and uses Geocoder class
    //to retrieve the coordinates of the location
    public LatLng checkPubCoordinates(TextView checkpName,TextView checkpStreetname, TextView checkpPostcode, float userLatitude, float userLongitude, LatLng userLocation) {

        float latitude;
        float longitude;
        LatLng tempLocation = new LatLng(0.0, 0.0);
        final String address = "" + checkpName.getText().toString() + checkpStreetname.getText().toString() + checkpPostcode.getText().toString();

        List<Address> geocodeMatches = null;

        try {
            geocodeMatches =
                    new Geocoder(this).getFromLocationName(
                            "" + address, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (geocodeMatches != null) {
            //if (!geocodeMatches.isEmpty()) {
                latitude = (float) geocodeMatches.get(0).getLatitude();
                longitude = (float) geocodeMatches.get(0).getLongitude();
                LatLng pubLocation = new LatLng(latitude, longitude);
                Toast.makeText(getApplicationContext(), "" +pubLocation, Toast.LENGTH_LONG).show();
                float differenceLat = (Math.abs(latitude) - Math.abs(userLatitude));
                float differenceLong = (Math.abs(longitude) - Math.abs(userLongitude));
              //  userLocation = pubLocation;
             //   St Augustus, Grosvenor St, M15 6BW
                // Business School, Oxford Road, M15 6BH


                if (differenceLat <= 0.05 && differenceLong <= 0.05) {
                    boolean pubChecked = true;
                    Toast.makeText(getApplicationContext(), "YOU JUST CHECKED IN!", Toast.LENGTH_LONG).show();
                    Intent intentBackToAccount = new Intent(CheckActivity.this, AccountActivity.class);
                    intentBackToAccount.putExtra("pubChecked", true);
                    startActivity(intentBackToAccount);
                    return pubLocation;
                } else {
                    boolean pubChecked = false;
                    Toast.makeText(getApplicationContext(), "Are you sure? Sorry, we couldn't find a pub in your location", Toast.LENGTH_LONG).show();
                    return userLocation;
                }
        }
        else {
            Toast.makeText(getApplicationContext(), "Sorry, we couldn't find a pub in your location", Toast.LENGTH_LONG).show();
            return tempLocation;
        }
    }

    public String CapturePub(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            //Create the connection object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);

            //Write/send/POST dara to the connection using output stream and buffered writer
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            //Write/send/Post key/value data (url encoded) to the server
                writer.write(getPostDataString(postDataParams));

            //clear the writer
                writer.flush();
                writer.close();

            //close the output stream
                os.close();

            //get the server response code to determine what to do next (ie success/error)
            int responseCode = conn.getResponseCode();
            System.out.println("Response code = " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Intent Return = new Intent(CheckActivity.this, MainActivity.class);
                CheckActivity.this.startActivity(Return);

                Toast.makeText(this, "Pub added", Toast.LENGTH_LONG).show();
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                Toast.makeText(this, "Error failed to save Pub", Toast.LENGTH_LONG).show();
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response = " + response);
        return response;
    }

    //This method converts a hashmap to a URL query string of key/value pairs
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }


}
