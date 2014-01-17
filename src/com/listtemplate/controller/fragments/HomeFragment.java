package com.listtemplate.controller.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.listtemplate.R;

/**
 * Created by Catalin BORA on 1/17/14.
 */
public class HomeFragment extends Fragment{

    /**
     * Called when the fragment is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View theHomeView = inflater.inflate(R.layout.fragment_home, container, false);


        return theHomeView;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
