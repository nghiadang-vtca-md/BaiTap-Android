package com.example.asyncdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AsyncDemoActivity extends AppCompatActivity {

    private TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_demo);

        myTextView = (TextView)findViewById(R.id.myTextView);
    }

    public void buttonClick(View view)
    {
        // queue
        AsyncTask task = new MyTask().execute();
        // parallel
        //AsyncTask task = new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class MyTask extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            int i = 0;
            while(i<=20)
            {
                try{
                    Thread.sleep(300);
                    // pass value for onProgressUpdate
                    publishProgress(i);
                    i++;
                }catch (Exception e){
                    return(e.getLocalizedMessage());
                }
            }
            return "Button pressed";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            myTextView.setText("Counter = " + values[0]);
        }

        // get value from doInBackground
        @Override
        protected void onPostExecute(String result) {
            int cpu_cores = Runtime.getRuntime().availableProcessors();
            myTextView.setText(result + " - cpu_cores = " + cpu_cores);
        }


    }
}
