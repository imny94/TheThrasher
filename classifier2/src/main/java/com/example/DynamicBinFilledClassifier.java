package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import wlsvm.WLSVM;

//implements weka.classifiers.UpdateableClassifier

public class DynamicBinFilledClassifier {

    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter out = null;
    WLSVM svmCls;

//    DynamicBinFilledClassifier(){
//
//    }

	/*
		The function createNewModel creates a new file for the training model.
		It will require all the relevant attributes as arguments for the function
		to create the first results in the file
	*/

    public void createNewModel(String fileName,Double weight, Double sonar1,Double sonar2,Double sonar3,
                               Double sonar4,Double sonar5,Double sonar6, String classification){
        // Declare the numeric weight and sonar attributes
        Attribute Attribute1 = new Attribute("weight"); // weight is in grams
        Attribute Attribute2 = new Attribute("sonar1"); // sonar readings is in metres
        Attribute Attribute3 = new Attribute("sonar2");
        Attribute Attribute4 = new Attribute("sonar3");
        Attribute Attribute5 = new Attribute("sonar4");
        Attribute Attribute6 = new Attribute("sonar5");
        Attribute Attribute7 = new Attribute("sonar6");

        // Declare the class attribute along with its values
        //   @ATTRIBUTE class        {Overflowing,75pFilled,50pFilled,25pFilled,Empty}
        FastVector fvClassVal = new FastVector(5);
        fvClassVal.addElement("Overflowing");
        fvClassVal.addElement("75pFilled");
        fvClassVal.addElement("50pFilled");
        fvClassVal.addElement("25pFilled");
        fvClassVal.addElement("Empty");
        Attribute ClassAttribute = new Attribute("binStatus", fvClassVal);

        // Declare the feature vector template
        FastVector fvWekaAttributes = new FastVector(8);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        fvWekaAttributes.addElement(Attribute4);
        fvWekaAttributes.addElement(Attribute5);
        fvWekaAttributes.addElement(Attribute6);
        fvWekaAttributes.addElement(Attribute7);
        fvWekaAttributes.addElement(ClassAttribute);

        // Create an empty training set
        Instances trainingSet = new Instances("Rel",fvWekaAttributes, 1);
        // Set Class Index
        trainingSet.setClassIndex(trainingSet.numAttributes() - 1);

        // Create the instance
        Instance iClassify = new Instance(trainingSet.numAttributes());

        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(0),weight);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(1),sonar1);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(2),sonar2);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(3),sonar3);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(4),sonar4);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(5),sonar5);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(6),sonar6);
        iClassify.setValue((Attribute)fvWekaAttributes.elementAt(7),classification);

        // Add the Instance
        trainingSet.add(iClassify);

        try {
            svmCls = new WLSVM();
            svmCls.buildClassifier(trainingSet);
            weka.core.SerializationHelper.write("svmBinTrainedModel", svmCls); // Stores trained model as "svmBinTrainedModel"
            // To load, use   WLSVM svmCls = (WLSVM) weka.core.SerializationHelper.read("svmTrainedModel");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateModel(String fileName,Double weight, Double sonar1,Double sonar2,Double sonar3,
                            Double sonar4,Double sonar5,Double sonar6, String classification){
        String toAdd = String.format("%f,%f,%f,%f,%f,%f,%f,%s",weight,sonar1,sonar2,sonar3,sonar4,sonar5,sonar6,classification);
        try{
            fw = new FileWriter(fileName,true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println(toAdd);
            fw.close();
            File f = new File(fileName);
            BufferedReader inputReader;
            inputReader = readFile(f);
            Instances data;

            data=new Instances(inputReader);
            data.setClassIndex(data.numAttributes()-1);
            svmCls = new WLSVM();
            svmCls.buildClassifier(data);
            weka.core.SerializationHelper.write("svmBinTrainedModel", svmCls);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
    }

    private BufferedReader readFile(File f) {
        try {
            return new BufferedReader(new FileReader(f.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
