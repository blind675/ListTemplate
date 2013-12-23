package com.listtemplate.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.listtemplate.model.data.type.CurrentlyUsedList;
import com.listtemplate.model.database.SQLconnector.SQLListHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/13/13
 * Time: 4:47 PM
 */
public class ListController {

    private static final String[] mAllColumns = {
            SQLListHelper.COLUMN_NAME,
            SQLListHelper.COLUMN_DESCRIPTION,
            SQLListHelper.COLUMN_ELEMENTS,
            SQLListHelper.COLUMN_SELECTED,
            SQLListHelper.COLUMN_PICTURE,
            SQLListHelper.COLUMN_THUMB,
    };

    private static SQLiteDatabase mDatabase;
    private static SQLListHelper mDbHelper;

    /**
     * Load all the lists from the database
     * @param context the context
     * @return list of the lists :)
     */
    public static List<CurrentlyUsedList> loadLists(Context context){

        List<CurrentlyUsedList> returnList = new ArrayList<CurrentlyUsedList>();

        // lazy instantiating
        if (mDbHelper == null) {
            mDbHelper = new SQLListHelper(context);
            mDatabase = mDbHelper.getWritableDatabase();
        }

        Cursor cursor = mDatabase.query(SQLListHelper.TABLE_LISTS, mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            // get the name of the list
            String name = cursor.getString(0);
            // get the description of the list
            String description = cursor.getString(1);
            // get the element string
            String elements = cursor.getString(2);
            // get the selected string
            String selected = cursor.getString(3);
            // get the picture of the list
            byte[] picture = cursor.getBlob(4);
            // get the thumbnail of the list
            byte[] thumbnail = cursor.getBlob(5);

            CurrentlyUsedList listRecord = new CurrentlyUsedList(name,description,picture,thumbnail);

            String[] elementsList = elements.split(",");
            String[] selectedList = selected.split(",");

            for(String element: elementsList ){
                listRecord.addElement(element);
            }

            int i=0;
            for(String select: selectedList ){
                listRecord.setElementSelected(listRecord.getElement(i), select.equals("1"));
            }

            returnList.add(listRecord);

            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return returnList;
    }

    /**
     * Write the list to the database.
     * If present do nothing.
     * @param listToWrite the list to be written
     * @param context the context
     */
    public static void writeList(CurrentlyUsedList listToWrite, Context context){

        // lazy instantiating
        if (mDbHelper == null) {
            mDbHelper = new SQLListHelper(context);
            mDatabase = mDbHelper.getWritableDatabase();
        }
        // test if already in database
        // this is done implicitly by making name a unique key
        // no 2 same keys can be the same (unique)

        ContentValues values = new ContentValues();
        values.put(SQLListHelper.COLUMN_NAME,listToWrite.getName());
        values.put(SQLListHelper.COLUMN_DESCRIPTION,listToWrite.getDescription());

        values.put(SQLListHelper.COLUMN_ELEMENTS,listToWrite.getElementsString());
        values.put(SQLListHelper.COLUMN_SELECTED,listToWrite.getSelectedString());

        values.put(SQLListHelper.COLUMN_PICTURE,listToWrite.getBackground());
        values.put(SQLListHelper.COLUMN_THUMB,listToWrite.getThumbnail());
        mDatabase.insert(SQLListHelper.TABLE_LISTS, null, values);

    }

    /**
     * Remove the list from the database if present.
     * If not present do nothing.
     * @param listToRemove the list to be removed
     * @param context the context
     */
    public static void removeList(CurrentlyUsedList listToRemove, Context context) {

        // lazy instantiating
        if (mDbHelper == null) {
            mDbHelper = new SQLListHelper(context);
            mDatabase = mDbHelper.getWritableDatabase();
        }

        mDatabase.delete(SQLListHelper.TABLE_LISTS,
                SQLListHelper.COLUMN_NAME + " = \'" + listToRemove.getName() + "\'", null);
    }
}
