package com.lejit.thesmartbin_2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sa90.materialarcmenu.ArcMenu;
import com.sa90.materialarcmenu.StateChangeListener;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        FloatingActionButton managementButton=(FloatingActionButton)findViewById(R.id.fab_arc_menu_2);
        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent management =new Intent(About.this,ManagementActivity.class);
                About.this.startActivity(management);
            }
        });
        FloatingActionButton logout=(FloatingActionButton)findViewById(R.id.fab_arc_menu_1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutCall=new Intent(About.this,MainActivity.class);
                About.this.startActivity(logoutCall);
            }
        });
        FloatingActionButton userProfile=(FloatingActionButton)findViewById(R.id.fab_arc_menu_3);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userpro=new Intent(About.this,UserProfile.class);
                About.this.startActivity(userpro);
            }
        });
        FloatingActionButton about=(FloatingActionButton)findViewById(R.id.fab_arc_menu_4);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutPage=new Intent(About.this,About.class);
                About.this.startActivity(aboutPage);
            }
        });
    }
    public void onDestroy() {

        super.onDestroy();

    }
}
