package com.example.notifydemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NotifyDemoActivity extends AppCompatActivity {

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_demo);

        // get from system ???
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        // more research for : ( Context.NOTIFICATION_SERVICE ) ???

        createNotificationChannel(
                "com.example.notifydemo.news",
                "NotifyDemo News",
                "Example News Channel"
        );


    }

    protected void createNotificationChannel(String id,String name,String description) {
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(
                new long[]{100,200,300,400,500,400,300,200,400}
        );
        notificationManager.createNotificationChannel(channel);
    }

    protected void sendNotification(View view){

        Intent resultIntent = new Intent(this,ResultActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelID = "com.example.notifydemo.news";

        final Icon icon = Icon.createWithResource(NotifyDemoActivity.this, android.R.drawable.ic_dialog_info);
        Notification.Action action = new Notification.Action.Builder(icon,"Open",pendingIntent).build();

        Notification notification = new Notification.Builder(NotifyDemoActivity.this,channelID)
                .setContentTitle("Example Notification")
                .setContentText("This is an example notification.")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(channelID)
                .setContentIntent(pendingIntent)
                .setActions(action)
                .build();

        // Code
        int notificationID = 101;
        // build
        notificationManager.notify(notificationID,notification);
    }
}
