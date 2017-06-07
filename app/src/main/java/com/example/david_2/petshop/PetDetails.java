package com.example.david_2.petshop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PetDetails extends AppCompatActivity {

    TextView name_header;
    public int id;
    Intent detailsIntent;
    petDatabase theDatabase;
    String petName;

    Typeface name_font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        name_font = Typeface.createFromAsset(getAssets(), "fonts/moonlight.ttf");
        detailsIntent = getIntent();


        name_header = (TextView) findViewById(R.id.tv_name_header);
        name_header.setTypeface(name_font);
        name_header.setTextSize(50);
        petName = detailsIntent.getStringExtra("allNames");
        name_header.setText(petName);
        id = detailsIntent.getIntExtra("id", 0);

        theDatabase = new petDatabase(this);
        theDatabase.getWritableDatabase();

        Toast.makeText(getApplicationContext(),"" + id + "", Toast.LENGTH_LONG).show();

    }


    public void onClick_toPetInfo(View aView)
    {
        Intent PetInfo = new Intent(this, PetInfo.class);
        PetInfo.putExtra("id", id);
        startActivity(PetInfo);
    }
    public void onClick_toPetSchedule(View aView)
    {
        Intent PetSchedule = new Intent(this, PetSchedule.class);
        PetSchedule.putExtra("id", id);
        startActivity(PetSchedule);
    }
    public void onClick_toPetFind(View aView)
    {
        Intent PetFind = new Intent(this, PetFind.class);
        PetFind.putExtra("id", id);
        startActivity(PetFind);
    }
    public void onClick_toPetCall(View aView)
    {
        Intent PetCall = new Intent(this, PetCall.class);
        PetCall.putExtra("id", id);
        startActivity(PetCall);
    }
}
