package com.example.countdowntimer;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //private static final long START_TIME_IN_MILLIS = 600000; // 10 minutes

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private EditText mEditTextInput;
    private Button mButtonSet;

    private CountDownTimer mCountDownTimer;

    private boolean mTimeRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mEditTextInput = findViewById(R.id.edit_text_input);
        mButtonSet = findViewById(R.id.button_set);

        mButtonSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if(input.length() == 0){
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if(millisInput == 0){
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                }

                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });
        
        mButtonStartPause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mTimeRunning){
                    pauseTime();
                } else{
                    startTime();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetTime();
            }
        });

        //updateCountDownText();
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTime();
        closeKeyBoard();
    }

    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void updateCountDownText() {
        int hours = (int)(mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int)((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int)(mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if(hours > 0){
            timeLeftFormatted = String.format(Locale.getDefault(),"--> %d:%02d:%02d <--",hours,minutes,seconds);
        }else {
            timeLeftFormatted = String.format(Locale.getDefault(),"--> %02d:%02d <--",minutes,seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void resetTime() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void startTime() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimeRunning = false;
                updateWatchInterface();
            }
        };
        mCountDownTimer.start();

        mTimeRunning = true;
        updateWatchInterface();
    }

    private void updateWatchInterface() {
        if(mTimeRunning){
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
            mButtonReset.setVisibility(View.INVISIBLE);
        }else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");

            if(mTimeLeftInMillis < 1000){ // < 1 second
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if(mTimeLeftInMillis < mStartTimeInMillis){
                mButtonReset.setVisibility(View.VISIBLE);
            }else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void pauseTime() {
        mCountDownTimer.cancel();
        mTimeRunning = false;
        updateWatchInterface();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("millisLeft", mTimeLeftInMillis);
        outState.putBoolean("timeRunning",mTimeRunning);
        outState.putLong("endTime",mEndTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        mTimeRunning = savedInstanceState.getBoolean("timeRunning");
        mEndTime = savedInstanceState.getLong("endTime");

        updateCountDownText();
        updateWatchInterface();

        if(mTimeRunning){
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTime();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis",600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft",mStartTimeInMillis);
        mTimeRunning = prefs.getBoolean("timeRunning",false);

        updateCountDownText();
        updateWatchInterface();

        if(mTimeRunning){
            mEndTime = prefs.getLong("endTime",0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if(mTimeLeftInMillis < 1000){
                mTimeLeftInMillis = 0;
                mTimeRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTime();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timeRunning", mTimeRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
        }
    }
}
