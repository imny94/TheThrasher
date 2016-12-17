package com.lejit.thesmartbin_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;


public class ManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        FloatingActionButton managementButton=(FloatingActionButton)findViewById(R.id.fab_arc_menu_2);
        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent management =new Intent(ManagementActivity.this,ManagementActivity.class);
                ManagementActivity.this.startActivity(management);
            }
        });
        FloatingActionButton logout=(FloatingActionButton)findViewById(R.id.fab_arc_menu_1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutCall=new Intent(ManagementActivity.this,MainActivity.class);
                ManagementActivity.this.startActivity(logoutCall);
            }
        });
        FloatingActionButton userProfile=(FloatingActionButton)findViewById(R.id.fab_arc_menu_3);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userpro=new Intent(ManagementActivity.this,Main2Activity.class);
                ManagementActivity.this.startActivity(userpro);
            }
        });
        FloatingActionButton about=(FloatingActionButton)findViewById(R.id.fab_arc_menu_4);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutPage=new Intent(ManagementActivity.this,About.class);
                ManagementActivity.this.startActivity(aboutPage);
            }
        });
    }
    public void onDestroy() {

        super.onDestroy();

    }
}
