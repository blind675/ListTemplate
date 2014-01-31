package com.listtemplate.controller.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.listtemplate.R;
import com.listtemplate.controller.adapters.HomeListAdapter;
import com.listtemplate.model.AppModel;

/**
 * Created by Catalin BORA on 1/17/14.
 *
 */
public class HomeFragment extends Fragment{

    private static ListView mListView;
    private static HomeListAdapter mAdapter;
    /**
     * Called when the fragment is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View theHomeView = inflater.inflate(R.layout.fragment_home, container, false);

        // Get ListView object from xml
        mListView = (ListView) theHomeView.findViewById(R.id.listView);

        // Create a custom adapter
        mAdapter = new HomeListAdapter(getActivity().getApplicationContext(),AppModel.getInstance().getOpenedLists());
        // Set the custom adapter
        mListView.setAdapter(mAdapter);
        // Set a custom event listener
        mListView.setOnItemClickListener(new ListItemClickListener());
        return theHomeView;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /* The click listener for ListView in the home fragment */
    private class ListItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(HomeFragment.class.getName()," pos:"+position+" the id:"+id+" selected option:" + mAdapter.getSelectedOption());

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment = null;

            // user clicked on an existing list
            if(position < AppModel.getInstance().getNumberOfLists()){
                // open the list from the - business logic perspective
                AppModel.getInstance().openList(position);

                // open the currently opened list fragment
                fragment = new CurrentListFragment();

            } else {
                // user clicked on the create part of the list
                if(mAdapter.getSelectedOption() == 1){
                    // clicked on the create simple list part
                    // TODO: implement fragment

                } else if( mAdapter.getSelectedOption() == 2){
                    // clicked on the create from template part
                    // TODO: implement fragment

                }
            }

            // if the click was valid change the fragment
            if(fragment != null){
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
    }

}
