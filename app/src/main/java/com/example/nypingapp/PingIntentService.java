package com.example.nypingapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingIntentService extends IntentService {



    public static final String PING_REPLAY = "com.example.nypingapp.pingReplayID";
    public static final String BRODCAST_PING_ID = "com.example.nypingapp.pingBroadcastID";
    public PingIntentService() {
        super("PingIntentService");
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        String hostAddress = intent.getStringExtra(MainActivity.PING_ID);

        try {
            double pingen = ping(hostAddress);
            Intent intentReplay = new Intent(BRODCAST_PING_ID);
            intentReplay.putExtra(PING_REPLAY, pingen);
            sendBroadcast(intentReplay);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private double ping(String hostAddress) throws IOException {
        int exit = -1;
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec("ping -c 1 " + hostAddress);
            process.waitFor();
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line + "\n";
            }
            bufferedReader.close();
            exit = process.exitValue();
            process.destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IOException("System call interrupted");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed reading system call result");
        }

        if(exit != 0) {
            throw new IOException("System call failed with exit: " + exit);
        }

        Pattern latencyPattern = Pattern.compile(".*?time=(.*)?ms");
        Matcher latencyMatcher = latencyPattern.matcher(result);
        double latency = 0;
        while (latencyMatcher.find()) {
            try {
                latency = Double.parseDouble(latencyMatcher.group(1));
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return latency;
    }
}
