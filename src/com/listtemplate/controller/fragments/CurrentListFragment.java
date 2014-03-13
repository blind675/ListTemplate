package com.listtemplate.controller.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.listtemplate.R;
import com.listtemplate.controller.adapters.CurrentListAdapter;
import com.listtemplate.controller.utils.MenuTracker;
import com.listtemplate.model.AppModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Catalin BORA on 1/24/14.
 *
 */
public class CurrentListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View currentListView = inflater.inflate(R.layout.fragment_current_list, container, false);

        // Get the list name TextView
        assert currentListView != null;
        TextView listName = (TextView) currentListView.findViewById(R.id.listTitle);
        // Change the list name
        listName.setText(AppModel.getInstance().getNameOfTheCurrentList());

        // Get the list background photo view
        ImageView listBackgroundPic = (ImageView) currentListView.findViewById(R.id.listBackgroundPic);
        // Set the background picture
        byte[] backgroundImage = AppModel.getInstance().getBackgroundImageOfTheCurrentList();
        if(backgroundImage != null){
            listBackgroundPic.setImageBitmap(BitmapFactory.decodeByteArray(backgroundImage, 0, backgroundImage.length));
        }

        // Get the creation date TextView
        TextView creationDate = (TextView) currentListView.findViewById(R.id.listStarted);

        // Get a simple date format
        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        // Get the current date
        Date startDate = AppModel.getInstance().getCreationDate();
        // Set the creation date to start date
        creationDate.setText(getString(R.string.started_on) +" "+mDateFormat.format(startDate));

        // Get ListView object from xml
        ListView listView = (ListView) currentListView.findViewById(R.id.listView);

        // Create a custom adapter
        CurrentListAdapter adapter = new CurrentListAdapter(getActivity().getApplicationContext(),R.id.checkBox);
        // Set the custom adapter
        listView.setAdapter(adapter);
        // Set a click adapter on the list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("TemplateList-Info", " Clicked on list");
                // 1. change the display
                // get the text view
                TextView textView = (TextView) view.findViewById(R.id.checkBox);
                // get the image view
                ImageView imageView = (ImageView) view.findViewById(R.id.imageCheck);
                // strike-through the text or not
                if(AppModel.getInstance().isElementSelected((String) textView.getText())){
                    // Set not strike-through
                    textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    // Set the dot picture
                    imageView.setImageResource(R.drawable.ic_black_dot);
                } else {
                    // Set the strike-through
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    // Set the check picture
                    imageView.setImageResource(R.drawable.ic_accept);
                }
                // 2. change the model
                AppModel.getInstance().toggleSelectedForElement((String) textView.getText());
                // 3. check if list is complete and show dialog
                if(AppModel.getInstance().isTheListComplete()){

                    // Show an alert dialog
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle(getString(R.string.list_is_complete));
                    alert.setMessage(getString(R.string.remove_list));
                    alert.setIcon(R.drawable.ic_alerts_and_states_warning);

                    alert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // remove the list from the db and the model
                            // also close the list
                            AppModel.getInstance().discardList(getActivity().getApplicationContext());

                            // switch the fragment
                            // change the fragment to the home fragment
                            FragmentManager fragmentManager = getFragmentManager();
                            Fragment fragment = new HomeFragment();

                            if(fragmentManager != null){

                                // update the main content by replacing fragments
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.content_frame, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }

                            // set the menu tracker to home
                            MenuTracker.getInstance().setOpenedFragment(MenuTracker.HOME);

                        }
                    });

                    alert.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Do nothing
                        }
                    });

                    alert.show();
                }

            }
        });
        // In case i didn't come from the menu
        // set the title again :)
        getActivity().getActionBar().setTitle(R.string.current_list);
        // and set the menu tracker to current list
        MenuTracker.getInstance().setOpenedFragment(MenuTracker.CURRENT_LIST);

        // return the fragment
        return currentListView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i("TemplateList-Info", " Called onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.i("TemplateList-Info", " Called onStop");

        // Save the state of the currently opened list in the database
        AppModel.getInstance().updateList(getActivity().getApplicationContext());
        // Set the currently opened list to none
        AppModel.getInstance().closeCurrentlyOpenList();
    }
}
