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
    public static final String col_7 = "DURATION";

    // Dogvaccine table details
    public static final String table_dogvaccine = "DOGVACCINE";
    public static final String col_8 = "PETID";
    public static final String col_9 = "VACCINEID";
    public static final String col_10 = "DATE";

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
                + col_6 + " text, "
                + col_7 + " text) ");
        db.execSQL(create_vaccine_table);

        // - - - CREATE dogvaccine table - - - //
        String create_dog_vaccine_table = ("create table " + table_dogvaccine +
                " ("
                + col_8 + " integer, "
                + col_9 + " integer, "
                + col_10 + " text), "
                + " FOREIGN KEY (" + col_8 + ") REFERENCES " + table_dog + "(" + col_1 + "), "
                + " FOREIGN KEY (" + col_9 + ") REFERENCES " + table_vaccine + "(" + col_5 + "), "
                + col_10 + " text, PRIMARY KEY (" + col_8 + ", " + col_9 + "  )) ");
        db.execSQL(create_dog_vaccine_table);
    }

    //+ " FOREIGN KEY (" + col_8 + ") REFERENCES " + table_dog + "(" + col_1 + "), "
    //+ " FOREIGN KEY (" + col_9 + ") REFERENCES " + table_vaccine + "(" + col_5 + "), "
    //+ col_10 + " text, PRIMARY KEY (" + col_8 + ", " + col_9 + "  )) ");

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table_dog);
        onCreate(db);
        db.execSQL("drop table if exists " + table_vaccine);
        onCreate(db);
        db.execSQL("drop table if exists " + table_dogvaccine);
        onCreate(db);
    }
// - - - Methods for handling important app features - - - //





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

    public boolean insertVaccine(String name, String duration)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_6, name);
        values.put(col_7, duration);

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

    // - - - For populating with the CSV - - - //
    public boolean insertVaccine_ID (int id, String name, String duration)
    {

        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(col_5, id);
            values.put(col_6, name);
            values.put(col_7, duration);

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

    public boolean insertDogVaccine(int dogID, int vaccineID, String date)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(col_8, dogID);
            values.put(col_9, vaccineID);
            values.put(col_10, date);

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

    public Cursor allDogVaccines()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * From " + table_dogvaccine, null);
        return res;
    }
}