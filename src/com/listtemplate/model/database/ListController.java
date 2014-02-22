package com.listtemplate.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.listtemplate.model.data.type.CurrentlyUsedList;
import com.listtemplate.model.database.SQLconnector.SQLDatabaseConnector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/13/13
 * Time: 4:47 PM
 */
public class ListController {

    private static final String[] mAllColumns = {
            SQLDatabaseConnector.LISTS_COLUMN_NAME,
            SQLDatabaseConnector.LISTS_COLUMN_DESCRIPTION,
            SQLDatabaseConnector.LISTS_COLUMN_CREATION_DATE,
            SQLDatabaseConnector.LISTS_COLUMN_ELEMENTS,
            SQLDatabaseConnector.LISTS_COLUMN_SELECTED,
            SQLDatabaseConnector.LISTS_COLUMN_PICTURE,
            SQLDatabaseConnector.LISTS_COLUMN_THUMB,
    };

    private static SQLiteDatabase mDatabase;
    private static SQLDatabaseConnector mDbHelper;

    /////////////////////////////////////// this is a well commented line

    /**
     * Load all the lists from the database
     * @param context the context
     * @return list of the lists :)
     */
    public static List<CurrentlyUsedList> loadLists(Context context){

        List<CurrentlyUsedList> returnList = new ArrayList<CurrentlyUsedList>();

        // lazy instantiating
        if (mDbHelper == null) {
            mDbHelper = new SQLDatabaseConnector(context);
            mDatabase = mDbHelper.getWritableDatabase();
        }

        Cursor cursor = null;
        if (mDatabase != null) {
            cursor = mDatabase.query(SQLDatabaseConnector.TABLE_LISTS, mAllColumns, null, null, null, null, null);
        }

        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                // get the name of the list
                String name = cursor.getString(0);
                // get the description of the list
                String description = cursor.getString(1);
                // get the creation date
                Date creationDate = new Date();
                creationDate.setTime(cursor.getLong(2));
                // get the element string
                String elements = cursor.getString(3);
                // get the selected string
                String selected = cursor.getString(4);
                // get the picture of the list
                byte[] picture = cursor.getBlob(5);
                // get the thumbnail of the list
                byte[] thumbnail = cursor.getBlob(6);

                CurrentlyUsedList listRecord = new CurrentlyUsedList(name,description,creationDate,picture,thumbnail);

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
        }

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
            mDbHelper = new SQLDatabaseConnector(context);
            mDatabase = mDbHelper.getWritableDatabase();
        }
        // test if already in database
        // this is done implicitly by making name a unique key
        // no 2 same keys can be the same (unique)

        ContentValues values = new ContentValues();
        values.put(SQLDatabaseConnector.LISTS_COLUMN_NAME,listToWrite.getName());
        values.put(SQLDatabaseConnector.LISTS_COLUMN_DESCRIPTION,listToWrite.getDescription());

        values.put(SQLDatabaseConnector.LISTS_COLUMN_CREATION_DATE,listToWrite.getCreationDate().getTime());

        values.put(SQLDatabaseConnector.LISTS_COLUMN_ELEMENTS,listToWrite.getElementsString());
        values.put(SQLDatabaseConnector.LISTS_COLUMN_SELECTED,listToWrite.getSelectedString());

        values.put(SQLDatabaseConnector.LISTS_COLUMN_PICTURE,listToWrite.getBackground());
        values.put(SQLDatabaseConnector.LISTS_COLUMN_THUMB,listToWrite.getThumbnail());
        mDatabase.insert(SQLDatabaseConnector.TABLE_LISTS, null, values);

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
            mDbHelper = new SQLDatabaseConnector(context);
            mDatabase = mDbHelper.getWritableDatabase();
        }

        mDatabase.delete(SQLDatabaseConnector.TABLE_LISTS,
                SQLDatabaseConnector.LISTS_COLUMN_NAME + " = \'" + listToRemove.getName() + "\'", null);
    }
}
