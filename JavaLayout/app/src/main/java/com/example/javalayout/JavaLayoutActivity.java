package com.example.javalayout;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.res.Resources;
import android.util.TypedValue;


public class JavaLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_java_layout);
        configureLayout();
    }

    private int convertToPx(int value){
        Resources r = getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,r.getDisplayMetrics());
        return px;
    }

    private void configureLayout(){
        Button myButton = new Button(this);
        myButton.setText(getString(R.string.press_me));
        myButton.setBackgroundColor(Color.YELLOW);
        myButton.setId(R.id.myButton);

        EditText myEditText = new EditText(this);
        myEditText.setId(R.id.myEditText);

        ConstraintLayout myLayout = new ConstraintLayout(this);
        myLayout.setBackgroundColor(Color.BLUE);

        int px = convertToPx(200);
        myEditText.setWidth(px);

        myLayout.addView(myButton);
        myLayout.addView(myEditText);

        setContentView(myLayout);


        ConstraintSet set = new ConstraintSet();

        set.constrainHeight(myButton.getId(),ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(myButton.getId(),ConstraintSet.WRAP_CONTENT);

        set.connect(myButton.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0);
        set.connect(myButton.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0);
        set.connect(myButton.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,0);
        set.connect(myButton.getId(),ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM,0);

        set.constrainHeight(myEditText.getId(),ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(myEditText.getId(),ConstraintSet.WRAP_CONTENT);

        set.connect(myEditText.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0);
        set.connect(myEditText.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0);
        set.connect(myEditText.getId(),ConstraintSet.BOTTOM,myButton.getId(),ConstraintSet.TOP,70);

        set.applyTo(myLayout);
    }
}
