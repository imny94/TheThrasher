package com.example;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FirebaseDataGetter {

    public FirebaseDataGetter() {
        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(
                            new FileInputStream("smartbin-16031-firebase-adminsdk-0kerw-7015e02a48.json"))
                    .setDatabaseUrl("https://smartbin-16031.firebaseio.com")
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Initialize the default app
        FirebaseApp defaultApp = FirebaseApp.initializeApp(options);

        System.out.println(defaultApp.getName());  // "[DEFAULT]"

// Retrieve services by passing the defaultApp variable...
        FirebaseAuth defaultAuth = FirebaseAuth.getInstance(defaultApp);
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(defaultApp);

        DatabaseReference lastTimeStampRef = FirebaseDatabase
                                                .getInstance()
                                                .getReference("Bin Last Time Stamp");
        lastTimeStampRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // As an admin, the app has access to read and write all data, regardless of Security Rules
        DatabaseReference RawDataRef = FirebaseDatabase
                .getInstance()
                .getReference("Raw Data");
        RawDataRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BinRawData rawDataBinLocation = dataSnapshot.getValue(BinRawData.class);
                System.out.println(rawDataBinLocation);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static class BinRawData{
//        Map<String,Map<String,Map<String,Double>>> binLocations;
        BinLocationRawData binData;

        BinRawData(BinLocationRawData data){
            binData = data;
        }
    }

    public static class BinLocationRawData{
        BinLocationTimeRawData indvTimeDepData;

        BinLocationRawData(BinLocationTimeRawData data){
            indvTimeDepData = data;
        }
    }

    public static class BinLocationTimeRawData{
        public Double pressure;
        public Double sonar1;
        public Double sonar2;
        public Double sonar3;
        public Double sonar4;
        public Double sonar5;
        public Double sonar6;

        BinLocationTimeRawData(Double p, Double s1, Double s2, Double s3, Double s4, Double s5, Double s6){
            pressure = p;
            sonar1 = s1;
            sonar2 = s2;
            sonar3 = s3;
            sonar4 = s4;
            sonar5 = s5;
            sonar6 = s6;
        }
    }
}


