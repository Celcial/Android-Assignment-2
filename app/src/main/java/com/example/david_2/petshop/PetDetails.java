package com.example.david_2.petshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PetDetails extends AppCompatActivity {

    TextView name_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        name_header = (TextView) findViewById(R.id.tv_name_header);
        name_header.setText(getIntent().getStringExtra("allNames"));
    }


    public void onClick_toPetInfo(View aView)
    {
        Intent PetInfo = new Intent(this, PetInfo.class);
        startActivity(PetInfo);
    }
    public void onClick_toPetSchedule(View aView)
    {
        Intent PetSchedule = new Intent(this, PetSchedule.class);
        startActivity(PetSchedule);
    }
    public void onClick_toPetFind(View aView)
    {
        Intent PetFind = new Intent(this, PetFind.class);
        startActivity(PetFind);
    }
    public void onClick_toPetCall(View aView)
    {
        Intent PetCall = new Intent(this, PetCall.class);
        startActivity(PetCall);
    }
}
