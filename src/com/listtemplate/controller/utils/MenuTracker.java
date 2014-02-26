package com.listtemplate.controller.utils;

import android.widget.ListView;

/**
 * Created by Catalin BORA on 2/22/14.
 *
 * Class used to keep track of the current fragment selected in the menu
 */
public class MenuTracker {

    private static final MenuTracker INSTANCE = new MenuTracker();
    private int currentFragment;
    private ListView mDrawerList;

    public static final int CURRENT_LIST = 1;
    public static final int HOME = 2;
    public static final int BROWSE_TEMPLATES = 3;
    public static final int CREATE_TEMPLATE = 4;
    public static final int SETTINGS = 5;

    public static MenuTracker getInstance() {
        return INSTANCE;
    }

    private MenuTracker() {
        currentFragment = HOME;
        mDrawerList = null;
    }

    // major hack or not :D
    /**
     * Set the listView of the menu so it's visible from anywhere
     * @param menuList the list view of the menu
     */
    public void setMenuList(ListView menuList){
        mDrawerList = menuList;
    }

    /**
     * Set the opened fragment.
     * @param tab the fragment number
     */
    public void setOpenedFragment(int tab){
        currentFragment = tab;

        // set selected if the list vew is set
        if(mDrawerList != null){
            mDrawerList.setItemChecked(tab-1,true);
        }
    }

    /**
     * Get the opened fragment
     * @return the fragment number
     */
    public int getOpenedFragment(){
        return currentFragment;
    }
}
