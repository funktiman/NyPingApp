package com.example.nypingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    public static final String PING_ID = "com.example.nypingapp.pingID";
    BroadcastPingReplay broadcastPingReceiver;

    TextView pingTextView;

    public static String ipaddressString = "";
    public static String toLogFile ="";
    public static double ping =0;
    public static String pingString = "";
    public static String currentDateandTime="";
    public static FileOutputStream fileout;
    public static OutputStreamWriter outputWriter;

    static final String filename = "logfil.txt";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd - HH:mm:ss");
        currentDateandTime = sdf.format(new Date());

        broadcastPingReceiver = new BroadcastPingReplay();
        // Filter:
        IntentFilter intentFilter = new IntentFilter(PingIntentService.BRODCAST_PING_ID);
        registerReceiver(broadcastPingReceiver, intentFilter);

    }

    // vis log knappen
    public void onClickView(View v){

        //starts the second activity
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

    }

    //knappen "ping"
    public void onClick(View v){

      // Toast.makeText(getApplicationContext(), "Calculating ping to target", Toast.LENGTH_LONG).show();

       try {
            // lagrer fra textboks ipaddressString
            EditText edText = (EditText) findViewById(R.id.editText);
            ipaddressString= edText.getText().toString();
            Intent intent = new Intent(this, PingIntentService.class);
            intent.putExtra(PING_ID, ipaddressString);
            startService(intent);
            Toast.makeText(getApplicationContext(), "Intent started, sender ipadresse til pingIntendService", Toast.LENGTH_LONG).show();


       }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error IO"+e, Toast.LENGTH_LONG).show();
        }
    }



    public void WriteToFile(String logEventText) {

        // add-write text into file
        try {
            fileout=openFileOutput(filename, MODE_PRIVATE);
            outputWriter=new OutputStreamWriter(fileout);
            outputWriter.append(logEventText);
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "Log saved to file successfully!",
                    Toast.LENGTH_SHORT).show();

        }
        catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Exception"+ "File write failed: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }




    class BroadcastPingReplay extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
             pingTextView = (TextView) findViewById(R.id.pingTextView);

            if(intent.getAction().equals(PingIntentService.BRODCAST_PING_ID)){
                Toast.makeText(getApplicationContext(), "Broadcast fra PingIntentService, ping adresse", Toast.LENGTH_LONG).show();

                double ping = intent.getDoubleExtra(PingIntentService.PING_REPLY, -1);
                pingString = String.valueOf(ping) + " ms";
                pingTextView.setText(pingString);

                toLogFile = currentDateandTime + " : " + ipaddressString + " : " + pingString + " \n";

                WriteToFile(toLogFile);

            }
        }




        //TextView pingTextView = (TextView)findViewById(R.id.pingTextView);
           // pingTextView.setText(pingString);



    }

}
