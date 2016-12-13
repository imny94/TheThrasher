package com.lejit.thesmartbin_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private Button groundbutton;
    private EditText ID;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        groundbutton=(Button)findViewById(R.id.groundstaff);

        groundbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ground =new Intent(MainActivity.this,Main2Activity.class);
                MainActivity.this.startActivity(ground);
            }
        });
    }
    public void onDestroy() {

        super.onDestroy();

    }
}

