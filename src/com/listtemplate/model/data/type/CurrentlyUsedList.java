package com.listtemplate.model.data.type;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by Catalin BORA on 12/1/13.
 * Class that stores the currently used list
 */
public class CurrentlyUsedList {
    private final String mName;
    private final String mDescription;
    private final Date mCreationDate;
    // really bad .. use 2 lists
    private final LinkedHashMap<String,Boolean> mElements = new LinkedHashMap<String, Boolean>();
    private final byte[] mBackground;
    private final byte[] mThumbnail;

    public CurrentlyUsedList(String name, String description, Date creationDate, byte[] background, byte[] thumbnail){
        mName = name;
        mDescription = description;
        mBackground = background;
        mThumbnail = thumbnail;
        mCreationDate = creationDate;
    }

    /**
     * Add an element to the list, and it's state to the list
     * @param key element name
     */
    public void addElement(String key){
        mElements.put(key,false);
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

    /**
     * Get the creation date of the list
     * @return creation date of the list
     */
    public Date getCreationDate() {
        return mCreationDate;
    }

    public byte[] getBackground(){
        return mBackground;
    }

    public byte[] getThumbnail(){
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

    /**
     * Generates an elements name string used for database storing
     * @return generated string
     */
    public String getElementsString(){
        String returnString = "";
        for (String element : mElements.keySet()) {
            returnString += element + ",";
        }
        return returnString;
    }

    /**
     * Generates an elements selected state string used for database storing
     * @return generated string
     */
    public String getSelectedString(){
        String returnString = "";
        for (String element : mElements.keySet()) {
            returnString += (isItemSelected(element))?"1":"0" + ",";
        }
        return returnString;
    }
}