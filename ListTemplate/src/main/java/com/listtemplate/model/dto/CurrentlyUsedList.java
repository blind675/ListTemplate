package com.listtemplate.model.dto;

import android.media.Image;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Catalin BORA on 12/1/13.
 * Class that stores the currently used list
 */
public class CurrentlyUsedList {
    private static String mName;
    private static LinkedHashMap<String,Boolean> mElements = new LinkedHashMap<String, Boolean>();
    private static Image mBackground = null;
    private static Image mThumbnail = null;

    public CurrentlyUsedList(String name, Image background, Image thumbnail){
        mName = name;
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
     * @return
     */
    public String getName(){
        return mName;
    }

    public Image getBackground(){
        return mBackground;
    }

    public Image getThumbnail(){
        return mThumbnail;
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

            Iterator<String> iterator = mElements.keySet().iterator();
            while (iterator.hasNext()){
                String element = iterator.next();
                if (index == i){
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
}