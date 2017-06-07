package com.example.david_2.petshop;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class PetMenu extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Intent PetDetails;
    ArrayList<String> dogNames;
    GridView myGrid;
    Context theContext;
    public petDatabase theDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_menu);
        theContext = (Context)this;
        // - - - LAUNCH DATABASE! - - - //
        theDatabase = new petDatabase(this);

        // - - - Loads dummy data from csv - - - //
        //csvLoadDogs();
        //csvLoadVaccines();
        //csvLoadDogVaccines ();

        // - - - load array of dog names from the database - - - //
        dogNames = new ArrayList<>();
        fillNamesArray();
        // - - - Renders the GridViewt with the newAdapter - - - //
        myGrid = (GridView) findViewById(R.id.gv_list_dogs);
        myGrid.setAdapter(new dogNameAdapter(this));
        myGrid.setOnItemClickListener(this);
    }

    @Override//PARAMS: the GridView  |   item clicked    |   position of clicked item    |   SQLite reference    |
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(position == dogNames.size())
        {
            startActivity(new Intent(PetMenu.this, createPet.class));
            this.recreate();
        }
        else
        {
            PetDetails = new Intent(this, PetDetails.class);
            PetDetails.putExtra("id", position);
            PetDetails.putExtra("allNames", (dogNames.get(position)));
            startActivity(PetDetails);
            this.recreate();
        }
    }

    class ViewHolder
    {
        //Button dogNameTextView;
        TextView theText;
        ImageView theCirle;
        ViewHolder(View v)
        {
            //dogNameTextView = (Button)v.findViewById(R.id.tv_dog_name);
            theCirle = (ImageView) v.findViewById(R.id.iv_Circle);
            theText = (TextView) v.findViewById(R.id.the_pet_name);

        }
    }
    // - - - Custom adapt for placing dog names in the GridView - - - //
    class dogNameAdapter extends BaseAdapter
    {
        ArrayList<String> allDogs;
        int counter;
        Context context;
        Typeface custom_font;

        public dogNameAdapter(Context context)
        {
            custom_font = Typeface.createFromAsset(getAssets(),"fonts/MixStriped.ttf");
            this.context = context;
            counter = 0;
            allDogs = new ArrayList<>();
            theDatabase.getWritableDatabase();
            Cursor c = theDatabase.dogNames();
            while (c.moveToNext())
            {
                allDogs.add(c.getString(0));
            }
            allDogs.add("");
        }

        @Override
        public int getCount() {
            return allDogs.size();
        }

        @Override
        public Object getItem(int position) {
            return allDogs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;
            if (row == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.single_dog,parent, false);
                holder = new ViewHolder(row);
                row.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) row.getTag();
            }
            if(position + 1 == allDogs.size())
            {
                //holder.theText.setTypeface(custom_font);
                //holder.theText.setTextSize(5);
                holder.theText.setText(allDogs.get(position));
                holder.theCirle.setImageResource(R.drawable.red_dot_lined);
                //holder.theText.setTextColor(getColor(R.color.solidRed));

            }
            else
            {
                holder.theText.setText(allDogs.get(position));
                //holder.theText.setTextSize();
                holder.theText.setTypeface(custom_font);
                //holder.theText.setTextColor(getColor(R.color.white));
                holder.theCirle.setImageResource(R.drawable.red_dot);

            }
            return row;
        }
    }
    // - - - Method for populating the dogNames array - - - //
    public void fillNamesArray()
    {
        Cursor c = theDatabase.dogNames();
        if (c.getCount() == 0)
        {
            showMessage("Error", "no data found");
        }
        while (c.moveToNext())
        {
            dogNames.add(c.getString(0));
        }
    }

// - - - Working with CSV - - - //

    public void csvLoadDogs ()
    {
        InputStream is = getResources().openRawResource(R.raw.dogs);
        BufferedReader reader = new BufferedReader( new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null)
            {
                // split on ","
                String[] token = line.split(",");
                // read data
                Boolean state = theDatabase.insertDog_ID(parseInt(token[0]),token[1],token[2],token[3]);
            }
        } catch (IOException e) {
            Log.wtf("createPet", "Error importing file on line" + line, e);
            e.printStackTrace();
        }
    }

    public void csvLoadVaccines ()
    {
        InputStream is = getResources().openRawResource(R.raw.vaccines);
        BufferedReader reader = new BufferedReader( new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null)
            {
                // split on ","
                String[] token = line.split(",");
                // read data
                Boolean state = theDatabase.insertVaccine_ID(parseInt(token[0]),token[1]);
            }
        } catch (IOException e) {
            Log.wtf("createPet", "Error importing file on line" + line, e);
            e.printStackTrace();
        }
    }

    public void csvLoadDogVaccines ()
    {
        InputStream is = getResources().openRawResource(R.raw.dogvaccines);
        BufferedReader reader = new BufferedReader( new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null)
            {
                // split on ","
                String[] token = line.split(",");
                // read data
                Boolean state = theDatabase.insertDogVaccine(parseInt(token[0]),parseInt(token[1]),token[2], token[3]);
            }
        } catch (IOException e) {
            Log.wtf("createPet", "Error importing file on line" + line, e);
            e.printStackTrace();
        }
    }

    // - - - Simple message display - - - //
    public void showMessage(String title, String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }

}
