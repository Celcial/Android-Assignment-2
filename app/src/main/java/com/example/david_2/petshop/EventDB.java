package com.example.david_2.petshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jay Vuong on 6/7/2017.
 */

public class EventDB extends SQLiteOpenHelper
{
    public final static int VERSION = 1;
    public final static String NAME= "event.db";
    public final static String CID = "CID";
    public final static String TABLE= "EVENT";
    public final static String TITLE="TITLE";
    public final static String DATE="DATE";
    public final static String NOTE="NOTE";

    public EventDB(Context context)
    {
        super(context, NAME, null, VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table " +TABLE +" ("+CID+" integer primary key, "+TITLE+" text, "+DATE+" text, "+NOTE+" text);";
        db.execSQL(sql);

    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists "+TABLE); onCreate(db);
    }
}
