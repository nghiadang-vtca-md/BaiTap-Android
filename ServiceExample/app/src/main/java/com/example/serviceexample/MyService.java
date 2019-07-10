package com.example.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        //super.onCreate();
        Log.i(TAG,"Service - onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        // ----------------------
//        Log.i(TAG,"Service - onStartCommand " + startId);
//
//        int i = 0;
//        while (i<=3){
//            try{
//                Thread.sleep(3000);
//                i++;
//            }catch (Exception e){
//
//            }
//            Log.i(TAG,"Service running");
//        }
        // -------------------------------
        // using AsyncTask instead
        AsyncTask task = new SrvTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,startId);
        return  Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        //super.onDestroy();
        Log.i(TAG,"Service - onDestroy");
    }

    private static final String TAG = "ServiceExample";

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"Service - onBind");
        return null;
    }

    private class SrvTask extends AsyncTask<Integer,Integer,String>{
        @Override
        protected String doInBackground(Integer... params) {
            int startId = params[0];
            int i = 0;
            while (i<=3){
                publishProgress(params[0]);
                try{
                    Thread.sleep(3000);
                    i++;
                }catch (Exception e){

                }
            }
            return ("Service complete " + startId);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG,s);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG,"Service running " + values[0]);
        }
    }
}
