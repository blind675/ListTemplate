package com.listtemplate.controller.fragments;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.listtemplate.R;
import com.listtemplate.controller.adapters.CurrentListAdapter;
import com.listtemplate.controller.utils.MenuTracker;
import com.listtemplate.model.AppModel;

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

        // Get ListView object from xml
        ListView listView = (ListView) currentListView.findViewById(R.id.listView);

        // Create a custom adapter
        CurrentListAdapter adapter = new CurrentListAdapter(getActivity().getApplicationContext(),R.id.checkBox);
        // Set the custom adapter
        listView.setAdapter(adapter);

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

        // Save the state of the currently opened list in the database
        // AppModel.getInstance().saveList(getActivity().getApplicationContext());
        // Set the currently opened list to none
        // AppModel.getInstance().closeCurrentlyOpenList();
    }
}
