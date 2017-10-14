package com.gadgetsinnepal.gadgetsinnepalapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        int secondsDelayed=1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(splashscreen.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 4000);
    }


//
//    public ObjectAnimator getProgressAnimator() {
//        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar1, "progress", 10000, 0);
//        progressAnimator.start();
//        progressAnimator.setDuration(4000);
//        return progressAnimator;
//    }
}

