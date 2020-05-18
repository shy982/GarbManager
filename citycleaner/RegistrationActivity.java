package com.example.citycleaner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        db=new DatabaseHelper(this);
        mTextUsername=findViewById(R.id.edittext_username);
        mTextPassword=findViewById(R.id.edittext_password);
        mTextCnfPassword=findViewById(R.id.edittext_cnf_password);
        mButtonRegister=findViewById(R.id.button_register);
        mTextViewLogin=findViewById(R.id.textview_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent=new Intent(RegistrationActivity.this,HomeActivity.class);
                startActivity(LoginIntent);
            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=mTextUsername.getText().toString().trim();
                String pwd=mTextPassword.getText().toString().trim();
                String cnf_pwd=mTextCnfPassword.getText().toString().trim();
                if(pwd.equals(cnf_pwd)){
                    long val=db.addUser(user,pwd);
                    if(val>0) {
                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegistrationActivity.this, HomeActivity.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegistrationActivity.this,"Password dont match",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
