package com.example.profdev11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class newAccountActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etPassword2;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(etPassword.getText().toString(),etPassword2.getText().toString());
            }
        });

    }


    void validate(String password, String password2){

        if(password.equals(password2)){
            Intent intent = new Intent(newAccountActivity.this, NavActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Passwords don't match",Toast.LENGTH_LONG).show();

        }
    }
}
