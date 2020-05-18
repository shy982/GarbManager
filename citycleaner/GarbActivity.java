package com.example.citycleaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class GarbActivity extends AppCompatActivity {
    public EditText name;
    public Button query_button;
    public TextView result_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garb);

        name=findViewById(R.id.name);
        name=findViewById(R.id.name);
        query_button=findViewById(R.id.query_button);
        result_address=findViewById(R.id.result);
        query_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                String n=name.getText().toString();
                String address=databaseAccess.getAddress(n);
                result_address.setText(address);
                databaseAccess.close();

            }
        });
    }
}
