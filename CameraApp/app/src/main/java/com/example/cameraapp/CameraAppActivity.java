package com.example.cameraapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CameraAppActivity extends AppCompatActivity {

    private static final int VIDEO_CAPTURE = 101;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri videoUri = data.getData();

        if(requestCode == VIDEO_CAPTURE){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Video save to: \n" + videoUri, Toast.LENGTH_LONG).show();
            } else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Video recording cancelled.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Fail to record video", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_app);

        Button recordButton = (Button)findViewById(R.id.recordButton);

        if(!hasCamera()){
            recordButton.setEnabled(false);
        }
    }

    public void startRecording(View view){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    private boolean hasCamera(){
        return (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY));
    }
}
