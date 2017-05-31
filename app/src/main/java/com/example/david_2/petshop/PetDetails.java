package com.example.david_2.petshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PetDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
    }
    public void onClick_to_four_options(int x)
    {
        if (x == 1)
        {
            Intent PetInfo = new Intent(this, PetInfo.class);
            startActivity(PetInfo);
        }
        else if (x == 2)
        {
            Intent PetSchedule = new Intent(this, PetSchedule.class);
            startActivity(PetSchedule);
        }
        else if (x == 3)
        {
            Intent PetFind = new Intent(this, PetFind.class);
            startActivity(PetFind);
        }
        else if (x==4)
        {
            Intent PetCall = new Intent(this, PetCall.class);
            startActivity(PetCall);
        }
        else
            {
                /* Execute if number passed is invalid*/
            }
    }
}
