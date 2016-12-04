package com.example;

/**
 * Created by nicholas on 05-Dec-16.
 */

public class testClassifier {

    public static void main(String[]args){
        DynamicBinFilledClassifier testClass = new DynamicBinFilledClassifier();
        testClass.createNewModel("sampleTest",500.0,0.5,0.5,0.5,0.5,0.5,0.5,"50pFilled");
    }
}

