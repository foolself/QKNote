package com.foolself.root.qknote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 16-4-16.
 */
public class NoteDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "notes";
    public static final String ID = "_id";
    public static final String ATTR = "attr";
    public static final String TIME = "time";
    public static final String CONTENT = "content";

    public NoteDB(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(" +
        ID + " integer primary key autoincrement," +
        ATTR + " integer not null," +
        TIME + " text not null," +
        CONTENT + " text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
