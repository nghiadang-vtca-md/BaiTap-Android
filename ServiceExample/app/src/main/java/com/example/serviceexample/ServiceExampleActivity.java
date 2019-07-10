package com.example.serviceexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ServiceExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_example);

//        // Test IntentService ... call MyIntentService class
//        Intent intent = new Intent(this,MyIntentService.class);
//        // call method start to run intent
//        startService(intent);
    }

    public void buttonClick(View view){
        // Comment service in onCreate
        // implement MyService.class instead
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
    }
}
