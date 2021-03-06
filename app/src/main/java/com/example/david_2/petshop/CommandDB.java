package com.example.david_2.petshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jay Vuong on 6/7/2017.
 */

public class CommandDB extends SQLiteOpenHelper
{
    public final static int VERSION = 1;
    public final static String NAME= "command.db";
    public final static String CID = "CID";
    public final static String TABLE= "COMMANDLIST";
    public final static String COMMAND="COMMAND";
    public final static String PATH="PATH";

    public CommandDB(Context context)
    {
        super(context, NAME, null, VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table " +TABLE +" ("+CID+" integer primary key, "+COMMAND+" text, "+PATH+" text);";
        db.execSQL(sql);

    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists "+TABLE); onCreate(db);
    }
}

