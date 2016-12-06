package com.example;

/**
 * Created by nicholas on 05-Dec-16.
 */

public class testClassifier {

    public static void main(String[]args){
        DynamicBinFilledClassifier testClass = new DynamicBinFilledClassifier();
        System.out.println("Creating model...");
        testClass.createNewModel("classifier2/src/main/java/com/example/sampleTest",500.0,0.5,0.5,0.5,0.5,0.5,0.5,"50pFilled");
        System.out.println("Updating model...");
        testClass.updateModel("classifier2/src/main/java/com/example/sampleTest",500.0,0.5,0.5,0.5,0.5,0.5,0.5,"50pFilled");
    }
}

