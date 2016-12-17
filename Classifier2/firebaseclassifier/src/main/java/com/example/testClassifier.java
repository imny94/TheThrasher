package com.example;

/**
 * Created by nicholas on 14-Dec-16.
 */

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import weka.classifiers.Classifier;

/**
 * Created by nicholas on 12-Dec-16.
 */

public class testClassifier extends DynamicBinFilledClassifier {

    private static Classifier ibk;
//    private static DynamicBinFilledClassifier testClass;
    private static String fileName = "sampleTest";

    private static DatabaseReference BinClassificationRef;
    private static DatabaseReference BinClassificationRefForGraph;
    private static DatabaseReference rawDataRef;
    private static DatabaseReference staffClassificationRef;
    private static DatabaseReference binLastTimeStampRef;

    private static Map<String,String> binClassification;
//    private static Map<String ,String > classificationMap;
//    private static Map<String,Map<String,String>> classificationForGraph;


    /*
    Initialises all relevant Firebase parameters as an Admin User

    All firebase references initialised here

    ChildEventListener for binLocation initialised here as well
     */
    testClassifier(){
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

//        System.out.println(defaultApp.getName());  // "[DEFAULT]"
//
//// Retrieve services by passing the defaultApp variable...
//        FirebaseAuth defaultAuth = FirebaseAuth.getInstance(defaultApp);
//        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(defaultApp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        BinClassificationRef = database.getReference("Bin Classification");
        BinClassificationRefForGraph = database.getReference("Bin Classification For Graph");
//        classificationMap = new HashMap<String ,String>();
//
//        classificationForGraph = new HashMap<String,Map<String,String>>();

//        users.put("alanisawesome", new User("June 23, 1912", "Alan Turing"));
//        users.put("gracehop", new User("December 9, 1906", "Grace Hopper"));
//
//        usersRef.setValue(users);

        rawDataRef = database.getReference("Raw Data");
        binLastTimeStampRef = database.getReference("Bin Last Time Stamp");
        final DatabaseReference binLocationsRef = database.getReference("Bin Locations");
        staffClassificationRef = database.getReference("Staff Classification");

//        final ArrayList<String> binLocationList = new ArrayList<>();
        binLocationsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String newLocation = dataSnapshot.getValue(String.class);
//                binLocationList.add(newLocation);
                createStaffClassificationListener(newLocation);
                createBinRawDataListener(newLocation);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String newLocation = dataSnapshot.getValue(String.class);
//                binLocationList.remove(newLocation);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;

//        binLastTimeStampRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Map<String,String> updatedTimeStampForOneBin = (Map<String, String>) dataSnapshot.getValue();
//                String binThatWasUpdated = dataSnapshot.getKey();
//                System.out.println(updatedTimeStampForOneBin);
//                System.out.println(dataSnapshot);
//                // ToDo Need to include function call to trigger classifying of data
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
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


    }


    /*
    This function is called whenever a new bin location is added into firebase,
    creating a new listener for each bin

    Each of this listener is unique for each bin

    The listener will check for any updates of raw data from bins and for every new dataset added,
    the listener will query Staff Classification to check if there are any classifications made by the staff
    that might have been missed out previously.
    It will then call doClassification and pass all the relevant data it obtains to doClassification without doing anything else
     */
    private static void createBinRawDataListener(final String binLocation){
        rawDataRef.child(binLocation)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("Child Added for Raw Data");
                Map<String , Double> latestData = (Map<String, Double>) dataSnapshot.getValue();
                final Double s1 = (latestData.get("Sonar1"));
                final Double s2 = (latestData.get("Sonar2"));
                final Double s3 = (latestData.get("Sonar3"));
                final Double s4 = (latestData.get("Sonar4"));
                final Double s5 = (latestData.get("Sonar5"));
                final Double s6 = (latestData.get("Sonar6"));
                final Double weight = (latestData.get("pressure"));
                Query staffQuery = staffClassificationRef.orderByKey().equalTo(binLocation);
                staffQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        System.out.println("checking for any staff classification, Added");
                        String staffClassification = dataSnapshot.getValue(String.class);
                        System.out.println("staff classification is : "+staffClassification);
                        doClassification(staffClassification,binLocation,weight,s1,s2,s3,s4,s5,s6);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        System.out.println("checking for any staff classification, Changed");
                        String staffClass = dataSnapshot.getValue(String.class);
                        System.out.println("staff classification is : "+staffClass);
                        doClassification(staffClass,binLocation,weight,s1,s2,s3,s4,s5,s6);
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

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println("Child Changed for Raw Data");

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
        }) ;
    }


    private static void createStaffClassificationListener(final String binLocation){
        staffClassificationRef.child(binLocation)
            .addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    System.out.println("New Staff Classification");
                    String staffClass = dataSnapshot.getValue(String.class);
                    lastTimeStampQuery(binLocation,staffClass);
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

    private static void lastTimeStampQuery(final String binLocation, final String staffClassification) {
        Query timeStampQuery = binLastTimeStampRef.orderByKey().equalTo(binLocation);
        timeStampQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("Checking for last time stamp for bin");
                String lastTimeStamp = dataSnapshot.getValue(String.class);
                rawDataQuery(binLocation,lastTimeStamp,staffClassification);
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

    private static void rawDataQuery(final String binLocation, String lastTimeStamp, final String staffClassification) {
        Query rawDataQuery = rawDataRef.child(binLocation).orderByKey().equalTo(lastTimeStamp);
        rawDataQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("Querying raw data");
                Map<String , Double> latestData = (Map<String, Double>) dataSnapshot.getValue();
                final Double s1 = (latestData.get("Sonar1"));
                final Double s2 = (latestData.get("Sonar2"));
                final Double s3 = (latestData.get("Sonar3"));
                final Double s4 = (latestData.get("Sonar4"));
                final Double s5 = (latestData.get("Sonar5"));
                final Double s6 = (latestData.get("Sonar6"));
                final Double weight = (latestData.get("pressure"));
                doClassification(staffClassification,binLocation,weight,s1,s2,s3,s4,s5,s6);

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


    /*
    This function is called by the listener created by createBinRawDataListener
    This function will check if there has been any classification by ground staff on bin
    If there is a classification, function will take raw data and update training model
    Else, function will take the data and run a classification on it

    It will then push the data up into firebase.

    The nodes it will update will be "Bin Classification For Graph" and "Bin Classification"

     */
    private static void doClassification(String staffClassification, String binLocation, Double weight, Double s1, Double s2, Double s3, Double s4, Double s5, Double s6){
        Double classification;
        if(staffClassification.equals("N.A")){
            System.out.println("no user classification");
            classification = classify(ibk,weight,s1,s2,s3,s4,s5,s6);
        }else{
            System.out.println("Using user classification");
            staffClassificationRef.child(binLocation).setValue("N.A"); // This makes sure the staff classification is not used twice
            classification = Double.parseDouble(staffClassification);
            updateModel(fileName,weight,s1,s2,s3,s4,s5,s6,classification);
        }
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);

        Map<String ,Object> timeStampWithClassification = new HashMap<String, Object>();
        String stringClassification = classification.toString();
        timeStampWithClassification.put(binLocation+"/"+formattedDate,stringClassification);
        System.out.println("adding bin classification for graph to firebase...");
        BinClassificationRefForGraph.updateChildren(timeStampWithClassification);
        timeStampWithClassification.clear();

        binClassification = new HashMap<>();
        binClassification.put("Classification",stringClassification);
        binClassification.put("Time",formattedDate);
        System.out.println("adding bin classification to firebase...");
        BinClassificationRef.child(binLocation).setValue(binClassification);
        binClassification.clear();
    }


    public static void changeFileName(String newFileName){
        fileName = newFileName;
    }


    /*
    Main method to call itself to get this "server" running
     */
    public static void main(String[]args){
        new testClassifier();                                                       // Initialises itself to start the listeners for firebase
//        testClass = new DynamicBinFilledClassifier();
        System.out.println("Creating model...");                                    // Attempts to create new model when starting program, just in case file does not exist.
        ibk = createNewModel(fileName,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);             // If file exists, nothing will happen
        System.out.println("Updating model...");
        ibk = updateModel(fileName,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00);               // Adds one data point to model to make sure function runs :)


//        JSONObject allData = null;
//        try {
//            allData = FirebaseFetcher.readJsonFromFirebase("https://smartbin-16031.firebaseio.com/.json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        JSONObject latestTimeStamp = (JSONObject) allData.get("Bin Last Time Stamp");
//        JSONArray binLocations = (JSONArray) allData.get("Bin Locations");
//        JSONObject rawData = (JSONObject) allData.get("Raw Data");
//        JSONObject staffClassification = (JSONObject) allData.get("Staff Classification");
//        System.out.println(staffClassification);
//        System.out.println("\n\n\n"+latestTimeStamp);
//        System.out.println(latestTimeStamp.getClass());
//        System.out.println("\n\n\n"+binLocations);
//        System.out.println(binLocations.getClass());
//        System.out.println("\n\n\n"+rawData);
//        System.out.println(rawData.getClass());



//        for(int i=0;i<binLocations.length();i++){
//            String location = (String) binLocations.get(i);
//            JSONObject timeStamp = (JSONObject) latestTimeStamp.get(location);
//            System.out.println(timeStamp);
//            System.out.println(timeStamp.getClass());
//            JSONObject binLoc = (JSONObject) rawData.get(location);
//            JSONObject latestData = (JSONObject) binLoc.get(timeStamp.getString("lastTimeStamp"));
//            Double s1 = latestData.getDouble("Sonar1");
//            Double s2 = latestData.getDouble("Sonar2");
//            Double s3 = latestData.getDouble("Sonar3");
//            Double s4 = latestData.getDouble("Sonar4");
//            Double s5 = latestData.getDouble("Sonar5");
//            Double s6 = latestData.getDouble("Sonar6");
//            Double weight = latestData.getDouble("pressure");
//
//            System.out.println(latestData);
//            String userClassification = (String) staffClassification.get(location);
//            Double classification;
//            if (userClassification.equals("N.A")){
//                System.out.println("no user classification");
//                classification = classify(ibk,weight,s1,s2,s3,s4,s5,s6);
//            }
//            else {
//                System.out.println("Using user classification");
//                classification = Double.parseDouble(userClassification);
//                testClass.updateModel("sampleTest",weight,s1,s2,s3,s4,s5,s6,classification);
//            }
//
//            String stringClassification = classification.toString();
//            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
//            Date date = new Date();
//            String formattedDate = dateFormat.format(date);

//            timeStampWithClassification.put(formattedDate,stringClassification);
//            classificationForGraph.put(location,timeStampWithClassification);
//            System.out.println("adding bin classification for graph to firebase...");
//            BinClassificationRefForGraph.setValue(classificationForGraph);
//            classificationForGraph.clear();

//            classificationMap.put("Classification",stringClassification);
//            classificationMap.put("Time", formattedDate);
//            binClassification.put(location,classificationMap);
//            System.out.println(binClassification.toString());
//            System.out.println("adding bin classification to firebase...");
//            BinClassificationRef.setValue(binClassification);
//            classificationMap.clear();
//            binClassification.clear();


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

//    }
}

