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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PetDetails extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView name_header;
    public int id;
    Intent detailsIntent;
    petDatabase theDatabase;
    String petName;
    Context context;
    Typeface custom_font;
    ArrayList<String> allFunctions;
    GridView theGrid;

    Typeface name_font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        name_font = Typeface.createFromAsset(getAssets(), "fonts/moonlight.ttf");
        detailsIntent = getIntent();
        context = (Context)this;

        name_header = (TextView) findViewById(R.id.tv_name_header);
        name_header.setTypeface(name_font);
        name_header.setTextSize(50);
        petName = detailsIntent.getStringExtra("allNames");
        name_header.setText(petName);
        id = detailsIntent.getIntExtra("id", 0);

        theDatabase = new petDatabase(this);
        theDatabase.getWritableDatabase();

        theGrid = (GridView)findViewById(R.id.btn_grid);
        theGrid.setAdapter(new gridAdapter(this));
        theGrid.setOnItemClickListener(this);

        Toast.makeText(getApplicationContext(),"" + id + "", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }

    class ViewHolder
    {
        TextView theText;
        ImageView theCirle;
        public ViewHolder(View v)
        {
            theCirle = (ImageView) v.findViewById(R.id.iv_Circle);
            theText = (TextView) v.findViewById(R.id.the_pet_name);
        }
    }
    class gridAdapter extends BaseAdapter
    {
        public gridAdapter(Context context)
        {
            custom_font = Typeface.createFromAsset(getAssets(),"fonts/MixStriped.ttf");
            allFunctions = new ArrayList<String>();
            allFunctions.add("Details");
            allFunctions.add("Schedule");
            allFunctions.add("Locate");
            allFunctions.add("Call Pet");
        }

        @Override
        public int getCount() {
            return allFunctions.size();
        }

        @Override
        public Object getItem(int position) {
            return allFunctions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;
            if (row == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.single_dog, parent, false);
                holder = new ViewHolder(row);
                row.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) row.getTag();
            }
            holder.theText.setText(allFunctions.get(position));
            holder.theText.setTextSize(25);

            holder.theText.setTypeface(custom_font);
            //holder.theCirle.setImageResource(R.drawable.red_dot_lined);
            return row;
        }
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
