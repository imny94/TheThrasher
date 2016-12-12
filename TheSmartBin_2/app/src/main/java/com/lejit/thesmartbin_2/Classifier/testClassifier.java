package com.lejit.thesmartbin_2.Classifier;

/**
 * Created by nicholas on 12-Dec-16.
 */

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by nicholas on 12-Dec-16.
 */

public class testClassifier {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[]args){
        DynamicBinFilledClassifier testClass = new DynamicBinFilledClassifier();
        System.out.println("Creating model...");
        testClass.createNewModel("sampleTest",500.0,0.5,0.5,0.5,0.5,0.5,0.5,"50pFilled");
        System.out.println("Updating model...");
        testClass.updateModel("sampleTest",500.0,0.5,0.5,0.5,0.5,0.5,0.5,"50pFilled");
    }
}