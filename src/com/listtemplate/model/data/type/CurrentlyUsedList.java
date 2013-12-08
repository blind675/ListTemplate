package com.listtemplate.model.data.type;

import android.graphics.Bitmap;

import java.util.LinkedHashMap;

/**
 * Created by Catalin BORA on 12/1/13.
 * Class that stores the currently used list
 */
public class CurrentlyUsedList {
    private final String mName;
    private final String mDescription;
    private final LinkedHashMap<String,Boolean> mElements = new LinkedHashMap<String, Boolean>();
    private final Bitmap mBackground;
    private final Bitmap mThumbnail;

    public CurrentlyUsedList(String name, String description,Bitmap background, Bitmap thumbnail){
        mName = name;
        mDescription = description;
        mBackground = background;
        mThumbnail = thumbnail;
    }

    /**
     * Add an element to the list, and it's state to the list
     * @param key element name
     * @param isSelected selected or not
     */
    public void addElement(String key,boolean isSelected){
        mElements.put(key,isSelected);
    }

    /**
     * Remove an element from the list
     * @param key the element name
     */
    public void removeElement(String key){
        mElements.remove(key);
    }

    /**
     * Get the name of the list
     * @return name of the list
     */
    public String getName(){
        return mName;
    }

    /**
     * Get the description of the list
     * @return description of the list
     */
    public String getDescription(){
        return mDescription;
    }

    public Bitmap getBackground(){
        return mBackground;
    }

    public Bitmap getThumbnail(){
        return mThumbnail;
    }

    public int getNumberOfElements(){
        return mElements.size();
    }

    /**
     * Check the state of an element in the list
     * @param key element name
     * @return if selected or not
     */
    public boolean isItemSelected(String key){
        if (!mElements.isEmpty()){
            return mElements.get(key);
        } else {
            return false;
        }
    }

    /**
     * Get the name of an element at a certain position in the list.
     * I'm using LinkedHashMap as a list and that's bad .
     * @param index the position
     * @return name of the element
     */
    public String getElement(int index){
        if(!mElements.isEmpty()){
            int i=0;

            for (String element : mElements.keySet()) {
                if (index == i) {
                    return element;
                } else {
                    i++;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * Change the value of an element given by key
     * @param key the name of the element
     * @param newValue the new value
     */
    public void setElementSelected(String key,boolean newValue){
        mElements.put(key,newValue);
    }

    /**
     * Toggle between true and false the selected value of an element
     * @param key name of the element
     */
    public void toggleElementSelected(String key){
        mElements.put(key,!isItemSelected(key));
    }
}