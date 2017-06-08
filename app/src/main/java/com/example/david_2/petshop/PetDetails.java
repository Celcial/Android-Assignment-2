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
    ArrayList<ImageView> allImages;
    GridView theGrid;
    Intent[] allIntents;

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

        allIntents = new Intent[4];
        allIntents[0] = new Intent(this,PetInfo.class);
        allIntents[1] = new Intent(this, PetSchedule.class);
        allIntents[2] = new Intent(this, PetFind.class);
        allIntents[3] = new Intent(this, PetCall.class);

        Toast.makeText(getApplicationContext(),"" + petName + "", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        allIntents[position].putExtra("id", position + 1);
        startActivity(allIntents[position]);
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
            allFunctions.add("");
            allFunctions.add("");
            allFunctions.add("");
            allFunctions.add("");
            /*allFunctions.add("Details");
            allFunctions.add("Schedule");
            allFunctions.add("Locate");
            allFunctions.add("Call pet");*/


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
            if(position == 0)
            {
                holder.theCirle.setImageResource(R.drawable.dot);
            }
            else if (position == 1)
            {
                holder.theCirle.setImageResource(R.drawable.cal);
                holder.theCirle.setScaleX(1);
                holder.theCirle.setScaleY(1);
            }
            else if (position == 2)
            {
                holder.theCirle.setImageResource(R.drawable.map);
            }
            else if(position == 3)
            {
                holder.theCirle.setImageResource(R.drawable.phone);
            }

            holder.theText.setText(allFunctions.get(position));
            holder.theText.setTextSize(25);
            holder.theText.setTypeface(custom_font);

            return row;
        }
    }

    public void onClick_toPetInfo(View aView)
    {
        Intent PetInfo = new Intent(this, PetInfo.class);
        PetInfo.putExtra("id", id);
        PetInfo.putExtra("name", "a" + name_header.getText().toString());
        startActivity(PetInfo);
    }
    public void onClick_toPetSchedule(View aView)
    {
        Intent PetSchedule = new Intent(this, PetSchedule.class);
        PetSchedule.putExtra("id", id);
        PetSchedule.putExtra("name", "a" + name_header.getText().toString());
        startActivity(PetSchedule);
    }
    public void onClick_toPetFind(View aView)
    {
        Intent PetFind = new Intent(this, PetFind.class);
        PetFind.putExtra("id", id);
        PetFind.putExtra("name", "a" + name_header.getText().toString());
        startActivity(PetFind);
    }
    public void onClick_toPetCall(View aView)
    {
        Intent PetCall = new Intent(this, PetCall.class);
        PetCall.putExtra("id", id);
        PetCall.putExtra("name", "a" + name_header.getText().toString());
        startActivity(PetCall);
    }
}
