package com.example.transitiondemo;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

public class TransitionDemoActivity extends AppCompatActivity {
    ConstraintLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_demo);

        myLayout = (ConstraintLayout)findViewById(R.id.myLayout);
        myLayout.setOnTouchListener(
                new ConstraintLayout.OnTouchListener(){
                    public boolean onTouch(View v, MotionEvent m) {
                        handleTouch();
                        return true;
                    }
                }
        );
    }

    private void handleTouch() {
        Button button = (Button)findViewById(R.id.myButton);

        // use ChangeBounds
        Transition changeBounds = new ChangeBounds();
        changeBounds.setDuration(3000);
        changeBounds.setInterpolator(new AccelerateDecelerateInterpolator());

        // use delay
        //TransitionManager.beginDelayedTransition(myLayout);

        // use delay with Bounce
        TransitionManager.beginDelayedTransition(myLayout,changeBounds);

        button.setMinimumWidth(500);
        button.setMinHeight(350);

        ConstraintSet set = new ConstraintSet();

        set.connect(R.id.myButton,ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM,0);
        set.connect(R.id.myButton,ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0);
        set.constrainWidth(R.id.myButton,ConstraintSet.WRAP_CONTENT);

        set.applyTo(myLayout);
    }
}
