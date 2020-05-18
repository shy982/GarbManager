package com.example.citycleaner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db =new DatabaseHelper(this);
        mTextUsername=findViewById(R.id.edittext_username);
        mTextPassword=findViewById(R.id.edittext_password);
        mButtonLogin=findViewById(R.id.button_login);
        mTextViewRegister=findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent=new Intent(HomeActivity.this,RegistrationActivity.class);
                startActivity(registerIntent);
            }
        });
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=mTextUsername.getText().toString().trim();
                String pwd=mTextPassword.getText().toString().trim();
                Boolean res=db.checkUser(user,pwd);
                if(res)
                {
                    Intent Garb=new Intent(HomeActivity.this,GarbActivity.class);
                    startActivity(Garb);
                }
                else
                {
                    Toast.makeText(HomeActivity.this,"Login Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
