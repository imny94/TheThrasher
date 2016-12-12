package com.lejit.thesmartbin_2;

/**
 * Created by MY LENOVO on 12/12/2016.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Tab1Info extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ground_tab1, container, false);
        return rootView;
    }
}
