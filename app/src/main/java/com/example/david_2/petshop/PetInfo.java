package com.example.david_2.petshop;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PetInfo extends AppCompatActivity {
    TextView petsName;
    Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        custom_font = Typeface.createFromAsset(getAssets(),"fonts/moonlight.ttf");


        petsName = (TextView)findViewById(R.id.tv_pet_name);
        petsName.setTypeface(custom_font);


    }
}
