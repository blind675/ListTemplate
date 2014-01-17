package com.listtemplate.model;

import android.content.Context;
import com.listtemplate.model.data.type.CurrentlyUsedList;
import com.listtemplate.model.data.type.TemplateRecord;
import com.listtemplate.model.database.DBController;
import com.listtemplate.model.server.WSController;

import java.util.ArrayList;
import java.util.List;

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
        // loadLists();
        // loadTemplates();
    }

    /**
     * Load all the lists from the database
     * @param context the context
     */
    public void loadLists(Context context) {
        // load all the lists
        // overwrite the existing ones
        mOpenLists = DBController.loadLists(context);
        // set the open list index to initial value
        // no list is opened
        mIndexOfTheCurrentlyOpenedList = -1;
    }

    /**
     * Load all the templates from the database
     * @param context the context
     */
    public void loadTemplates(Context context) {
        // load all the templates
        // overwrite the existing ones
        mTemplates = DBController.loadTemplates(context);
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

    //***************************************** TEMPLATES PART ***************************************************//

    /**
     * Save the template to the template list and database.
     * If template with same elements present do nothing.
     * @param templateRecord the record to save
     */
    public void saveTemplate(TemplateRecord templateRecord){
        // run trough mTemplates
        int j,k, numberOfMatchingElements;
        for (TemplateRecord mTemplate : mTemplates) {
            // see if they have the same number of elements
            numberOfMatchingElements = 0;
            if (mTemplate.getNumberOfElements() == templateRecord.getNumberOfElements()) {
                // run trough the elements and see if they repeat
                for (j = 0; j <= templateRecord.getNumberOfElements(); j++) {
                    for (k = 0; k <= templateRecord.getNumberOfElements(); k++) {
                        if (templateRecord.getElement(k).equals(mTemplate.getElement(j))) {
                            numberOfMatchingElements++;
                        }
                    }
                    // if times of element list go trough is different than number of elements that match
                    // jump to the next
                    if (j != numberOfMatchingElements) {
                        break;
                    }
                }

                // if all elements match get out
                if (numberOfMatchingElements == templateRecord.getNumberOfElements()) {
                    return;
                }
            }
        }

        // if is shared active get unique id
        if(templateRecord.getSharedDate() != null){
            templateRecord.setId(WSController.getTemplateID());
        }
        // add template
        mTemplates.add(templateRecord);

        // if share enabled
        if(templateRecord.getSharedDate() != null){
            // do a server sync
            WSController.doSync(mTemplates);
            // update all database
            DBController.updateTemplates(mTemplates);
        } else{
            // just write last to DB
            DBController.writeTemplate(templateRecord);
        }

    }

    /**
     * Get the number of existing templates
     * @return number of existing templates
     */
    public int getNumberOfTemplates(){
        // return the size of mTemplates list
        return mTemplates.size();

    }

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
     * if no list is opened return null
     * @return name of the list
     */
    public String getNameOfTheCurrentList(){
        if(mIndexOfTheCurrentlyOpenedList != -1){
            return mOpenLists.get(mIndexOfTheCurrentlyOpenedList).getName();
        } else {
            return null;
        }
    }

    /**
     * Get the description of the list referenced by mIndexOfTheCurrentlyOpenedList
     * If no list is opened return null
     * @return description of the list
     */
    public String getDescriptionOfTheCurrentList(){
        if(mIndexOfTheCurrentlyOpenedList != -1){
            return mOpenLists.get(mIndexOfTheCurrentlyOpenedList).getDescription();
        } else {
            return null;
        }
    }

    /**
     * Get the number of elements of the list referenced by mIndexOfTheCurrentlyOpenedList
     * If no list is opened return 0
     * @return number of elements of the list
     */
    public int getNumberOfElementsOfTheCurrentList(){
        if(mIndexOfTheCurrentlyOpenedList != -1){
            return mOpenLists.get(mIndexOfTheCurrentlyOpenedList).getNumberOfElements();
        } else {
            return 0;
        }
    }

    /**
     * Get the element of the list referenced by mIndexOfTheCurrentlyOpenedList at the position index
     * If no list is opened return null
     * @param index the position of the element
     * @return the name of the element
     */
    public String getElementOfTheCurrentList(int index){
        // get the element with the index of the mIndexOfTheCurrentlyOpenedList list in mOpenedLists
        if(mIndexOfTheCurrentlyOpenedList != -1){
            return mOpenLists.get(mIndexOfTheCurrentlyOpenedList).getElement(index);
        } else {
            return null;
        }
    }

    /**
     * Check if the state of the element given by name in the list referenced by mIndexOfTheCurrentlyOpenedList
     * is selected or not
     * If no list is opened return false
     * @param elementName the name of the element
     * @return if the element is selected or not
     */
    public boolean isElementSelected(String elementName){
        // get the selected state element of the mIndexOfTheCurrentlyOpenedList list in mOpenedLists
        return mIndexOfTheCurrentlyOpenedList != -1 && mOpenLists.get(mIndexOfTheCurrentlyOpenedList).isItemSelected(elementName);
    }

    /**
     * Change the internal state of the element in the list referenced by mIndexOfTheCurrentlyOpenedList(currently opened list)
     * Does not change the database value
     * If no list is opened do nothing
     * @param element the element whose selected state changes
     */
    public void toggleSelectedForElement(String element){
        if(mIndexOfTheCurrentlyOpenedList != -1){
            mOpenLists.get(mIndexOfTheCurrentlyOpenedList).toggleElementSelected(element);
        }
    }

    //*************************************** CREATING LIST PART **************************************************//

    /**
     * Create a new list from a template referenced by index
     * Add the list to the back of the mOpenLists list.
     * @param index the index of the template to use
     */
    public void createListFromTemplate(int index){
        // create thumbnail from template background
        // create a list from template index + thumbnail
        // add it to the back of the mOpenLists
        // set mIndexOfTheCurrentlyOpenedList to last position (use CreateList method)
        createList(
            mTemplates.get(index).getName(),
            mTemplates.get(index).getDescription(),
            mTemplates.get(index).getBackground());
        // add the elements from the templates
        for (int i=0;i < mTemplates.get(index).getNumberOfElements();i++){
            addElementToTheCurrentList(mTemplates.get(index).getElement(i));
        }
    }

    /**
     * Creates a new list from name and background. Also creates a thumbnail from background.
     * Adds the list to the back of the mOpenLists list.
     * @param name name of the list
     * @param background background of the list
     */
    public void createList(String name, String description, byte[] background) {
        // create thumbnail from background
        //TODO: utilities method that generates a thumbnail
        byte[] thumbnail = null;
        // create a list with name,description,background + thumbnail
        CurrentlyUsedList newList = new CurrentlyUsedList(name,description,background,thumbnail);
        // add it to the back of the mOpenLists
        mOpenLists.add(newList);
        // set mIndexOfTheCurrentlyOpenedList to last position (use openList())
        openList(mOpenLists.size()-1);
    }

    /**
     * Write the list referenced by mIndexOfTheCurrentlyOpenedList to the database.
     * If object already in database do nothing
     * @param context the context
     */
    public void saveList(Context context) {
        DBController.writeList(mOpenLists.get(mIndexOfTheCurrentlyOpenedList),context);
    }

    /**
     * Remove list referenced by mIndexOfTheCurrentlyOpenedList
     * Also remove from database if present
     * @param context the context
     */
    public void discardList(Context context) {
        // remove list from database
        DBController.removeList(mOpenLists.get(mIndexOfTheCurrentlyOpenedList),context);
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
        mOpenLists.get(mIndexOfTheCurrentlyOpenedList).addElement(elementName);
        // this is done implicitly
        // mOpenLists.get(mIndexOfTheCurrentlyOpenedList).setElementSelected(elementName,false);
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
