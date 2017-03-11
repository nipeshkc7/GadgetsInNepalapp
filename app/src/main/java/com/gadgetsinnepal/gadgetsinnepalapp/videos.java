package com.gadgetsinnepal.gadgetsinnepalapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class videos extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    Button b;
    YouTubePlayerView youTubePlayerView;
    //String VIDEO_ID="aSdat-o01nk";

    String VIDEO_ID="";

    String API_KEY="AIzaSyCP0ZNI79j_mdN8wriQ1RwQiYyJ0n-LMBQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        youTubePlayerView=(YouTubePlayerView) findViewById(R.id.youtubeplayer);

        VIDEO_ID=getIntent().getStringExtra("videoId");
        Log.d("VideoID",VIDEO_ID);
        youTubePlayerView.initialize(API_KEY,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        Toast.makeText(this,"INITIALIZED",Toast.LENGTH_LONG).show();
        if(!b){
            youTubePlayer.loadVideo(VIDEO_ID);
            //youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this,"Failed initialization"+youTubeInitializationResult,Toast.LENGTH_LONG).show();
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener=new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

   private PlayerStateChangeListener playerStateChangeListener=new PlayerStateChangeListener() {
       @Override
       public void onLoading() {

       }

       @Override
       public void onLoaded(String s) {

       }

       @Override
       public void onAdStarted() {

       }

       @Override
       public void onVideoStarted() {

       }

       @Override
       public void onVideoEnded() {

       }

       @Override
       public void onError(YouTubePlayer.ErrorReason errorReason) {

       }
   };
}
