package com.lejit.thesmartbin_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button managebutton;
    private Button groundbutton;
    private EditText ID;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groundbutton=(Button)findViewById(R.id.groundstaff);
        managebutton=(Button)findViewById(R.id.managementbutton);
        managebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manage =new Intent(MainActivity.this,ManagementActivity.class);
                MainActivity.this.startActivity(manage);
            }
        });
        groundbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ground =new Intent(MainActivity.this,Main2Activity.class);
                MainActivity.this.startActivity(ground);
            }
        });
    }
}

