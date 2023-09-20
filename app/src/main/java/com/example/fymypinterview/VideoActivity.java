package com.example.fypmypinterview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Method;

public class VideoActivity extends AppCompatActivity {

    final static int VIDEO_RECORD_CODE = 101;
    private static int CAMRA_REMISSION_CODE = 100;
    Uri uriVideo = null;
    VideoView videoviewPlay;
    FloatingActionButton addVideosBtn;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        Button buttonRecording = (Button)findViewById(R.id.recording);
        Button buttonPlayback = (Button)findViewById(R.id.playback);
        videoviewPlay = (VideoView)findViewById(R.id.videoview);
        addVideosBtn = findViewById(R.id.addVideosBtn);
        buttonRecording.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (ContextCompat.checkSelfPermission(VideoActivity.this, Manifest.permission.CAMERA )== PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(VideoActivity.this, new String[]
                            { Manifest.permission.CAMERA}, CAMRA_REMISSION_CODE);

                }else {
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent,VIDEO_RECORD_CODE);
                }

            }});

        buttonPlayback.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(uriVideo == null){
                    Toast.makeText(VideoActivity.this,
                            "No Video",
                            Toast.LENGTH_LONG)
                            .show();
                }else{
                    Toast.makeText(VideoActivity.this,
                            "Playback: " + uriVideo.getPath(),
                            Toast.LENGTH_LONG)
                            .show();
                    videoviewPlay.setVideoURI(uriVideo);
                    videoviewPlay.start();
                }
            }});
        addVideosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, AddVideoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == VIDEO_RECORD_CODE) {
                uriVideo = data.getData();
                Toast.makeText(VideoActivity.this,
                        uriVideo.getPath(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        } else if (resultCode == RESULT_CANCELED) {
            uriVideo = null;
            Toast.makeText(VideoActivity.this,
                    "Cancelled!",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}