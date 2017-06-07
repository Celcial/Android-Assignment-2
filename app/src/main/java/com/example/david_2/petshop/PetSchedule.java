package com.example.david_2.petshop;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import static android.R.attr.typeface;

public class PetSchedule extends AppCompatActivity {
    private GridView eventList;
    private Cursor cursor;
    private ArrayList<String> title = new ArrayList<>();
    private EventDB helper;
    private ArrayAdapter adapter;
    private SQLiteDatabase dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_schedule);
        eventList = (GridView) findViewById(R.id.gvEvent);
        UpdateList();

        Button add_event = (Button) findViewById(R.id.ib_add_event);
        add_event.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetSchedule.this, createEvent.class));

            }
        });
    }

    public void UpdateList(){
        title.clear();
        helper = new EventDB(getApplicationContext());
        dbHandler = helper.getReadableDatabase();
        cursor = dbHandler.query(helper.TABLE, new String[]{helper.TITLE,helper.DATE,helper.NOTE}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            title.add(cursor.getString(cursor.getColumnIndex("TITLE"))+"  "+cursor.getString(cursor.getColumnIndex("DATE")));

        }

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, title);
        eventList.setAdapter(adapter);
    }

}
