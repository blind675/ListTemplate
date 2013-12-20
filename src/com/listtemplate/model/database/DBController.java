package com.listtemplate.model.database;

import android.content.Context;
import com.listtemplate.model.data.type.CurrentlyUsedList;
import com.listtemplate.model.data.type.TemplateRecord;

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
     * @param context the context
     */
    public static void removeList(CurrentlyUsedList listToRemove, Context context) {
        // delegate
        ListController.removeList(listToRemove,context);
    }

    /**
     * Write the list to the database.
     * If present do nothing.
     * @param listToWrite the list to be written
     * @param context the context
     */
    public static void writeList(CurrentlyUsedList listToWrite, Context context){
        // delegate
        ListController.writeList(listToWrite,context);
    }

    /**
     * Write the template to the database.
     * If present do nothing.
     * @param templateToWrite the template to be written
     */
    public static void writeTemplate(TemplateRecord templateToWrite){
        // TODO: implement method
    }

    /**
     * Load all the lists from the database
     * @param context the context
     * @return list of the lists :)
     */
    public static List<CurrentlyUsedList> loadLists(Context context){
        // delegate
        return ListController.loadLists(context);
    }

    /**
     * Load all templates from the database
     * @param context the context
     * @return list of the templates :)
     */
    public static List<TemplateRecord> loadTemplates(Context context) {
       return TemplateController.loadTemplates(context);
    }

    /**
     * Update all the database for templates
     * @param listOfTemplates current list of templates
     */
    public static void updateTemplates(List<TemplateRecord> listOfTemplates){
        // TODO: implement method
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
