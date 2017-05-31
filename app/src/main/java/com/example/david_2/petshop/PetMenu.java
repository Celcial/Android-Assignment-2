package com.example.david_2.petshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PetMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_menu);
    }

    public void onClick_to_pet_details(int x)
    {
        Intent PetDetails = new Intent(this, PetDetails.class);
        startActivity(PetDetails);
    }
}
