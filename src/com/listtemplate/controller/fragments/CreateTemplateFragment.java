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
 * Created by Catalin BORA on 3/14/14.
 * Class generated using Intellij IDEA
 */
public class CreateTemplateFragment extends Fragment {

    private List<String> mElementsList = new ArrayList<String>();
    private static EditText mTitleView;
    private static EditText mAddElementView;
    private static EditText mDescriptionView;
    private static CheckBox mAddAuthorCheck;
    private static CheckBox mReceiveEmailCheck;
    private static CheckBox mShareCheck;
    private static Button mSaveButton;
    private static byte[] mBackground;
    private ArrayAdapter<String> mAdapter;
    private SwipeDismissAdapter mSwipeAdapter;

    /**
    /**
     * Called when the fragment is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View theCreateTemplateView = inflater.inflate(R.layout.fragment_create_template, container, false);
        assert theCreateTemplateView != null;

        // Get ListView object from xml
        ListView listView = (ListView) theCreateTemplateView.findViewById(R.id.listView);

        // Create a list adapter
        // reuse the list adapter from the create list part
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
        mTitleView = (EditText) theCreateTemplateView.findViewById(R.id.templateTitle);
        // Get the description view
        mDescriptionView = (EditText) theCreateTemplateView.findViewById(R.id.descriptionText);
        // Get the add author checkbox
        mAddAuthorCheck = (CheckBox) theCreateTemplateView.findViewById(R.id.authorCheck);

        // Get the add element view
        mAddElementView = (EditText) theCreateTemplateView.findViewById(R.id.addElement);
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
        mSaveButton = (Button) theCreateTemplateView.findViewById(R.id.saveTemplateButton);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // Process the data and save the template
                //saveTheTemplate();
            }
        });

        // Set the title of the window, it's not set in other ways
        getActivity().getActionBar().setTitle(R.string.create_list);

        return theCreateTemplateView;
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
     * Get values from all the fields and construct a new template
     */
    private void saveTheTemplate(){

    }
}
