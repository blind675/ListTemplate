package com.listtemplate.controller.utils;

/**
 * Created by Catalin BORA on 2/22/14.
 *
 * Class used to keep track of the current fragment selected in the menu
 */
public class MenuTracker {

    private static final MenuTracker INSTANCE = new MenuTracker();
    private int currentFragment;

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
    }

    /**
     * Set the opened fragment.
     * @param tab the fragment number
     */
    public void setOpenedFragment(int tab){
        currentFragment = tab;
    }

    /**
     * Get the opened fragment
     * @return the fragment number
     */
    public int getOpenedFragment(){
        return currentFragment;
    }
}
