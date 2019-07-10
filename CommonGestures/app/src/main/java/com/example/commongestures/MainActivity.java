package com.example.commongestures;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.GestureDetector;
import android.view.MotionEvent;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
    private TextView gestureText;
    private TextView eventBelongToText;

    private GestureDetectorCompat gDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureText = (TextView)findViewById(R.id.gestureStatusText);
        eventBelongToText = (TextView)findViewById(R.id.eventBelongToText);

        this.gDetector = new GestureDetectorCompat(this,this);
        gDetector.setOnDoubleTapListener(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        gestureText.setText("onSingleTapConfirmed");
        eventBelongToText.setText("OnDoubleTapListener");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        gestureText.setText("onDoubleTap");
        eventBelongToText.setText("OnDoubleTapListener");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        gestureText.setText("onDoubleTapEvent");
        eventBelongToText.setText("OnDoubleTapListener");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        gestureText.setText("onDown");
        eventBelongToText.setText("OnGestureListener");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        gestureText.setText("onShowPress");
        eventBelongToText.setText("OnGestureListener");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        gestureText.setText("onSingleTapUp");
        eventBelongToText.setText("OnGestureListener");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        gestureText.setText("onScroll");
        eventBelongToText.setText("OnGestureListener");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        gestureText.setText("onLongPress");
        eventBelongToText.setText("OnGestureListener");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        gestureText.setText("onFling");
        eventBelongToText.setText("OnGestureListener");
        return true;
    }
}
