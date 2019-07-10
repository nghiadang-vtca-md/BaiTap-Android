package com.example.videoplayer;

import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class VideoPlayerActivity extends AppCompatActivity {

    private BroadcastReceiver receiver;

    private static final int REQUEST_CODE = 101;

    private VideoView videoView;
    private MediaController mediaController;
    String TAG = "VideoPlayer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        configureVideoView();
    }

    private void configureVideoView() {
        videoView = (VideoView)findViewById(R.id.videoView1);
        videoView.setVideoPath("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4");

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                Log.i(TAG,"Duration = " + videoView.getDuration());
            }
        });

        videoView.start();
    }

    public void enterPipMode(View view){
        Button pipButton = (Button)findViewById(R.id.pipButton);

        Rational rational = new Rational(videoView.getWidth(), videoView.getHeight());

        PictureInPictureParams params = new PictureInPictureParams.Builder()
                                            .setAspectRatio(rational)
                                            .build();

        pipButton.setVisibility(View.INVISIBLE);
        videoView.setMediaController(null);
        enterPictureInPictureMode(params);
    }

    private void createPipAction(){
        final ArrayList<RemoteAction> actions = new ArrayList<>();

        Intent actionIntent = new Intent("com.example.videoplayer.VIDEO_INFO");

        final PendingIntent pendingIntent = PendingIntent
                                            .getBroadcast(VideoPlayerActivity.this, REQUEST_CODE, actionIntent, 0);

        final Icon icon = Icon.createWithResource(VideoPlayerActivity.this, R.drawable.ic_info_24dp);

        RemoteAction remoteAction = new RemoteAction(icon, "Info", "Video info", pendingIntent);
        actions.add(remoteAction);

        PictureInPictureParams params = new PictureInPictureParams.Builder()
                                            .setActions(actions)
                                            .build();
        setPictureInPictureParams(params);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);

        Button pipButton = (Button)findViewById(R.id.pipButton);

        if(isInPictureInPictureMode){
            // using Broadcast
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.example.videoplayer.VIDEO_INFO");

            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Toast.makeText(context,"Favorite Home Movie Clips", Toast.LENGTH_LONG).show();
                }
            };
            registerReceiver(receiver, filter);
            //
            createPipAction();
        }else{
            pipButton.setVisibility(View.VISIBLE);
            videoView.setMediaController(mediaController);

            if(receiver != null){
                unregisterReceiver(receiver);
            }
        }
    }

}
