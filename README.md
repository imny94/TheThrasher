# 50.001 IOT Project
##### This is a Project done for the 50.001 IOT Project.
######This project aims to add IOT capabilities to dustbins to help make waste management more efficient.

This branch contains the code used in the project. Included in this project are :

| Code For      | Folder    |
| ------------- |:---------:|
| Load Cell | LoadCell |
| Ultrasonic Sensor | SonarSystem |
| Firebase Management | FirebaseData |
| Classifier for State of Bin | Classifier2 |
| Android Application for Front-End Users | TheSmartBin_2 |
| Grapher on Android Application for Data Analytics | Grapher |

Load Cell is connected to Raspberry pi via Arduino for Analog to Digital conversion.
Ultrasonic sensors are linked to Raspberry Pi as well. Both data from Load Cell and Ultrasonic sensors
are then pushed into firebase from the Raspberry Pi to this location : https://smartbin-16031.firebaseio.com/

Classifier is a basic Machine-Learning Style Classifier which is used to classify the state of the dustbin
based on values obtained from ultrasonic and load cells equipped onto dustbin. Run testClassifier.java to start 
Classifier. Classifier will run-automatically, retrieving and updating relevant information from and to firebase.
