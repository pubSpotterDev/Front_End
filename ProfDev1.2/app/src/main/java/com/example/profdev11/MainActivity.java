package com.example.profdev11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText password;
    TextView info;
    Button login;
    int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.etName);
        password = (EditText) findViewById(R.id.etPassword);
        info = (TextView) findViewById(R.id.tvInfo);
        login = (Button) findViewById(R.id.btnLogin);

        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        info.setText("Remaining attempts: "+String.valueOf(counter));


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(),password.getText().toString());
            }
        });

    }

    void validate(String username, String userpassword){

        if(username.equals("admin") && userpassword.equals("kings123")){
            Intent intent = new Intent(MainActivity.this, NavActivity.class);
            startActivity(intent);
        }else{
            counter--;
            info.setText("Remaining attempts: "+String.valueOf(counter));

            if(counter==0){
                login.setEnabled(false);
            }
        }
    }
}
