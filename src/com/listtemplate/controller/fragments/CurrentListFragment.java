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
import com.listtemplate.controller.adapters.HomeListAdapter;
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
        TextView listName = (TextView) currentListView.findViewById(R.id.listTitle);
        // Change the list name
        listName.setText(AppModel.getInstance().getNameOfTheCurrentList());

        // Get the list description TextView
        TextView listDescription = (TextView) currentListView.findViewById(R.id.listDescription);
        // Change the list description
        listDescription.setText(AppModel.getInstance().getDescriptionOfTheCurrentList());

        // Get the list background photo view
        ImageView listBackgroundPic = (ImageView) currentListView.findViewById(R.id.listBackgroundPic);
        // Set the background picture
        byte[] backgroundImage = AppModel.getInstance().getBackgroundImageOfTheCurrentList();
        listBackgroundPic.setImageBitmap(BitmapFactory.decodeByteArray(backgroundImage, 0, backgroundImage.length));

        // Get ListView object from xml
        ListView listView = (ListView) currentListView.findViewById(R.id.listView);

        // Create a custom adapter
        //mAdapter = new HomeListAdapter(getActivity().getApplicationContext(), AppModel.getInstance().getOpenedLists());
        // Set the custom adapter
        //listView.setAdapter(mAdapter);
        return currentListView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
