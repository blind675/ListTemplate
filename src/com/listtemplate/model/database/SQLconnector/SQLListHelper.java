package com.listtemplate.model.database.SQLconnector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/13/13
 * Time: 4:34 PM
 */
public class SQLListHelper extends SQLiteOpenHelper {

    // The fields
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ELEMENTS = "elements";
    public static final String COLUMN_SELECTED = "selected";
    public static final String COLUMN_PICTURE = "picture";
    public static final String COLUMN_THUMB = "thumbnail";

    public static final String TABLE_LISTS = "lists";

    private static final String DATABASE_NAME = "listtemplate.db";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_LISTS + "(" +
            COLUMN_NAME + " varchar(20) primary key not null , " +
            COLUMN_DESCRIPTION + " varchar(200) ,"+
            COLUMN_ELEMENTS + " varchar(2500) ," +
            COLUMN_SELECTED + " varchar(500),"+
            COLUMN_PICTURE + "  blob, " +
            COLUMN_THUMB + " blob );";

    private static final int DATABASE_VERSION = 1;

    public SQLListHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(SQLListHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTS);
        onCreate(sqLiteDatabase);
    }
}
