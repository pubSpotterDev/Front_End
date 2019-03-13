package com.example.profdev11;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.design.widget.BottomNavigationView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final int REQUEST_LOCATION_PERMISSION = 1;

    //Hardcoded values to keep the navbar from breaking without dB integration
    int points = 0;
    int age = 21;
    String gender = "male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Construct a GeoDataClient.
       // mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
       // mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        //mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        /**
        //DEPRECATED - now integrated into top menu
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);
        inflater.inflate(R.menu.navigation,menu);
        return true;
    }//onCreateOptionsMenu method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
                //CHANGE THESE ACTIVITY POINTERS WHEN THE FORMS ARE MADE
            case R.id.add_pub:
                Intent intent = new Intent(MapsActivity.this, FormActivity.class);
                startActivity(intent);
                return true;
            case R.id.check_in_pub:
                Intent intent2 = new Intent(MapsActivity.this, CheckActivity.class);
                startActivity(intent2);
                return true;
            //NavBar
            case R.id.navigation_main:
                Intent intentNav = new Intent(MapsActivity.this,NavActivity.class);
                startActivity(intentNav);
                return true;
            case R.id.navigation_map:
                Toast.makeText(getApplicationContext(),"You are already on the map page",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation_account:
                Intent intentAccount = new Intent(MapsActivity.this,AccountActivity.class);
                Intent intentGet = getIntent();
                String username = intentGet.getStringExtra("USERNAME");
                intentAccount.putExtra("NAME",username);
                intentAccount.putExtra("AGE",age);
                intentAccount.putExtra("POINTS",points);
                intentAccount.putExtra("GENDER",gender);
                startActivity(intentAccount);
                return true;
            case R.id.navigation_about:
                Intent intentAbout = new Intent(MapsActivity.this,AboutActivity.class);
                startActivity(intentAbout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//switch
    }//onOptionsItemSelected method

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Home = new LatLng(53.470407, -2.239145);
        float Zoom = (float)15.00;

        //gets user location
        enableMyLocation();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Home, Zoom));

        //double currentLat = location.getLatitude();




        LatLng TheFootage = new LatLng(53.470, -2.236);
        LatLng TheTemple = new LatLng(53.475, -2.242);
        LatLng TheGasWorksBrewbar = new LatLng(53.473, -2.246);
        LatLng BeAtOne = new LatLng(53.482, -2.246);


        mMap.addMarker(new MarkerOptions()
                .position(TheFootage)
                .title("The Footage")
                .snippet("126 Grosvenor Street, Manchester, M1 7HL"));
        mMap.addMarker(new MarkerOptions()
                .position(TheTemple)
                .title("The Temple")
                .snippet("100 Great Bridgewater Street, Manchester, M1 5JW"));
        mMap.addMarker(new MarkerOptions()
                .position(TheGasWorksBrewbar)
                .title("The Gas Works Brewbar")
                .snippet("2 Tony Wilson Place, Manchester, M1 5WG"));
        mMap.addMarker(new MarkerOptions()
                .position(BeAtOne)
                .title("Be At One")
                .snippet("Barton Arcade, Deansgate, Manchester, M3 2BW"));



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                } else {
                    Toast.makeText(this,
                            "location_permission_denied",
                            Toast.LENGTH_SHORT).show();
                }//location permissions check
                break;
        }//switch
    }//onRequestPermissionsResult method

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
           // LatLng UserLocation = new LatLng();
            String TAG = "";
            Log.d(TAG, "getLocation: permissions granted");
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }//location access if/else
    }//enableMyLocation method

/**
    //NavBar DEPRECATED - now integrated into top menu
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.navigation_main:
                    Intent intentNav = new Intent(MapsActivity.this,NavActivity.class);
                    startActivity(intentNav);
                    return true;
                case R.id.navigation_map:
                    Toast.makeText(getApplicationContext(),"You are already on the map page",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_account:
                    Intent intentAccount = new Intent(MapsActivity.this,AccountActivity.class);
                    Intent intentGet = getIntent();
                    String username = intentGet.getStringExtra("USERNAME");
                    intentAccount.putExtra("NAME",username);
                    intentAccount.putExtra("AGE",age);
                    intentAccount.putExtra("POINTS",points);
                    intentAccount.putExtra("GENDER",gender);
                    startActivity(intentAccount);
                    return true;
                case R.id.navigation_about:
                    Intent intentAbout = new Intent(MapsActivity.this,AboutActivity.class);
                    startActivity(intentAbout);
                    return true;
            }//switch
            return false;
        }//onNavigationItemSelected bool
    };//OnNavigationItemSelectedListener
*/
}//MapsActivity class
