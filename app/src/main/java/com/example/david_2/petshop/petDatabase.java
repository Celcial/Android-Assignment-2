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
    public static final String col_4 = "HEIGHT";

    // Vaccine table details
    public static final String table_vaccine = "VACCINE";
    public static final String col_5 = "VACCINEID";
    public static final String col_6 = "VNAME";

    // Dogvaccine table details
    public static final String table_dogvaccine = "DOGVACCINE";
    public static final String col_7 = "PETID";
    public static final String col_8 = "VACCINEID";
    public static final String col_9 = "DATE";
    public static final String col_10 = "NOTES";
    // Command table details
    public static final String table_command = "COMMAND";
    public static final String col_11 = "COMMANDID";
    public static final String col_12 = "PETID";
    public static final String col_13 = "CNAME";
    public static final String col_14 = "PATHWAY";


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

        // - - - CREATE vaccine table - - - //
        String create_vaccine_table = ("create table " + table_vaccine +
                " ("
                + col_5 + " integer primary key autoincrement, "
                + col_6 + " text) ");
        db.execSQL(create_vaccine_table);

        // - - - CREATE dogvaccine table - - - //
        String create_dog_vaccine_table = ("create table " + table_dogvaccine +
                " ("
                + col_7 + " integer, "
                + col_8 + " integer, "
                + col_9 + " text, "
                + col_10 + " text, "
                + " FOREIGN KEY (" + col_7 + ") REFERENCES " + table_dog + "(" + col_1 + "), "
                + " FOREIGN KEY (" + col_8 + ") REFERENCES " + table_vaccine + "(" + col_5 + "), "
                +  "PRIMARY KEY (" + col_7 + ", " + col_8 + "  )) ");
        db.execSQL(create_dog_vaccine_table);

        String create_command_table = ("create table " + table_command
                + " ("
                + col_11 + " integer autoincrement, "
                + col_12 + " integer, "
                + col_13 + " text, "
                + col_14 + " text, "
                + "FOREIGN KEY (" + col_12 + ") REFERENCES " + table_dog + " (" + col_1 + "), "
                + "PRIMARY KEY (" + col_11 + " , " + col_12 + " )) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table_dog);
        //onCreate(db);
        db.execSQL("drop table if exists " + table_vaccine);
        //onCreate(db);
        db.execSQL("drop table if exists " + table_dogvaccine);
        //onCreate(db);
        db.execSQL("drop table if exists " + table_command);
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

// - - - METHODS FOR HANDLING VACCINES - - - //

    public boolean insertVaccine(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_6, name);
        long result = db.insert(table_vaccine, null, values);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getVaccineID(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select " + col_5 + " from ("+ table_vaccine + " where " + col_6 + " = " + name + ")"  ,null);
        return res;
    }

    // - - - For populating with the CSV - - - //
    public boolean insertVaccine_ID (int id, String name)
    {

        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(col_5, id);
            values.put(col_6, name);

            long result = db.insert(table_vaccine, null, values);

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

    public Cursor allVaccines()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * From " + table_vaccine, null);
        return res;
    }

// - - - METHODS FOR HANDING DOGVACCINES - - -//

    public boolean insertDogVaccine(int dogID, int vaccineID, String date, String notes)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(col_7, dogID);
            values.put(col_8, vaccineID);
            values.put(col_9, date);
            values.put(col_10, notes);

            long result = db.insert(table_dogvaccine, null, values);

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

    public Cursor vaccinesForPet(int petID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select " +table_vaccine + "." + col_6 + " , " + table_dogvaccine + "." + col_9 + " , " + table_dogvaccine + "." + col_10 + " from "
                + table_dogvaccine + " , " + table_vaccine
                + " where (" + table_vaccine + "." + col_5 + " = " + table_dogvaccine + "." + col_8
                + ") AND ("
                + table_dogvaccine + "." + col_7 + " = " + petID +")",null);
        return res;
    }

    public Cursor allDogVaccines()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * From " + table_dogvaccine, null);
        return res;
    }
// - - - METHODS FOR HANDLING COMMAND - - - //

// #################################################################################################
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
//##################################################################################################














}