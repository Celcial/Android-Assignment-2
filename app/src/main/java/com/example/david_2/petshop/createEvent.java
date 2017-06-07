package com.example.david_2.petshop;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by David_2 on 31/05/2017.
 */

public class createEvent extends Activity{
    private EditText dateText,noteText;

    private Calendar myCalendar = Calendar.getInstance();
    private Button addButton;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private petDatabase db;
    private SQLiteDatabase dbHandler;
    private Spinner spTitle;
    private List<String> vacineList;
    private String vac="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_event);

        //Set view size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.widthPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.9));

        dateText = (EditText)findViewById(R.id.etDate);
        dateText.setText(sdf.format(myCalendar.getTime()));
        noteText = (EditText) findViewById(R.id.etNote);
        addButton = (Button) findViewById(R.id.btnAdd);
        spTitle = (Spinner)findViewById(R.id.spnTitle);
        vacineList = Arrays.asList(getResources().getStringArray(R.array.vacine));
        db = new petDatabase(getApplicationContext());
        dbHandler = db.getWritableDatabase();

        //Open date picker pop-up
        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(createEvent.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Add Event
        spTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {

                vac = vacineList.get(arg2);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!vac.matches("[a-zA-Z]+"))
                        {
                            Toast.makeText(createEvent.this, "Invalid Title", Toast.LENGTH_LONG).show();
                        }

                        else{
                            //INSERT HERER
                            // Title: String vac
                            // Daten: String textDate.getTExt().toString();
                            // Note: String textNote.getText().toString();

                            setReminder();
                            finish();
                        }
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
            //Intent intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("geo:0,0?q=37.423156,-122.084917 (UNITEC)"));
        });
    }

    public void setReminder(){
        //set event time from 8.00 to 17.00 on the event day
        myCalendar.set(Calendar.HOUR_OF_DAY, 8);
        myCalendar.set(Calendar.MINUTE, 0);
        long startTime= myCalendar.getTimeInMillis();
        myCalendar.set(Calendar.HOUR_OF_DAY,17);
        long endTime = myCalendar.getTimeInMillis();

        //create event to calendar
        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();
        eventValues.put(CalendarContract.Events.CALENDAR_ID, 1);
        eventValues.put(CalendarContract.Events.TITLE, vac);
        eventValues.put(CalendarContract.Events.DESCRIPTION, noteText.getText().toString());
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, "GMT");
        eventValues.put(CalendarContract.Events.DTSTART, startTime);
        eventValues.put(CalendarContract.Events.DTEND, endTime);
        eventValues.put("eventStatus", 1);
        eventValues.put(CalendarContract.Events.HAS_ALARM, 1);
        //set reminder to one day before & one minute before
        Uri eventUri = getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        long eventID = Long.parseLong(eventUri.getLastPathSegment());
        String reminderUriString = "content://com.android.calendar/reminders";
        ContentValues reminderValues = new ContentValues();
        reminderValues.put("event_id", eventID);
        reminderValues.put("method", 1);
        reminderValues.put("minutes",60*24);
        getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
        reminderValues.put("minutes", 1);
        getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateText.setText(sdf.format(myCalendar.getTime()));
        }
    };

}
