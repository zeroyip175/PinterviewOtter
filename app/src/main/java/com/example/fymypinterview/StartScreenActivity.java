package com.example.fypmypinterview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class StartScreenActivity extends AppCompatActivity {
    public Button getStarted_btn;
    public ImageView otter_icon_done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        getStarted_btn = findViewById(R.id.getStarted_btn);
        init_objects();


        getStarted_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreenActivity.this, JobMain.class);
                startActivity(intent);
            }
        });


    }


    private void init_objects() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            otter_icon_done = findViewById(R.id.otter_icon_done);
            otter_icon_done.setVisibility(View.VISIBLE);

        Animation transAnimation = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE,
                Animation.ABSOLUTE, Animation.ABSOLUTE+500);
        // from x, to x, from y, to y

            transAnimation.setDuration(1000);
            transAnimation.setRepeatCount(1);
            transAnimation.setFillEnabled(false);
            AnimationSet animationSet1 = new AnimationSet(false);
            animationSet1.addAnimation(transAnimation);
            animationSet1.setInterpolator(new DecelerateInterpolator());
            animationSet1.setRepeatMode(Animation.REVERSE);
            otter_icon_done.startAnimation(animationSet1);
        }
            }, 500);
    }



}
