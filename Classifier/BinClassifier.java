import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import wlsvm.WLSVM;

public class DynamicFilledClassifier {

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
	Instances trainingSet = new Instances("Rel",fvWekaAttributes, 1000);
// Set Class Index
	trainingSet.setClassIndex(7);

// Create the instance
	Instance 
}