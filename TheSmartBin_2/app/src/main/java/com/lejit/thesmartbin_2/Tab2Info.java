package com.lejit.thesmartbin_2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.firebase.client.Firebase;
//<<<<<<< HEAD
//=======
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;

//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.io.Writer;
//import java.util.Map;
//
//import weka.classifiers.Classifier;
//import weka.classifiers.lazy.IBk;
//import weka.core.Attribute;
//import weka.core.FastVector;
//import weka.core.Instance;
//import weka.core.Instances;
//>>>>>>> b47c5f3edbed7b0a4c84880d3700bc3788acde91

/**
 * Created by MY LENOVO on 12/12/2016.
 */

public class Tab2Info extends Fragment {
    Button submit;
    String fileName = "classifierData";
    String latestTimeStamp;
    Double pressure;
    Double sonar1;
    Double sonar2;
    Double sonar3;
    Double sonar4;
    Double sonar5;
    Double sonar6;
    double DataInfo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


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


        View rootView = inflater.inflate(R.layout.ground_tab2, container, false);
        final String [] values =
                {"Select Bin Status","Empty","25%","50%","75%","Full",};
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        submit=(Button) rootView.findViewById(R.id.submitButton);
        Firebase.setAndroidContext(this.getActivity());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = spinner.getSelectedItem().toString();
                if(text=="25%"||text=="50%"||text=="75%"){
                    text=text.substring(0,1);
                    DataInfo=(Double.parseDouble(text))/100.00;

                }
                if(text=="Full"){
                    DataInfo=1.00;
                }
                if(text=="Empty"){
                    DataInfo=0.00;
                }

                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();
                Firebase myFirebaseRef=new Firebase("https://smartbin-16031.firebaseio.com/");
                myFirebaseRef.child(ts).setValue(text);


//                Firebase myFirebaseRefTest=new Firebase("https://smartbin-16031.firebaseio.com");
//                myFirebaseRefTest.child("Blk 1 Lvl 3 test/classification").setValue(text);
//
//
//
//
//                final DatabaseReference dataRef = database.getReference("Blk 1 Lvl 3 test/"+latestTimeStamp);
//                ValueEventListener valueEventListener = dataRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Query query = dataRef.orderByKey().endAt("value");
//                        query.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                System.out.println(dataSnapshot);
//                                Map<String, Double> dataMap = (Map<String, Double>) dataSnapshot.getValue();
//                                System.out.println(dataMap.toString());
//                                pressure = dataMap.get("value");
//                                sonar1 = dataMap.get("sonar1");
//                                sonar2 = dataMap.get("sonar2");
//                                sonar3 = dataMap.get("sonar3");
//                                sonar4 = dataMap.get("sonar4");
//                                sonar5 = dataMap.get("sonar5");
//                                sonar6 = dataMap.get("sonar6");
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//<<<<<<< HEAD
////                DynamicBinFilledClassifier binClassifier = new DynamicBinFilledClassifier();
////                if(new File(fileName+".arff").isFile()){
////                    // File already exists, so use update method
////                    System.out.println("Updating model...");
////                    updateModel(fileName,pressure,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6,text);
////                }
////                else {
//////                    // File does not exist, so re-create new file
//////                    System.out.println("Creating model...");
//////                    createNewModel(fileName,pressure,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6,text);
//////                }
//=======
////                DynamicBinFilledClassifier binClassifier = new DynamicBinFilledClassifier();
////                if(new File(fileName+".arff").isFile()){
////                    // File already exists, so use update method
////                    System.out.println("Updating model...");
////                    updateModel(fileName,pressure,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6,text);
////                }
////                else {
////                    // File does not exist, so re-create new file
////                    System.out.println("Creating model...");
////                    createNewModel(fileName,pressure,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6,text);
////                }
//>>>>>>> b47c5f3edbed7b0a4c84880d3700bc3788acde91

            }
        });

        return rootView;

    }
//    FileWriter fw = null;
//    BufferedWriter bw = null;
//    PrintWriter out = null;
//    //WLSVM svmCls;
//    Classifier ibk;
//
//    public Classifier createNewModel(String fileName, Double weight, Double sonar1, Double sonar2, Double sonar3,
//                                     Double sonar4, Double sonar5, Double sonar6, String classification){
//
//        if(new File(fileName+".arff").isFile()){
//            System.out.println("The file already exists! Consider updating the model instead. " +
//                    "\nUnless you want to overwrite data, please delete or change location/filename of existing file");
//            return null;
//        }
//        System.out.println("Entered createNewModel Class \n"+fileName+"\n weight is:" + weight);
//
//        // Declare the numeric weight and sonar attributes
//        Attribute Attribute1 = new Attribute("weight"); // weight is in grams
//        Attribute Attribute2 = new Attribute("sonar1"); // sonar readings is in metres
//        Attribute Attribute3 = new Attribute("sonar2");
//        Attribute Attribute4 = new Attribute("sonar3");
//        Attribute Attribute5 = new Attribute("sonar4");
//        Attribute Attribute6 = new Attribute("sonar5");
//        Attribute Attribute7 = new Attribute("sonar6");
//
//        // Declare the class attribute along with its values
//        //   @ATTRIBUTE class        {Overflowing,75pFilled,50pFilled,25pFilled,Empty}
//        FastVector fvClassVal = new FastVector(5);
//        fvClassVal.addElement("Overflowing");
//        fvClassVal.addElement("75pFilled");
//        fvClassVal.addElement("50pFilled");
//        fvClassVal.addElement("25pFilled");
//        fvClassVal.addElement("Empty");
//        Attribute ClassAttribute = new Attribute("binStatus", fvClassVal);
//
//        // Declare the feature vector template
//        FastVector fvWekaAttributes = new FastVector(8);
//        fvWekaAttributes.addElement(Attribute1);
//        fvWekaAttributes.addElement(Attribute2);
//        fvWekaAttributes.addElement(Attribute3);
//        fvWekaAttributes.addElement(Attribute4);
//        fvWekaAttributes.addElement(Attribute5);
//        fvWekaAttributes.addElement(Attribute6);
//        fvWekaAttributes.addElement(Attribute7);
//        fvWekaAttributes.addElement(ClassAttribute);
//
//        // Create an empty training set
//        Instances trainingSet = new Instances("Rel",fvWekaAttributes, 1);
//        // Set Class Index
//        trainingSet.setClassIndex(trainingSet.numAttributes() - 1);
//
//        // Create the instance
//        Instance iClassify = new Instance(trainingSet.numAttributes());
//
//        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(0),weight);
//        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(1),sonar1);
//        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(2),sonar2);
//        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(3),sonar3);
//        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(4),sonar4);
//        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(5),sonar5);
//        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(6),sonar6);
//        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(7),classification);
//
//        // Add the Instance
//        trainingSet.add(iClassify);
//        System.out.println(String.valueOf(trainingSet));
//
//        try{
//            PrintWriter writer = new PrintWriter(fileName+".arff", "UTF-8");
//            writer.print(trainingSet);
//            writer.close();
//        } catch (IOException e) {
//            // do something
//        }
//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), fileName+".arff");
//
//        try(Writer writer = new BufferedWriter(new OutputStreamWriter(
//                new FileOutputStream(fileName+".arff"), "utf-8"))) {
//            writer.write(String.valueOf(trainingSet));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//<<<<<<< HEAD
//
//        try {
////            svmCls = new WLSVM();
////            svmCls.buildClassifier(trainingSet);
//=======
//
////        try {
////            svmCls = new WLSVM();
////            svmCls.buildClassifier(trainingSet);
//>>>>>>> b47c5f3edbed7b0a4c84880d3700bc3788acde91
//            ibk = new IBk();
//            ibk.buildClassifier(trainingSet);
//            weka.core.SerializationHelper.write(fileName, ibk);//svmCls); // Stores trained model as "svmBinTrainedModel"
//            // To load, use   WLSVM svmCls = (WLSVM) weka.core.SerializationHelper.read("svmTrainedModel");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return ibk;
//
//    }
//
//    /*
//
//        Returns null if unsuccessful and ibk if update completes
//
//     */
//
//    public Classifier updateModel(String fileName,Double weight, Double sonar1,Double sonar2,Double sonar3,
//                                  Double sonar4,Double sonar5,Double sonar6, String classification){
//        if(!new File(fileName+".arff").isFile()){
//            System.out.println("No such file found, check for location or misspelling of filename");
//            return null;
//        }
//        String toAdd = String.format("%f,%f,%f,%f,%f,%f,%f,%s",weight,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6,classification);
//        System.out.println(toAdd);
//        try{
//            fw = new FileWriter(fileName+".arff",true);
//            bw = new BufferedWriter(fw);
//            fw.write("\n"+toAdd);
//            fw.close();
//            File f = new File(fileName+".arff");
//            BufferedReader inputReader;
//            inputReader = readFile(f);
//            Instances data;
//
//            try {
//                data = new Instances(inputReader);
//                data.setClassIndex(data.numAttributes()-1);
//                ibk = new IBk();
//                ibk.buildClassifier(data);
//                weka.core.SerializationHelper.write(fileName, ibk);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            data= new Instances(inputReader);
//            int numAttributes = data.numAttributes();
//            data.setClassIndex(numAttributes-1);
////            svmCls = new WLSVM();
////            svmCls.buildClassifier(data);
//            ibk = new IBk();
//            ibk.buildClassifier(data);
//            weka.core.SerializationHelper.write("svmBinTrainedModel", ibk);//svmCls);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return ibk;
//<<<<<<< HEAD
//
////    @Override
////    public void updateClassifier(Instance instance) throws Exception {
////        // load data
////        ArffLoader loader = new ArffLoader();
////        loader.setFile(new File("/some/where/data.arff"));
////        Instances structure = loader.getStructure();
////        structure.setClassIndex(structure.numAttributes() - 1);
////
////        // train NaiveBayes
////        NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
////        nb.buildClassifier(structure);
////        Instance current;
////        while ((current = loader.getNextInstance(structure)) != null)
////            nb.updateClassifier(current);
////    }
//   }
//
//    private BufferedReader readFile(File f) {
//        try {
//            return new BufferedReader(new FileReader(f.toString()));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//=======

//    @Override
//    public void updateClassifier(Instance instance) throws Exception {
//        // load data
//        ArffLoader loader = new ArffLoader();
//        loader.setFile(new File("/some/where/data.arff"));
//        Instances structure = loader.getStructure();
//        structure.setClassIndex(structure.numAttributes() - 1);
//
//        // train NaiveBayes
//        NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
//        nb.buildClassifier(structure);
//        Instance current;
//        while ((current = loader.getNextInstance(structure)) != null)
//            nb.updateClassifier(current);
//    }
//   }
//
//    private BufferedReader readFile(File f) {
//        try {
//            return new BufferedReader(new FileReader(f.toString()));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
    }
//>>>>>>> b47c5f3edbed7b0a4c84880d3700bc3788acde91





