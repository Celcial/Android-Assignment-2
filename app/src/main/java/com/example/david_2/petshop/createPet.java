package com.example.david_2.petshop;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static java.lang.Integer.parseInt;

/**
 * Created by David_2 on 31/05/2017.
 */

public class createPet extends Activity {

    public petDatabase theDatabase;
    EditText dogName;
    Button newPetButton, allDogs, allVaccines, allDogVaccines;

    Button test;
    TextView id;
    Button test2;
    TextView petName;

    String PetName;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_create_pet);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.9));

        dogName = (EditText)findViewById(R.id.et_enter_dog_name);
        newPetButton = (Button)findViewById(R.id.btn_insert_pet);
        allDogs = (Button)findViewById(R.id.btn_all_dogs);
        allVaccines = (Button)findViewById(R.id.btn_all_vaccines);
        allDogVaccines = (Button)findViewById(R.id.btn_dog_vaccines);

        theDatabase = new petDatabase(this);
        theDatabase.getWritableDatabase();

        // - - - Launch buttons - - - //
        onClick_allDogs();
        onClick_allVaccines();
        onClick_dog_vaccines();

    }



    public void onClick_allDogs()
    {
        allDogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = theDatabase.allDogs();
                if (data.getCount() == 0)
                {
                    showMessage("Error", "no data found");
                }

                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext())
                {
                    buffer.append("ID : " + data.getString(0) + "\n");
                    buffer.append("Name : " + data.getString(1) + "\n");
                    buffer.append("Weight : " + data.getString(2) + "\n");
                    buffer.append("Height : " + data.getString(3) + "\n\n");
                }
                showMessage("Data : ", buffer.toString());

            }
        });
    }

    public void onClick_allVaccines()
    {
        allVaccines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Cursor data = theDatabase.allVaccines();
                if (data.getCount() == 0)
                {
                    showMessage("Error", "no data found");
                }

                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext())
                {
                    buffer.append("ID : " + data.getString(0) + "\n");
                    buffer.append("Vaccine : " + data.getString(1) + "\n\n");
                }
                showMessage("Data : ", buffer.toString());

            }
        });
    }

    public void onClick_dog_vaccines()
    {
        allDogVaccines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                Cursor data = theDatabase.allDogVaccines();

                if (data.getCount() == 0)
                {
                    showMessage("Error", "no data found");
                }

                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext())
                {
                    buffer.append("PetID : " + data.getString(0) + "\n");
                    buffer.append("VaccineID : " + data.getString(1) + "\n");
                    buffer.append("Date : " + data.getString(2) + "\n");
                    buffer.append("Notes : " + data.getString(3) + "\n\n");
                }
                showMessage("Data : ", buffer.toString());

            }
        });
    }



    public void onClick_create_pet(View aView)
    {
        /* Gets the new dog's name from the EditText (et_enter_dog_name. */
        PetName = dogName.getText().toString();
        // - - - Name field required - - - //
        if (PetName.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"The pet name field is required.",Toast.LENGTH_LONG).show();
        }
        else
        {
            Boolean state = theDatabase.insertDog(PetName,null,null);
            if (state == true)
            {
                Toast.makeText(getApplicationContext(),"Data inserted!",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Data failed to insert.",Toast.LENGTH_LONG).show();
            }
        }
    }

// - - - TESTING - - - //

    public void showMessage(String title, String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();

    }


}
