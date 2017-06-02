package com.example.david_2.petshop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PetMenu extends AppCompatActivity {

    Intent PetDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_menu);

        ImageButton newPet = (ImageButton)findViewById(R.id.btn_create_pet);

        newPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetMenu.this, createPet.class));
            }
        });


    }

    public void onClick_to_pet_details(View aView)
    {
        PetDetails = new Intent(this, PetDetails.class);
        PetDetails.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(PetDetails);
    }
}
