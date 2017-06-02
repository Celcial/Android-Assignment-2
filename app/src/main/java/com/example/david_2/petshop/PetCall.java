package com.example.david_2.petshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;

public class PetCall extends AppCompatActivity {
    private Button btnAdd,btnClear,btnRefresh;
    private GridView gvCommand;
    private SQLiteDatabase dbHandler;
    private Cursor cursor;
    private ArrayList<String> cmdName = new ArrayList<>();
    private ArrayList<String> cmdPath = new ArrayList<>();
    private W5DBHelper helper;
    private ArrayAdapter adapter;
    private MediaPlayer mediaPlayer ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_call);

        btnAdd = (Button) findViewById(R.id.button2);
        btnClear = (Button) findViewById(R.id.button3);
        btnRefresh = (Button) findViewById(R.id.button4);
        gvCommand = (GridView) findViewById(R.id.gridView);

        UpdateList();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetCall.this, Record.class);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDatabase(helper.NAME);
                UpdateList();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateList();
            }
        });

        gvCommand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                String file = cmdPath.get(position);
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(file);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        });

    }

    public void UpdateList(){
        cmdName.clear();
        cmdPath.clear();
        helper = new W5DBHelper(getApplicationContext());
        dbHandler = helper.getReadableDatabase();
        cursor = dbHandler.query(W5DBHelper.TABLE, new String[]{W5DBHelper.COMMAND,W5DBHelper.PATH}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            cmdName.add(cursor.getString(cursor.getColumnIndex("COMMAND")));
            cmdPath.add(cursor.getString(cursor.getColumnIndex("PATH")));
        }

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, cmdName);
        gvCommand.setAdapter(adapter);
    }
}

