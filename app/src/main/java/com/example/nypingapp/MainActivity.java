package com.example.nypingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    public static String ipaddressString = "";
    public static String toLogFile ="";
    public static double ping =0;
    public static String pingString = "";
    public static String currentDateandTime="";
    public static FileOutputStream fileout;
    public static OutputStreamWriter outputWriter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }
}
