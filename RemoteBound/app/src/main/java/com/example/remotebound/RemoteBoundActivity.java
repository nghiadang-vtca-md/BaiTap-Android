package com.example.remotebound;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RemoteBoundActivity extends AppCompatActivity {
    final String TAG = "testDebug";
    Messenger myService = null;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_bound);

        Intent intent = new Intent(getApplicationContext(),RemoteService.class);
        bindService(intent,myConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG,"onCreate");
    }

    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = new Messenger(service);
            isBound = true;
            Log.i(TAG,"onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
            isBound = false;
            Log.i(TAG,"onServiceDisconnected");
        }
    };

    public void sendMessage(View view){
        if(!isBound) return;

        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("MyString", "Message Received");

        msg.setData(bundle);

        try{
            myService.send(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i(TAG,"sendMessage");

    }
}
