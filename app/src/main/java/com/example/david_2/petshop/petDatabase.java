package com.example.david_2.petshop;

/**
 * Created by David_2 on 5/06/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by David_2 on 2/06/2017.
 */

public class petDatabase extends SQLiteOpenHelper {

    // Database general details
    public static final int database_version = 1;
    public static final String database_name = "allDogs.db";

    // Dog table details
    public static final String table_dog = "DOG";
    public static final String col_1 = "PETID";
    public static final String col_2 = "PETNAME";
    public static final String col_3 = "WEIGHT";
    public static final String col_4 = "HEIGHT"; // add MORE SHIT
    // Command table details
    public static final String table_command = "COMMAND";
    public static final String col_11 = "COMMANDID";
    public static final String col_12 = "PETID";
    public static final String col_13 = "CNAME";
    public static final String col_14 = "PATHWAY";
    // Event table details
    public static final String table_event = "EVENT";
    public static final String col_15 = "EVENTID";
    public static final String col_16 = "NAME";
    public static final String col_17 = "DATE";
    public static final String col_18 = "NOTES";
    public static final String col_19 = "PETID";

    public petDatabase(Context context) {

        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // - - - CREATE dog table - - - //
        String create_dog_table = ("create table " + table_dog +
                " ("
                + col_1 + " integer primary key autoincrement, "
                + col_2 + " text, "
                + col_3  + " text, "
                + col_4  + " text) ");
        db.execSQL(create_dog_table);

        String create_command_table = ("create table " + table_command
                + " ("
                + col_11 + " integer autoincrement, "
                + col_12 + " integer, "
                + col_13 + " text, "
                + col_14 + " text, "
                + "FOREIGN KEY (" + col_12 + ") REFERENCES " + table_dog + " (" + col_1 + "), "
                + "PRIMARY KEY (" + col_11 + " , " + col_12 + " )) ");


        String create_event_table = ("create table " + table_event
                + " ("
                + col_15 + "integer autoincrement, "
                + col_16 + "text, "
                + col_17 + "text, "
                + col_18 + "text, "
                + col_19 + "integer, "
                + " FOREIGN KEY (" + col_19 + ") REFERENCES " + table_dog + "(" + col_1 + "), "
                +  "PRIMARY KEY (" + col_15 + ", " + col_19 + "  )) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table_dog);
        //onCreate(db);
        db.execSQL("drop table if exists " + table_command);
        //onCreate(db);
        db.execSQL("drop table if exists " + table_event);
        onCreate(db);
    }

// - - - METHODS FOR HANDLING DOGS - - - //

    public boolean insertDog(String name, String weight, String height)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_2, name);
        values.put(col_3, weight);
        values.put(col_4, height);

        long result = db.insert(table_dog, null, values);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    // - - - For populating with the CSV - - - //
    public boolean insertDog_ID (int id, String name, String weight, String height)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(col_1, id);
            values.put(col_2, name);
            values.put(col_3, weight);
            values.put(col_4, height);

            long result = db.insert(table_dog, null, values);

            if (result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch(SQLiteConstraintException e)
        {
            e.printStackTrace();
            return false;
        }
    }
// Retrieves the name of a pet using the ID
    public Cursor getPetName(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select " + table_dog + "." + col_2
                + " from " + table_dog
                + " where " + table_dog + "." + col_1 + " = " + id,null);
        return res;
    }

    public Cursor allDogs()

    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * From " + table_dog, null);
        return res;
    }

    public Cursor dogNames()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select " + col_2 + " from " + table_dog, null);
        return res;
    }

    public Cursor getDogIDs()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select PETID From DOG", null);
        return res;
    }

// - - - METHODS FOR HANDLING COMMAND - - - //

    // Insert a new command object
    public boolean insertCommand(String commandName, String path, int petID)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(col_12, petID);
            values.put(col_13, commandName);
            values.put(col_14, path);

            long result = db.insert(table_command, null, values);

            if (result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }}
        catch(SQLiteConstraintException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public Cursor dogCommands(int petID, int commandID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select " + col_13 + " , " + col_14
                + " From " + table_command
                + " where (" + col_12 + " = " + petID
                + " and " + col_11 + " = " + commandID + ")"  , null);

        return res;
    }
}