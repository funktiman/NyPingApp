package com.example.nypingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main2Activity extends AppCompatActivity {

    public static String logFileString ="";
    public TextView textStatusen;
    public ScrollView scrollviewen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /**
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
         */

        ReadLog();
        TextView TextViewLog = (TextView)findViewById(R.id.TextViewLog);
        TextViewLog.setText(logFileString);

        //for scrolling med smooth scroll + flick
        textStatusen = (TextView) findViewById(R.id.TextViewLog);
        scrollviewen = (ScrollView) findViewById(R.id.SCROLLER_ID2);

        scrollToBottom();

    }



    private void scrollToBottom() {
        {
            scrollviewen.post(new Runnable() {
                public void run() {
                    scrollviewen.smoothScrollTo(0, textStatusen.getBottom());
                }
            });
        }
    }
    public void buttonClose(View v){

        //closes down the second view
        finish();

    }

    public void ReadLog() {
        //reading text from file
        try {
            FileInputStream fileIn = openFileInput("logfile.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[256];
            String s = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                logFileString = String.copyValueOf(inputBuffer, 0, charRead);

            }
            InputRead.close();
            //Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();


        }

        catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Exception"+ "File read failed: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
