package com.listtemplate.controller.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
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
import com.listtemplate.controller.utils.MenuTracker;
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
    private static EditText mTitleView;
    private static EditText mAddElementView;
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
        assert theCreateListView != null;

        // Get ListView object from xml
        ListView listView = (ListView) theCreateListView.findViewById(R.id.listView);

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
        mSwipeAdapter.setAbsListView(listView);
        listView.setAdapter(mSwipeAdapter);

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
        Button useListButton = (Button) theCreateListView.findViewById(R.id.useListButton);
        useListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // Process the data and use the list
                useTheList();
            }
        });

        // Set the title of the window, it's not set in other ways
        getActivity().getActionBar().setTitle(R.string.create_list);

        return theCreateListView;
    }

    /**
     * Add the element in the textBox to the local array
     * and update the display
     */
    private void executeAdd(){
        // don't add empty rows
        if (mAddElementView.getText()!= null && mAddElementView.getText().toString().isEmpty()) {
            return;
        }
        // add element
        mElementsList.add(mAddElementView.getText().toString());

        // refresh the list
        mSwipeAdapter.notifyDataSetChanged();

        // clear the field
        mAddElementView.getText().clear();
    }

    /**
     * Save and open the current list
     */
    private void useTheList(){
        Log.i("TemplateList-Info"," Trying to use the currently created list.");

        String title = null;

        // get either the text or the hint as a title for the list
        if(mTitleView.getText() != null && mTitleView.getText().toString().isEmpty()){
            if(mTitleView.getHint() != null && !mTitleView.getHint().toString().isEmpty()){
                title = mTitleView.getHint().toString();
            }
        } else {
            title = mTitleView.getText().toString();
        }

        // create te list in the model
        AppModel.getInstance().createList(title,null,Calendar.getInstance().getTime(),null);

        // add the elements
        for (String element: mElementsList) {
            AppModel.getInstance().addElementToTheCurrentList(element);
        }

        // save the list to db
        AppModel.getInstance().saveList(getActivity().getApplicationContext());

        // change the fragment to the current list
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new CurrentListFragment();

        if(fragmentManager != null){

            // update the main content by replacing fragments
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        // set the menu tracker to current list
        MenuTracker.getInstance().setOpenedFragment(MenuTracker.CURRENT_LIST);

        Log.i("TemplateList-Info"," Exit the use the current list method");

    }
}
