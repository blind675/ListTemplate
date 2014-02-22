package com.listtemplate.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.listtemplate.R;
import com.listtemplate.controller.adapters.MenuAdapter;
import com.listtemplate.controller.fragments.HomeFragment;
import com.listtemplate.controller.utils.MenuTracker;
import com.listtemplate.model.AppModel;

/**
 * The main activity of the application. All else is based on fragments.
 */
public class MyActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mHomeFragmentActive;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the data of the DB in the AppModel first
        // the AppModel works like a cache (I overcomplicated a little)
        // TODO: think of having a loading screen

        // load the templates
        AppModel.getInstance().loadTemplates(this);


        // load the current lists
        AppModel.getInstance().loadLists(this);

        setContentView(R.layout.main);

        String[] menuItemsTitles = getResources().getStringArray(R.array.menu_items);
        String[] menuItemsDescriptions = getResources().getStringArray(R.array.menu_description);
        TypedArray menuIcons =  getResources().obtainTypedArray(R.array.menu_images);
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        MenuAdapter mAdapter = new MenuAdapter(this, menuItemsTitles, menuItemsDescriptions, menuIcons);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.indicator,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                // set the selected fragment before opening the menu drawer
                mDrawerList.setItemChecked(MenuTracker.getInstance().getOpenedFragment(), true);

                getActionBar().setTitle(mDrawerTitle);
                //mAdapter.notifyDataSetChanged(); // forced refresh
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(2);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        selectItem(2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action buttons

        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {
        // get the frame manager
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = null;
        mHomeFragmentActive = false;

        // TODO: logic for making the current list the default opened tab only if i have a current list

        switch(position){
            case 1: /** Called when the user clicks the Current List tab */
                setTitle(R.string.current_list);
                // get the fragment for current list
                // fragment = new CurrentListFragment();
                // set the menu tracker to current list
                MenuTracker.getInstance().setOpenedFragment(MenuTracker.CURRENT_LIST);
                break;
            case 3: /** Called when the user clicks the Browse Templates tab */
                setTitle(R.string.browse_templates);
                // get the fragment for spending
                // fragment = new BrowseTemplatesFragment();
                // set the menu tracker to browse templates
                MenuTracker.getInstance().setOpenedFragment(MenuTracker.BROWSE_TEMPLATES);
                break;
            case 4: /** Called when the user clicks the Create Template tab */
                setTitle(R.string.create_template);
                // get the fragment for spending
                // fragment = new CreateTemplateFragment();
                // set the menu tracker to create template
                MenuTracker.getInstance().setOpenedFragment(MenuTracker.CREATE_TEMPLATE);
                break;
            case 5: /** Called when the user clicks the Settings tab */
                setTitle(R.string.settings);
                // get the fragment for spending
                // fragment = new SettingsTemplate();
                // set the menu tracker to settings
                MenuTracker.getInstance().setOpenedFragment(MenuTracker.SETTINGS);
                break;
            default:/* Called when the user clicks the Home tab
                       the default is home fragment also the same as case 2 */
                setTitle(R.string.home);
                // get the fragment for home
                fragment = new HomeFragment();
                // set the menu tracker to home
                MenuTracker.getInstance().setOpenedFragment(MenuTracker.HOME);
                break;
        }

        if(fragment != null){

            // update the main content by replacing fragments
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Called when you press the Back button.
     * First go to the home fragment, after suspend the application.
     */
    @Override
    public void onBackPressed() {
        if(!mHomeFragmentActive) {
            // change to Home screen
            selectItem(22);
        } else {
            super.onBackPressed();
            // dispatch the event
        }
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

}
