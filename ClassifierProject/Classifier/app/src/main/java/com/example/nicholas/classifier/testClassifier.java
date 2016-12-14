package com.example.nicholas.classifier;

import android.os.Build;
import android.support.annotation.RequiresApi;

import weka.classifiers.Classifier;

/**
 * Created by nicholas on 12-Dec-16.
 */

public class testClassifier {
    static Classifier ibk;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[]args){
        final DynamicBinFilledClassifier testClass = new DynamicBinFilledClassifier();
        System.out.println("Creating model...");
        ibk = testClass.createNewModel("sampleTest",500.0,0.5,0.5,0.5,0.5,0.5,0.5,0.50);
        System.out.println("Updating model...");
        ibk = testClass.updateModel("sampleTest",500.0,0.5,0.5,0.5,0.5,0.5,0.5,0.50);

//        Instances newDataSet = testClass.createInstance(500.0,0.5,0.5,0.5,0.5,0.5,0.5);
//        Double pred;
//        Double howFilled = 0.0;
//        for(int i = 0; i<newDataSet.numInstances();i++){
//            try {
//                pred = ibk.classifyInstance(newDataSet.instance(i));
//                if(pred==0.0){
//                    howFilled = 1.0;
//                }else if(pred==1.0){
//                    howFilled = 0.75;
//                }else if(pred==2.0){
//                    howFilled = 0.50;
//                }else if(pred==3.0){
//                    howFilled = 0.25;
//                }else if(pred==4.0){
//                    howFilled = 0.0;
//                }
//                System.out.println("I predict ----------> You are ---------> " + howFilled + " filled");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
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
