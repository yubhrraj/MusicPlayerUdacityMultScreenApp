package com.pathakcalc.musicplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer music;
    private Handler handler;
    private Runnable mrunnable;
    private SeekBar seek;
    private Button play, pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        seek = (SeekBar)  findViewById(R.id.seekBar);
        handler = new Handler();

        music = MediaPlayer.create(this, R.raw.gnr_sweet);
        seek.setMax(music.getDuration());
        playCycle();


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                music.start();
                music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(MainActivity.this, "I am done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean userinput) {
                music.seekTo(progress);
                seek.setProgress(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.pause();

            }
        });

    }
    public void playCycle(){
        int mCurrentPosition = music.getCurrentPosition();
        seek.setProgress(mCurrentPosition);
        if(music.isPlaying()){
            mrunnable= new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(mrunnable, 1000);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        music.stop();
        music.release();
        music=null;
    }
}
