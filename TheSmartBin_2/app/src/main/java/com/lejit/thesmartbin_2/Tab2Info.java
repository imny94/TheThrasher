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

import java.io.File;

/**
 * Created by MY LENOVO on 12/12/2016.
 */

public class Tab2Info extends Fragment {
    Button submit;
    String fileName = "classifierData";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ground_tab2, container, false);
        String [] values =
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
                    text=Character.toString(text.charAt(0))+Character.toString(text.charAt(1))+"pFilled";
                }
                Firebase myFirebaseRef=new Firebase("https://smartbin-16031.firebaseio.com");
                myFirebaseRef.child("message").setValue(text);


                if(new File(fileName+".arff").isFile()){
                    // File already exists, so use update method

                }
                else {
                    // File does not exist, so re-create new file
                }

            }
        });

        return rootView;

    }


    }


