package com.example.david_2.petshop;

import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Record extends PetMenu {

    private Button btnSave, btnCancel;
    private ImageButton btnStart, btnStop, btnStartPlayback, btnStopPlayback;
    private EditText txtAdd;
    private String AudioSavePathInDevice = null;
    private String fileName = null;
    private MediaRecorder mediaRecorder ;
    private MediaPlayer mediaPlayer ;
    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        btnStart = (ImageButton) findViewById(R.id.imageButton1);
        btnStop = (ImageButton) findViewById(R.id.imageButton2);
        btnStartPlayback = (ImageButton) findViewById(R.id.imageButton3);
        btnStopPlayback = (ImageButton)findViewById(R.id.imageButton4);
        btnSave = (Button)findViewById(R.id.button5);
        btnCancel = (Button)findViewById(R.id.button6);
        btnStop.setEnabled(false);
        btnStartPlayback.setEnabled(false);
        btnStopPlayback.setEnabled(false);
        txtAdd = (EditText) findViewById(R.id.etDate);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().setLayout((int)(width),(int)(height*.9));



        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName = txtAdd.getText().toString();
                if (!fileName.matches("[a-zA-Z]+"))
                {
                    Toast.makeText(Record.this, "Invalid command name", Toast.LENGTH_LONG).show();
                }
                else {
                    if (checkPermission()) {
                        AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName + ".3gp";
                        MediaRecorderReady();
                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        btnStart.setEnabled(false);
                        btnStop.setEnabled(true);
                        Toast.makeText(Record.this, "Recording started", Toast.LENGTH_LONG).show();
                    } else {
                        requestPermission();
                    }
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                btnStop.setEnabled(false);
                btnStartPlayback.setEnabled(true);
                btnStart.setEnabled(true);
                btnStopPlayback.setEnabled(false);

                Toast.makeText(Record.this, "Recording Completed", Toast.LENGTH_LONG).show();
            }
        });

        btnStartPlayback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {
                btnStop.setEnabled(false);
                btnStart.setEnabled(false);
                btnStopPlayback.setEnabled(true);

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(Record.this, "Recording Playing", Toast.LENGTH_LONG).show();
            }
        });

        btnStopPlayback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStop.setEnabled(false);
                btnStart.setEnabled(true);
                btnStopPlayback.setEnabled(false);
                btnStartPlayback.setEnabled(true);

                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                CommandDB helper = new CommandDB(getApplicationContext());
                SQLiteDatabase dbHandler = helper.getWritableDatabase();
                String sqlInsert = "insert into COMMANDLIST (CID,COMMAND,PATH) values(null,'"+fileName.toUpperCase()+"','"+AudioSavePathInDevice+"');";
                dbHandler.execSQL(sqlInsert);

                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Record.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(Record.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Record.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
}
