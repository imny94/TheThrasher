package com.example;

/**
 * Created by nicholas on 14-Dec-16.
 */

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * Created by nicholas on 12-Dec-16.
 */

public class testClassifier {
    static Classifier ibk;

    private static Double classify(DynamicBinFilledClassifier testClass, Double weight, Double s1, Double s2, Double s3, Double s4, Double s5, Double s6 ){
        Instances newDataSet = testClass.createInstance(weight,s1,s2,s3,s4,s5,s6);
        Double pred;
        Double howFilled = 0.0;
        for(int i = 0; i<newDataSet.numInstances();i++){
            try {
                pred = ibk.classifyInstance(newDataSet.instance(i));
                if(pred==0.0){
                    howFilled = 1.00;
                }else if(pred==1.0){
                    howFilled = 0.75;
                }else if(pred==2.0){
                    howFilled = 0.50;
                }else if(pred==3.0){
                    howFilled = 0.25;
                }else if(pred==4.0){
                    howFilled = 0.00;
                }
                System.out.println("I predict ----------> You are ---------> " + howFilled + " filled");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return howFilled;
    }

    public static void main(String[]args){
        final DynamicBinFilledClassifier testClass = new DynamicBinFilledClassifier();
//        System.out.println("Creating model...");
//        ibk = testClass.createNewModel("sampleTest",500.0,0.5,0.5,0.5,0.5,0.5,0.5,"50pFilled");
        System.out.println("Updating model...");
        ibk = testClass.updateModel("sampleTest",1500.0,0.5,0.5,0.5,0.5,0.5,0.5,0.50);


        JSONObject allData = null;
        try {
            allData = FirebaseFetcher.readJsonFromFirebase("https://smartbin-16031.firebaseio.com/.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject latestTimeStamp = (JSONObject) allData.get("Bin Last Time Stamp");
        JSONArray binLocations = (JSONArray) allData.get("Bin Locations");
        JSONObject rawData = (JSONObject) allData.get("Raw Data");
        JSONObject staffClassification = (JSONObject) allData.get("Staff Classification");
//        System.out.println(staffClassification);
//        System.out.println("\n\n\n"+latestTimeStamp);
//        System.out.println(latestTimeStamp.getClass());
//        System.out.println("\n\n\n"+binLocations);
//        System.out.println(binLocations.getClass());
//        System.out.println("\n\n\n"+rawData);
//        System.out.println(rawData.getClass());

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(
                            new FileInputStream("firebaseclassifier/smartbin-16031-firebase-adminsdk-0kerw-7015e02a48.json"))
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

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference BinClassificationRef = database.getReference("Bin Classification");
        DatabaseReference BinClassificationRefForGraph = database.getReference("Bin Classification For Graph");

        Map<String,Map<String,String >> binClassification = new HashMap<String, Map<String, String>>();
        Map<String ,String > classificationMap = new HashMap<String ,String>();

        Map<String,Map<String,String>> classificationForGraph = new HashMap<String,Map<String,String>>();
        Map<String ,String> timeStampWithClassification = new HashMap<String ,String>();

//        users.put("alanisawesome", new User("June 23, 1912", "Alan Turing"));
//        users.put("gracehop", new User("December 9, 1906", "Grace Hopper"));
//
//        usersRef.setValue(users);

        for(int i=0;i<binLocations.length();i++){
            String location = (String) binLocations.get(i);
            JSONObject timeStamp = (JSONObject) latestTimeStamp.get(location);
            System.out.println(timeStamp);
            System.out.println(timeStamp.getClass());
            JSONObject binLoc = (JSONObject) rawData.get(location);
            JSONObject latestData = (JSONObject) binLoc.get(timeStamp.getString("lastTimeStamp"));
            Double s1 = latestData.getDouble("Sonar1");
            Double s2 = latestData.getDouble("Sonar2");
            Double s3 = latestData.getDouble("Sonar3");
            Double s4 = latestData.getDouble("Sonar4");
            Double s5 = latestData.getDouble("Sonar5");
            Double s6 = latestData.getDouble("Sonar6");
            Double weight = latestData.getDouble("pressure");

            System.out.println(latestData);
            String userClassification = (String) staffClassification.get(location);
            Double classification;
            if (userClassification.equals("N.A")){
                System.out.println("no user classification");
                classification = classify(testClass,weight,s1,s2,s3,s4,s5,s6);
            }
            else {
                System.out.println("Using user classification");
                classification = Double.parseDouble(userClassification);
                testClass.updateModel("sampleTest",weight,s1,s2,s3,s4,s5,s6,classification);
            }

            String stringClassification = classification.toString();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
            Date date = new Date();
            String formattedDate = dateFormat.format(date);

            timeStampWithClassification.put(formattedDate,stringClassification);
            classificationForGraph.put(location,timeStampWithClassification);
            System.out.println("adding bin classification for graph to firebase...");
            BinClassificationRefForGraph.setValue(classificationForGraph);
            classificationForGraph.clear();

            classificationMap.put("Classification",stringClassification);
            classificationMap.put("Time", formattedDate);
            binClassification.put(location,classificationMap);
            System.out.println(binClassification.toString());
            System.out.println("adding bin classification to firebase...");
            BinClassificationRef.setValue(binClassification);
            classificationMap.clear();
            binClassification.clear();


        }
////        Double sonar1 = newDataSet.get("sonar1");
//                Double sonar2 = newDataSet.get("sonar2");
//                Double sonar3 = newDataSet.get("sonar3");
//                Double sonar4 = newDataSet.get("sonar4");
//                Double sonar5 = newDataSet.get("sonar5");
//                Double sonar6 = newDataSet.get("sonar6");
//                Double weight = newDataSet.get("value");
//                if(newDataSet.containsKey("staffClassification")){
//                    Double staffClassification = newDataSet.get("staffClassification");
//                    ibk = testClass.updateModel("sampleTest",weight,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6,staffClassification);
//                    myFirebaseRef.child(currentKey).child("classification").setValue(staffClassification);
//                }
//                else {
//                    Double pred;
//                    Double howFilled = 0.0;
//                    Instances testingSet = testClass.createInstance(weight,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6);
//                    for(int i = 0; i<testingSet.numInstances();i++){
//                        try {
//                            pred = ibk.classifyInstance(testingSet.instance(i));
//                            if(pred==0.0){
//                                howFilled = 1.0;
//                            }else if(pred==1.0){
//                                howFilled = 0.75;
//                            }else if(pred==2.0){
//                                howFilled = 0.50;
//                            }else if(pred==3.0){
//                                howFilled = 0.25;
//                            }else if(pred==4.0){
//                                howFilled = 0.0;
//                            }
//                            System.out.println("I predict ----------> You are ---------> " + howFilled + " filled");
//                            myFirebaseRef.child(currentKey).child("classification").setValue(howFilled);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }

//        System.out.println("Testing creation of instance");
//        System.out.println(testClass.createInstance(500.0,0.5,0.5,0.5,0.5,0.5,0.5).toString());

//        final Firebase myFirebaseRef=new Firebase("https://smartbin-16031.firebaseio.com/");
//
//        myFirebaseRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String currentKey = dataSnapshot.getKey();
//                Map<String,Double> newDataSet = dataSnapshot.getValue(Map.class);
//                Double sonar1 = newDataSet.get("sonar1");
//                Double sonar2 = newDataSet.get("sonar2");
//                Double sonar3 = newDataSet.get("sonar3");
//                Double sonar4 = newDataSet.get("sonar4");
//                Double sonar5 = newDataSet.get("sonar5");
//                Double sonar6 = newDataSet.get("sonar6");
//                Double weight = newDataSet.get("value");
//                if(newDataSet.containsKey("staffClassification")){
//                    Double staffClassification = newDataSet.get("staffClassification");
//                    ibk = testClass.updateModel("sampleTest",weight,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6,staffClassification);
//                    myFirebaseRef.child(currentKey).child("classification").setValue(staffClassification);
//                }
//                else {
//                    Double pred;
//                    Double howFilled = 0.0;
//                    Instances testingSet = testClass.createInstance(weight,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6);
//                    for(int i = 0; i<testingSet.numInstances();i++){
//                        try {
//                            pred = ibk.classifyInstance(testingSet.instance(i));
//                            if(pred==0.0){
//                                howFilled = 1.0;
//                            }else if(pred==1.0){
//                                howFilled = 0.75;
//                            }else if(pred==2.0){
//                                howFilled = 0.50;
//                            }else if(pred==3.0){
//                                howFilled = 0.25;
//                            }else if(pred==4.0){
//                                howFilled = 0.0;
//                            }
//                            System.out.println("I predict ----------> You are ---------> " + howFilled + " filled");
//                            myFirebaseRef.child(currentKey).child("classification").setValue(howFilled);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        final DatabaseReference timeStampRef = database.getReference("Blk 1 Lvl 3 test");
//        timeStampRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Query timeStampQuery = timeStampRef.orderByKey().startAt("l").endAt("lastTimeStamp");
//                timeStampQuery.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Map<String,String> timeStampMap = (Map<String, String>) dataSnapshot.getValue();
//                        latestTimeStamp = timeStampMap.get("lastTimeStamp");
//                        System.out.println(latestTimeStamp);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }
}

