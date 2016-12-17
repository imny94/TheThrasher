package com.example;

/**
 * Created by USER on 12/16/2016.
 */
// PUT THIS IN GRADLE
// https://mvnrepository.com/artifact/org.json/json

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

//compile group: 'org.json', name: 'json', version: '20160810'

public class FirebaseFetcher {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromFirebase(String firebaseUrl) throws IOException, JSONException {
//        String firebaseUrl = "https://smartbin-16031.firebaseio.com/.json";
        InputStream is = new URL(firebaseUrl).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


    public static void main(String[] args){
        Object firebaseAllData = null;
        try {
            System.out.println("Accessing firebase...");
            firebaseAllData = readJsonFromFirebase("https://smartbin-16031.firebaseio.com/Raw Data/.json");
            System.out.println(firebaseAllData);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        JSONObject smt = (JSONObject) firebaseAllData.get("Raw Data");
//
//
//        System.out.println("\n\n\n"+smt);
//        System.out.println(smt.getClass());
//
//        JSONObject smtInside = (JSONObject) smt.get("Blk 1 Lvl 3");
//        System.out.println("\n\n\n" + smtInside);
//        System.out.println(smtInside.getClass());
//
//        JSONObject smtInsideTheInside = (JSONObject) smtInside.get("16-12-2016-14:29:25");
//        System.out.println("\n\n\n\n" + smtInsideTheInside);
//        System.out.println(smtInsideTheInside.getClass());
    }
}