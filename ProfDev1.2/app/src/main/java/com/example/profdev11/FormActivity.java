package com.example.profdev11;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public class FormActivity extends AppCompatActivity {

    EditText pName, pStreetname, pPostcode;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);



        final TextView pId = findViewById(R.id.etPid);
        final TextView pName = findViewById(R.id.etPname);
        final TextView pStreetname = findViewById(R.id.etPstreetname);
        final TextView pPostcode = findViewById(R.id.etPpostcode);
        add = findViewById(R.id.btnadd);

        final HashMap<String, String> params = new HashMap<>();
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                params.put("pub_id", pId.getText().toString());
                params.put("name", pName.getText().toString());
                params.put("streetname", pStreetname.getText().toString());
                params.put("postcode", pPostcode.getText().toString());


                String url = "http://10.0.2.2:8010/pubspotter/api";
                PerformPostCall(url, params);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPubCoordinates(pName, pStreetname, pPostcode);
            }
        });
    }

    public String PerformPostCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            //Create the connection object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

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
                Intent Return = new Intent(FormActivity.this, MainActivity.class);
                FormActivity.this.startActivity(Return);

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
            if (first) {
                first = false;
            } else {
                result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        }
        return result.toString();
    }

    //This method takes the address details input by the user and uses Geocoder class
    //to retrieve the coordinates of the location
    public LatLng newPubCoordinates(TextView pName,TextView pStreetname, TextView pPostcode) {

        float latitude;
        float longitude;
        LatLng tempLocation = new LatLng(0.0, 0.0);
        final String address = "" + pName.getText().toString() + pStreetname.getText().toString() + pPostcode.getText().toString();

        List<Address> geocodeMatches = null;

        try {
            geocodeMatches =
                    new Geocoder(this).getFromLocationName(
                            "" + address , 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (!geocodeMatches.isEmpty()) {
            latitude = (float) geocodeMatches.get(0).getLatitude();
            longitude = (float) geocodeMatches.get(0).getLongitude();
            LatLng pubLocation = new LatLng(latitude, longitude);
            Toast.makeText(getApplicationContext(), " " + pubLocation , Toast.LENGTH_LONG).show();
            return pubLocation;

        }
        else {
            Toast.makeText(getApplicationContext(), "No matches" , Toast.LENGTH_LONG).show();
            return tempLocation;
        }
    }

}