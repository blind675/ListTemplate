package com.listtemplate.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import com.listtemplate.model.database.DBController;
import com.listtemplate.model.data.type.CurrentlyUsedList;
import com.listtemplate.model.data.type.TemplateRecord;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/4/13
 * Time: 10:42 PM
 */
public class AppModel {
    private static final AppModel INSTANCE = new AppModel();

    private List<CurrentlyUsedList> mOpenLists = new ArrayList<CurrentlyUsedList>();
    private List<TemplateRecord> mTemplates = new ArrayList<TemplateRecord>();
    private int mIndexOfTheCurrentlyOpenedList = -1;

    public static AppModel getInstance() {
        return INSTANCE;
    }

    private AppModel() {
        // load everything
        loadLists();
        loadTemplates();
    }

    /**
     * Load all the lists from the database
     */
    public void loadLists() {
        // load all the lists
        // overwrite the existing ones
        mOpenLists = DBController.loadLists();
        // set the open list index to initial value
        // no list is opened
        mIndexOfTheCurrentlyOpenedList = -1;
    }

    /**
     * Load all the templates from the database
     */
    public void loadTemplates() {
        // load all the templates
        // overwrite the existing ones
        mTemplates = DBController.loadTemplates();
    }

    //**************************************** TESTING UTILS *****************************************************//
    // Clear singleton fields
    public void clearAll(){
        mOpenLists = new ArrayList<CurrentlyUsedList>();
        mTemplates = new ArrayList<TemplateRecord>();
        mIndexOfTheCurrentlyOpenedList = -1;
    }

    // Clear singleton fields and delete from database
    public void deleteAll(){
       clearAll();
       DBController.deleteAll();
    }

    //************************************* USING TEMPLATES PART ************************************************//

    //**************************************** USING LIST PART ***************************************************//

    /**
     * Get the number of existing lists
     * @return number of existing lists
     */
    public int getNumberOfLists(){
        // return the size of mOpenLists list
        return mOpenLists.size();
    }

    /**
     * Opens the list by setting the mIndexOfTheCurrentlyOpenedList
     * @param index the list index in mOpenLists
     */
    public void openList(int index){
        // set mIndexOfTheCurrentlyOpenedList
        mIndexOfTheCurrentlyOpenedList = index;
    }

    /**
     * Get the name of the list referenced by mIndexOfTheCurrentlyOpenedList
     * @return name of the list
     */
    public String getNameOfTheCurrentList(){
        return mOpenLists.get(mIndexOfTheCurrentlyOpenedList).getName();
    }

    /**
     * Get the description of the list referenced by mIndexOfTheCurrentlyOpenedList
     * @return description of the list
     */
    public String getDescriptionOfTheCurrentList(){
        return mOpenLists.get(mIndexOfTheCurrentlyOpenedList).getDescription();
    }

    /**
     * Get the number of elements of the list referenced by mIndexOfTheCurrentlyOpenedList
     * @return number of elements of the list
     */
    public int getNumberOfElementsOfTheCurrentList(){
        return mOpenLists.get(mIndexOfTheCurrentlyOpenedList).getNumberOfElements();
    }

    /**
     * Get the element of the list referenced by mIndexOfTheCurrentlyOpenedList at the position index
     * @param index the position of the element
     * @return the name of the element
     */
    public String getElementOfTheCurrentList(int index){
        // get the element with the index of the mIndexOfTheCurrentlyOpenedList list in mOpenedLists
        return mOpenLists.get(mIndexOfTheCurrentlyOpenedList).getElement(index);
    }

    /**
     * Check if the state of the element given by name in the list referenced by mIndexOfTheCurrentlyOpenedList
     * is selected or not
     * @param elementName the name of the element
     * @return if the element is selected or not
     */
    public boolean isElementSelected(String elementName){
        // get the selected state element of the mIndexOfTheCurrentlyOpenedList list in mOpenedLists
        return mOpenLists.get(mIndexOfTheCurrentlyOpenedList).isItemSelected(elementName);
    }

    //*************************************** CREATING LIST PART **************************************************//
    public void createListFromTemplate(int index){
        // create thumbnail from template background
        // create a list from template index + thumbnail (use CreateList method)
        // add it to the back of the mOpenLists
        // set mIndexOfTheCurrentlyOpenedList to last position
    }

    /**
     * Creates a new list from name and background. Also creates a thumbnail from background.
     * Adds the list to the back of the mOpenLists list.
     * @param name name of the list
     * @param background background of the list
     */
    public void createList(String name, String description, Bitmap background) {
        // create thumbnail from background
        //TODO: utilities method that generates a thumbnail
        Bitmap thumbnail = null;
        // create a list with name,description,background + thumbnail
        CurrentlyUsedList newList = new CurrentlyUsedList(name,description,background,thumbnail);
        // add it to the back of the mOpenLists
        mOpenLists.add(newList);
        // set mIndexOfTheCurrentlyOpenedList to last position (use openList())
        openList(mOpenLists.size()-1);
    }

    /**
     * Write the list referenced by mIndexOfTheCurrentlyOpenedList to the database.
     * If object already in DB d nothing
     */
    public void useList() {
        DBController.writeList(mOpenLists.get(mIndexOfTheCurrentlyOpenedList));
    }

    /**
     * Remove list referenced by mIndexOfTheCurrentlyOpenedList
     * Also remove from database if present
     */
    public void discardList() {
        // remove list from database
        DBController.removeList(mOpenLists.get(mIndexOfTheCurrentlyOpenedList));
        // remove last record of mOpenLists
        mOpenLists.remove(mIndexOfTheCurrentlyOpenedList);
    }

    /**
     * Add an element to the list referenced by mIndexOfTheCurrentlyOpenedList
     * @param elementName name of the element to add
     */
    public void addElementToTheCurrentList(String elementName){
        // add an element
        // since is a new element is never selected (isSelected  = false)
        mOpenLists.get(mIndexOfTheCurrentlyOpenedList).addElement(elementName,false);
    }

    /**
     * Removes an element from the list referenced by mIndexOfTheCurrentlyOpenedList given by name
     * @param elementName name of the element to remove
     */
    public void removeElementFromTheCurrentList(String elementName){
        // remove an element from the last list from mOpenLists
        mOpenLists.get(mIndexOfTheCurrentlyOpenedList).removeElement(elementName);
    }
}
