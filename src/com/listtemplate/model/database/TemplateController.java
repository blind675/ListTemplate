package com.listtemplate.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.listtemplate.model.data.type.TemplateRecord;
import com.listtemplate.model.database.SQLconnector.SQLTemplateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/18/13
 * Time: 3:16 PM
 */
public class TemplateController {

    private static final String[] mAllColumns = {
            SQLTemplateHelper.COLUMN_ID_KEY,
            SQLTemplateHelper.COLUMN_ID,
            SQLTemplateHelper.COLUMN_USED_INCREMENT,
            SQLTemplateHelper.COLUMN_NAME,
            SQLTemplateHelper.COLUMN_RATING,
            SQLTemplateHelper.COLUMN_DESCRIPTION,
            SQLTemplateHelper.COLUMN_USED_PERSONAL,
            SQLTemplateHelper.COLUMN_USED_ALL,
            SQLTemplateHelper.COLUMN_AUTHOR,
            SQLTemplateHelper.COLUMN_AUTHOR_EMAIL,
            SQLTemplateHelper.COLUMN_SEND_MAIL,
            SQLTemplateHelper.COLUMN_SHARED_DATE,
            SQLTemplateHelper.COLUMN_ELEMENTS,
            SQLTemplateHelper.COLUMN_TAGS,
            SQLTemplateHelper.COLUMN_PICTURE
    };

    private static SQLiteDatabase mDatabase;
    private static SQLTemplateHelper mDbHelper;

    public static List<TemplateRecord> loadTemplates(Context context){

        List<TemplateRecord> returnTemplateList = new ArrayList<TemplateRecord>();

        // lazy instantiating
        if (mDbHelper == null) {
            mDbHelper = new SQLTemplateHelper(context);
            mDatabase = mDbHelper.getWritableDatabase();
        }

        Cursor cursor = mDatabase.query(SQLTemplateHelper.TABLE_TEMPLATES, mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            // get the key of the list
            String key = cursor.getString(1);
            // get the id of the list
            int id = cursor.getInt(2);
            // get the used_increment of the list
            int used_increment = cursor.getInt(3);
            // get the name of the list
            String name = cursor.getString(4);
            // get the rating of the list
            int rating = cursor.getInt(5);
            // get the description of the list
            String description = cursor.getString(6);
            // get the used_personal of the list
            int used_personal = cursor.getInt(7);
            // get the used_all of the list
            int used_all = cursor.getInt(8);
            // get the author string
            String author = cursor.getString(9);
            // get the author_email string
            String author_email = cursor.getString(10);
            // get the send_mail of the list
            short send_mail = cursor.getShort(11);
            // get the shared_date of the list
            //Date shared_date = cursor.getString(12);
            // get the element string
            String elements = cursor.getString(13);
            // get the tags string
            String tags = cursor.getString(14);
            // get the picture of the list
            byte[] picture = cursor.getBlob(15);

            //TemplateRecord templateRecord = new TemplateRecord();


        }

        return returnTemplateList;

    }
}
