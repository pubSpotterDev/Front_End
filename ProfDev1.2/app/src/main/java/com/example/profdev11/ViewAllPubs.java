package com.example.profdev11;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class ViewAllPubs extends AppCompatActivity {

    String [] Name;
    String [] Street_Name;
    String [] Postcode;

    ArrayList<Pub> allPubs = new ArrayList<>();
    List<Map<String, String>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_pubs);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
            // instantiate the pubNames array and set the size
            // to the amount of pub object returned by the server
            Name = new String[jsonArray.length()];
            Street_Name = new String[jsonArray.length()];
            Postcode = new String[jsonArray.length()];

            // use a for loop to iterate over the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                // the following line of code will get the name of the pub from the
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


                //Adapter Data For List View
                Map<String, String> datum = new HashMap<>(2);
                datum.put("Name_StreetN", NS);
                datum.put("Postcode", postcode);
                data.add(datum);


                final ListView PubList = findViewById(R.id.pubList);
                SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[]{"Name_Street", "Postcode"}, new int[]{android.R.id.text1, android.R.id.text2}) {
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        return view;
                    }
                };
                PubList.setAdapter(adapter);

//  Just trying something for the Check In activity - plz ignore for now
/*                Intent intentCheckPubs = new Intent(ViewAllPubs.this, CheckActivity.class);
                int
                intentCheckPubs.putExtra("name", name);
                intentCheckPubs.putExtra("streetname", streetname);
                intentCheckPubs.putExtra("postcode", postcode);
                startActivity(intentCheckPubs);*/


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String convertStreamToString(InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
