package com.listtemplate.controller.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.itemmanipulation.SwipeDismissAdapter;
import com.listtemplate.R;
import com.listtemplate.controller.adapters.CreateListAdapter;
import com.listtemplate.model.AppModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Catalin BORA on 2/4/14.
 *
 */
public class CreateListFragment extends Fragment {

    private List<String> mElementsList = new ArrayList<String>();
    private static ListView mListView;
    private static EditText mTitleView;
    private static EditText mAddElementView;
    private static Button mUseListButton;
    private ArrayAdapter<String> mAdapter;
    private SwipeDismissAdapter mSwipeAdapter;
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

        // Create a list adapter
        mAdapter = new CreateListAdapter(getActivity().getApplicationContext(),mElementsList);
        // Use the swipe to delete with undo from Niek Haarman ( http://nhaarman.github.io/ListViewAnimations/ )
        // Wrap my adapter in his

        mSwipeAdapter = new SwipeDismissAdapter(mAdapter, new OnDismissCallback() {
            @Override
            public void onDismiss(AbsListView absListView, int[] reverseSortedPositions) {

                for (int position : reverseSortedPositions) {
                    mElementsList.remove(position);
                    mAdapter.notifyDataSetChanged();
                }

            }
        });
        mSwipeAdapter.setAbsListView(mListView);
        mListView.setAdapter(mSwipeAdapter);

        // Get the useList button
        mUseListButton = (Button) theCreateListView.findViewById(R.id.useListButton);

        // Get the list title view
        mTitleView = (EditText) theCreateListView.findViewById(R.id.listTitle);
        // Set the title to Untitled or Untitled 1 or 2 or ....
        mTitleView.setHint(AppModel.getInstance().generateListName());

        // Get the started date view
        TextView startedOnView = (TextView) theCreateListView.findViewById(R.id.startedOnDateView);
        // Get a simple date format
        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        // Get the current date
        Calendar currentDate = Calendar.getInstance();
        // Set the started date to today
        startedOnView.setText(getResources().getString(R.string.started_on) +" " + mDateFormat.format(currentDate.getTime()) );

        // Get the add element view
        mAddElementView = (EditText) theCreateListView.findViewById(R.id.addElement);
        // Put an event listener on the add text view
        mAddElementView.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    executeAdd();
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Get the use list button
        mUseListButton = (Button) theCreateListView.findViewById(R.id.useListButton);
        // TODO: put an event listener here too

        return theCreateListView;
    }

    /**
     * Add the element in the textBox to the local array
     * and update the display
     */
    private void executeAdd(){
        // don't add empty rows
        if(!mAddElementView.getText().toString().isEmpty()){
            // add element
            mElementsList.add(mAddElementView.getText().toString());

            // refresh the list
            mSwipeAdapter.notifyDataSetChanged();

            // clear the field
            mAddElementView.getText().clear();
        }
    }

}
