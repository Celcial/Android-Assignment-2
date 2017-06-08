package com.example.david_2.petshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PetSchedule extends AppCompatActivity {
    private GridView eventList;
    private Cursor cursor;
    private ArrayList<String> title = new ArrayList<>();
    private EventDB helper;
    private ArrayAdapter adapter;
    private SQLiteDatabase dbHandler;
    private String theName;
    TextView petName;
    Intent theDogName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_schedule);
        eventList = (GridView) findViewById(R.id.gvEvent);
        UpdateList();
        theDogName = getIntent();
        theName = theDogName.getStringExtra("name");
        Toast.makeText(getApplicationContext(), " " + theName,Toast.LENGTH_LONG).show();

        petName = (TextView)findViewById(R.id.tv_pet_name);

        petName.setText(theName);





        Button add_event = (Button) findViewById(R.id.ib_add_event);
        add_event.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PetSchedule.this, createEvent.class),1);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UpdateList();
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
