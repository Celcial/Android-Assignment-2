package com.example.david_2.petshop;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
        TextView myTextView=(TextView)findViewById(R.id.tv_pet_name);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"PetShop/app/src/main/assets/fonts/MixStriped.ttf");
        myTextView.setTypeface(typeFace);
        /* - - - Add MixStriped font end - - -*/
        
        

        LinearLayout llDisplayData, llChangeBG;

        llDisplayData = (LinearLayout) findViewById(R.id.linear_layout_scrl_event); /*NOT SURE*/
        LayoutInflater linflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView;

        customView = linflater.inflate(R.layout.add_event_popup, null);

        llChangeBG = (LinearLayout) customView.findViewById(R.id.linear_layout_scrl_event); /*NOT SURE*/

        llDisplayData.addView(customView);
    }
}
