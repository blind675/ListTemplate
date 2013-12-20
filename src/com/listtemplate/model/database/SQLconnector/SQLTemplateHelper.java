package com.listtemplate.model.database.SQLconnector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Catalin BORA
 * Date: 12/18/13
 * Time: 3:20 PM
 */
public class SQLTemplateHelper extends SQLiteOpenHelper {

    // The fields
    public static final String COLUMN_ID_KEY = "key";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USED_INCREMENT = "used_increment";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_USED_PERSONAL = "used_personal";
    public static final String COLUMN_USED_ALL = "used_all";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_AUTHOR_EMAIL = "author_email";
    public static final String COLUMN_SEND_MAIL = "send_mail";
    public static final String COLUMN_SHARED_DATE = "shared_date";
    public static final String COLUMN_ELEMENTS = "elements";
    public static final String COLUMN_TAGS = "tags";
    public static final String COLUMN_PICTURE = "picture";

    public static final String TABLE_TEMPLATES = "templates";

    private static final String DATABASE_NAME = "listtemplate.db";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TEMPLATES + "(" +
            COLUMN_ID_KEY + " varchar(30) primary key not null," +
            COLUMN_ID + " int ," +
            COLUMN_USED_INCREMENT + " int," +
            COLUMN_NAME + " varchar(20) ," +
            COLUMN_RATING + " int ,"+
            COLUMN_DESCRIPTION + " varchar(200) ,"+
            COLUMN_USED_PERSONAL + " int ,"+
            COLUMN_USED_ALL + " int ,"+
            COLUMN_AUTHOR + " varchar(50) ,"+
            COLUMN_AUTHOR_EMAIL + " varchar(100) ,"+
            COLUMN_SEND_MAIL + " bit ,"+
            COLUMN_SHARED_DATE + " date ,"+
            COLUMN_ELEMENTS + " varchar(2000) ," +
            COLUMN_TAGS + " varchar(500),"+
            COLUMN_PICTURE + "  blob );";

    private static final int DATABASE_VERSION = 1;

    public SQLTemplateHelper(Context context) {
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPLATES);
        onCreate(sqLiteDatabase);
    }

}
