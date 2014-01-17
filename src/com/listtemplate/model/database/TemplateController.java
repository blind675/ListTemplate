package com.listtemplate.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.listtemplate.model.data.type.TemplateRecord;
import com.listtemplate.model.database.SQLconnector.SQLDatabaseConnector;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/18/13
 * Time: 3:16 PM
 */
public class TemplateController {

    private static final String[] mAllColumns = {
            SQLDatabaseConnector.TEMPLATE_COLUMN_ID_KEY,
            SQLDatabaseConnector.TEMPLATE_COLUMN_ID,
            SQLDatabaseConnector.TEMPLATE_COLUMN_USED_INCREMENT,
            SQLDatabaseConnector.TEMPLATE_COLUMN_NAME,
            SQLDatabaseConnector.TEMPLATE_COLUMN_RATING,
            SQLDatabaseConnector.TEMPLATE_COLUMN_DESCRIPTION,
            SQLDatabaseConnector.TEMPLATE_COLUMN_USED_PERSONAL,
            SQLDatabaseConnector.TEMPLATE_COLUMN_USED_ALL,
            SQLDatabaseConnector.TEMPLATE_COLUMN_AUTHOR,
            SQLDatabaseConnector.TEMPLATE_COLUMN_AUTHOR_EMAIL,
            SQLDatabaseConnector.TEMPLATE_COLUMN_SEND_MAIL,
            SQLDatabaseConnector.TEMPLATE_COLUMN_SHARED_DATE,
            SQLDatabaseConnector.TEMPLATE_COLUMN_ELEMENTS,
            SQLDatabaseConnector.TEMPLATE_COLUMN_TAGS,
            SQLDatabaseConnector.TEMPLATE_COLUMN_PICTURE
    };

    private static SQLiteDatabase mDatabase;
    private static SQLDatabaseConnector mDbHelper;

    public static List<TemplateRecord> loadTemplates(Context context){

        List<TemplateRecord> returnTemplateList = new ArrayList<TemplateRecord>();

        // lazy instantiating
        if (mDbHelper == null) {
            mDbHelper = new SQLDatabaseConnector(context);
            mDatabase = mDbHelper.getWritableDatabase();
        }

        Cursor cursor = null;
        if (mDatabase != null) {
            cursor = mDatabase.query(SQLDatabaseConnector.TABLE_TEMPLATES, mAllColumns, null, null, null, null, null);
        }

        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                // get the key of the list
                String key = cursor.getString(0);
                // get the id of the list
                int id = cursor.getInt(1);
                // get the used_increment of the list
                int used_increment = cursor.getInt(2);
                // get the name of the list
                String name = cursor.getString(3);
                // get the rating of the list
                int rating = cursor.getInt(4);
                // get the description of the list
                String description = cursor.getString(5);
                // get the used_personal of the list
                int used_personal = cursor.getInt(6);
                // get the used_all of the list
                int used_all = cursor.getInt(7);
                // get the author string
                String author = cursor.getString(8);
                // get the author_email string
                String author_email = cursor.getString(9);
                // get the send_mail of the list
                short send_mail = cursor.getShort(10);
                // get the shared_date of the list
                Date shared_date = null;
                //new Date(cursor.getString(12).toString());
                // get the element string
                String elements = cursor.getString(12);
                // get the tags string
                String tags = cursor.getString(13);
                // get the picture of the list
                byte[] picture = cursor.getBlob(14);

                TemplateRecord templateRecord = new TemplateRecord(used_increment,name,description,rating,used_personal,used_all,author,author_email,(send_mail==1)?true:false,shared_date,picture);
                templateRecord.setId(id);

                String[] elementsList = elements.split(",");
                String[] tagsList = tags.split(",");

                for(String element: elementsList ){
                    templateRecord.addElement(element);
                }

                for(String tag: tagsList ){
                    templateRecord.addTag(tag);
                }

                returnTemplateList.add(templateRecord);
                cursor.moveToNext();

            }

            // make sure to close the cursor
            cursor.close();
            }

        return returnTemplateList;
    }

}
