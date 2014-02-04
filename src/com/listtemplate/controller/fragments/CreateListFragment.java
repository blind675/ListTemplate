package com.listtemplate.controller.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.listtemplate.R;
import com.listtemplate.model.AppModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Catalin BORA on 2/4/14.
 *
 */
public class CreateListFragment extends Fragment {

    private static ListView mListView;
    private static EditText mTitleView;
    private static EditText mAddElementView;
    private static Button mUseListButton;
    /**
     * Called when the fragment is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View theCreateListView = inflater.inflate(R.layout.fragment_create_list, container, false);

        // Get ListView object from xml
        mListView = (ListView) theCreateListView.findViewById(R.id.listView);

        // Get the useList button
        mUseListButton = (Button) theCreateListView.findViewById(R.id.useListButton);

        // Get the list title view
        mTitleView = (EditText) theCreateListView.findViewById(R.id.listTitle);
        // Set the title to Untitled or Untitled 1 or 2 or ....
        mTitleView.setText(AppModel.getInstance().generateListName());

        // Get the started date view
        TextView startedOnView = (TextView) theCreateListView.findViewById(R.id.startedOnDateView);
        // Get a simple date format
        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        // Get the current date
        Calendar currentDate = Calendar.getInstance();
        // Set the started date to today
        startedOnView.setText(R.string.started_on + mDateFormat.format(currentDate.getTime()) );

        // Get the add element view
        mAddElementView = (EditText) theCreateListView.findViewById(R.id.addElement);

        // Get the use list button
        mUseListButton = (Button) theCreateListView.findViewById(R.id.useListButton);
        
        return theCreateListView;

    }
}
