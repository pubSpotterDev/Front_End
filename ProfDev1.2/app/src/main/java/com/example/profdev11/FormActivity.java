package com.example.profdev11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FormActivity extends AppCompatActivity {

    EditText pName, pAddress;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        pName = findViewById(R.id.etPname);
        pAddress = findViewById(R.id.etPaddress);
        add = findViewById(R.id.btnadd);


    }
}
