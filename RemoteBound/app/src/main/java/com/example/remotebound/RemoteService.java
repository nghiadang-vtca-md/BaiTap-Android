package com.example.remotebound;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

public class RemoteService extends Service {
    final String TAG = "testDebug";
    final Messenger myMessenger = new Messenger(new IncomingHandler());

    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return myMessenger.getBinder();
    }

    class IncomingHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG,"handleMessage");
            Bundle data = msg.getData();
            String dataString = data.getString("MyString");
            Toast.makeText(getApplicationContext(),dataString,Toast.LENGTH_SHORT).show();
        }
    }
}
