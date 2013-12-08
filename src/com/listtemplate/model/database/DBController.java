package com.listtemplate.model.database;

import com.listtemplate.model.data.type.CurrentlyUsedList;
import com.listtemplate.model.data.type.TemplateRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/6/13
 * Time: 2:00 PM
 */
public class DBController {

    /**
     * Remove the list from the database if present.
     * If not present do nothing.
     * @param listToRemove the list to be removed
     */
    public static void removeList(CurrentlyUsedList listToRemove) {
        // TODO: implement method
    }

    /**
     * Write the list to the database.
     * If present do nothing.
     * @param listToWrite the list to be written
     */
    public static void writeList(CurrentlyUsedList listToWrite){
        // TODO: implement method
    }

    /**
     * Load all the lists from the database
     * @return list of the lists :)
     */
    public static List<CurrentlyUsedList> loadLists(){
        // TODO: implement method
        return new ArrayList<CurrentlyUsedList>();
    }

    /**
     * Load all templates from the database
     * @return
     */
    public static List<TemplateRecord> loadTemplates() {
       // TODO: implement method
       return new ArrayList<TemplateRecord>();
    }

    /**
     * Delete all the data
     */
    public static void deleteAll(){
        // remove lists
        // remove templates
        // remove stored data
        // TODO: implement method
    }


}
