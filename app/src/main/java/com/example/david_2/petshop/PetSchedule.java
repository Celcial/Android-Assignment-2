package com.example.david_2.petshop;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import static android.R.attr.typeface;

public class PetSchedule extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_schedule);

        /* - - - Add MixStriped font to the activity - - - */
        //TextView myTextView=(TextView)findViewById(R.id.tv_pet_name);
        //Typeface typeFace=Typeface.createFromAsset(getAssets(),"PetShop/app/src/main/assets/fonts/MixStriped.ttf");
        //myTextView.setTypeface(typeFace);
        /* - - - Add MixStriped font end - - -*/

        Button add_event = (Button)findViewById(R.id.ib_add_event);
        add_event.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetSchedule.this, createEvent.class));

            }
        });
        
        

        LinearLayout llDisplayData, llChangeBG;

        llDisplayData = (LinearLayout) findViewById(R.id.l_all_events); /*NOT SURE*/
        LayoutInflater linflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView;

        customView = linflater.inflate(R.layout.layout_all_events, null);

        llChangeBG = (LinearLayout) customView.findViewById(R.id.l_all_events); /*NOT SURE*/

        llDisplayData.addView(customView);
    }
}
