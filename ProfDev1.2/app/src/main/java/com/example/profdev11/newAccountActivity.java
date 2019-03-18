package com.example.profdev11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class newAccountActivity extends AppCompatActivity {

    EditText etID, etEmail, etPassword, etPassword2, etName, etGender, etDOB, etPoints;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        final TextView etID = findViewById(R.id.etID);
        final TextView etEmail = findViewById(R.id.etEmail);
        final TextView etName = findViewById(R.id.etName);
        final TextView etGender = findViewById(R.id.etGender);
        final TextView etDOB = findViewById(R.id.etDOB);
        final TextView etPoints = findViewById(R.id.etPoints);
        final TextView etPassword = findViewById(R.id.etPassword);
        final TextView etPassword2 = findViewById(R.id.etPassword2);
        btnCreate = findViewById(R.id.btnCreate);

        final HashMap<String, String> params = new HashMap<>();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                params.put("id", etID.getText().toString());
                params.put("email", etEmail.getText().toString());
                params.put("name", etName.getText().toString());
                params.put("gender", etGender.getText().toString());
                params.put("dob", etDOB.getText().toString());
                params.put("points", etPoints.getText().toString());
                params.put("password", etPassword.getText().toString());


                String url = "http://10.0.2.2:8010/pubspotter/userapi";
                PerformPostCall(url, params);
////                newPubCoordinates(pName, pStreetname, pPostcode);

                validate(etPassword.getText().toString(),etPassword2.getText().toString(),etEmail.getText().toString());
            }
        });//create account click listener
    }//onCreate method

    void validate(String password, String password2, String email){

        if(password.length()<7){
            Toast.makeText(getApplicationContext(),"Password is not long enough",Toast.LENGTH_LONG).show();
        }//if password is not long enough
        else {
            if (password.equals(password2)) {
                Intent intent = new Intent(newAccountActivity.this, NavActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();

            }//else passwords don't match
        }//else if password is long enough
    }//validate method
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
                Intent Return = new Intent(newAccountActivity.this, MainActivity.class);
                newAccountActivity.this.startActivity(Return);

                Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                Toast.makeText(this, "Error failed to save User", Toast.LENGTH_LONG).show();
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
}//newAccountActivity class