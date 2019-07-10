package com.example.stopwatchexample;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean isRunning;
    long checkEvery10s = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("--> %s <--");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= checkEvery10s){
                    //chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(MainActivity.this, "Bing! " + checkEvery10s/1000 + "s", Toast.LENGTH_LONG).show();
                    checkEvery10s += 10000;
                }
            }
        });
    }

    public void startChronometer(View view){
        if(!isRunning){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            isRunning = true;
        }
    }

    public void pauseChronometer(View view){
        if(isRunning){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            isRunning = false;
        }
    }

    public void resetChronometer(View view){
        checkEvery10s = 10000;
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
}
