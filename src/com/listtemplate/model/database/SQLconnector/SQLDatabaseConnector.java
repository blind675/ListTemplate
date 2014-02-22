package com.listtemplate.model.database.SQLconnector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 01/17/13
 * Time: 3:34 PM
 */
public class SQLDatabaseConnector extends SQLiteOpenHelper {

    // The fields of the lists table
    public static final String LISTS_COLUMN_NAME = "name";
    public static final String LISTS_COLUMN_DESCRIPTION = "description";
    public static final String LISTS_COLUMN_CREATION_DATE = "creationDate";
    public static final String LISTS_COLUMN_ELEMENTS = "elements";
    public static final String LISTS_COLUMN_SELECTED = "selected";
    public static final String LISTS_COLUMN_PICTURE = "picture";
    public static final String LISTS_COLUMN_THUMB = "thumbnail";

    public static final String TABLE_LISTS = "lists";

    // Lists database creation sql statement
    private static final String LISTS_DATABASE_CREATE = "create table "
            + TABLE_LISTS + "(" +
            LISTS_COLUMN_NAME + " varchar(20) primary key not null , " +
            LISTS_COLUMN_DESCRIPTION + " varchar(200) ," +
            LISTS_COLUMN_CREATION_DATE + " bigint ," +
            LISTS_COLUMN_ELEMENTS + " varchar(2500) ," +
            LISTS_COLUMN_SELECTED + " varchar(500),"+
            LISTS_COLUMN_PICTURE + "  blob, " +
            LISTS_COLUMN_THUMB + " blob );";


    // The fields of the template table
    public static final String TEMPLATE_COLUMN_ID_KEY = "key";
    public static final String TEMPLATE_COLUMN_ID = "id";
    public static final String TEMPLATE_COLUMN_USED_INCREMENT = "used_increment";
    public static final String TEMPLATE_COLUMN_NAME = "name";
    public static final String TEMPLATE_COLUMN_RATING = "rating";
    public static final String TEMPLATE_COLUMN_DESCRIPTION = "description";
    public static final String TEMPLATE_COLUMN_USED_PERSONAL = "used_personal";
    public static final String TEMPLATE_COLUMN_USED_ALL = "used_all";
    public static final String TEMPLATE_COLUMN_AUTHOR = "author";
    public static final String TEMPLATE_COLUMN_AUTHOR_EMAIL = "author_email";
    public static final String TEMPLATE_COLUMN_SEND_MAIL = "send_mail";
    public static final String TEMPLATE_COLUMN_SHARED_DATE = "shared_date";
    public static final String TEMPLATE_COLUMN_ELEMENTS = "elements";
    public static final String TEMPLATE_COLUMN_TAGS = "tags";
    public static final String TEMPLATE_COLUMN_PICTURE = "picture";

    public static final String TABLE_TEMPLATES = "templates";

    // Template database creation sql statement
    private static final String TEMPLATE_DATABASE_CREATE = "create table "
            + TABLE_TEMPLATES + "(" +
            TEMPLATE_COLUMN_ID_KEY + " varchar(30) primary key not null," +
            TEMPLATE_COLUMN_ID + " int ," +
            TEMPLATE_COLUMN_USED_INCREMENT + " int," +
            TEMPLATE_COLUMN_NAME + " varchar(20) ," +
            TEMPLATE_COLUMN_RATING + " int ,"+
            TEMPLATE_COLUMN_DESCRIPTION + " varchar(200) ,"+
            TEMPLATE_COLUMN_USED_PERSONAL + " int ,"+
            TEMPLATE_COLUMN_USED_ALL + " int ,"+
            TEMPLATE_COLUMN_AUTHOR + " varchar(50) ,"+
            TEMPLATE_COLUMN_AUTHOR_EMAIL + " varchar(100) ,"+
            TEMPLATE_COLUMN_SEND_MAIL + " bit ,"+
            TEMPLATE_COLUMN_SHARED_DATE + " date ,"+
            TEMPLATE_COLUMN_ELEMENTS + " varchar(2000) ," +
            TEMPLATE_COLUMN_TAGS + " varchar(500),"+
            TEMPLATE_COLUMN_PICTURE + "  blob );";


    private static final String DATABASE_NAME = "listtemplate.db";

    private static final int DATABASE_VERSION = 1;

    public SQLDatabaseConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create lists table
        sqLiteDatabase.execSQL(LISTS_DATABASE_CREATE);
        // create template table
        sqLiteDatabase.execSQL(TEMPLATE_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // drop lists table
        Log.w(SQLDatabaseConnector.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTS);
        // drop template table
        Log.w(SQLDatabaseConnector.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPLATES);

        onCreate(sqLiteDatabase);
    }

}