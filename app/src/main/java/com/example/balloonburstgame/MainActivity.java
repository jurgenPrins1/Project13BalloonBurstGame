package com.example.balloonburstgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textViewCountDown,textViewTime,textViewScore;
    private ImageView b1,b2,b3,b4,b5,b6,b7,b8,b9;
    private GridLayout gridLayout;
    int score = 0;
    Handler handler;
    Runnable runnable;

    ImageView[] balloonsArray;

    MediaPlayer mediaPlayer;

    boolean status = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCountDown = findViewById(R.id.textViewCountDown);
        textViewTime = findViewById(R.id.textViewTime);
        textViewScore = findViewById(R.id.textViewScore);
        b1 = findViewById(R.id.balloon1);
        b2 = findViewById(R.id.balloon2);
        b3 = findViewById(R.id.balloon3);
        b4 = findViewById(R.id.balloon4);
        b5 = findViewById(R.id.balloon5);
        b6 = findViewById(R.id.balloon6);
        b7 = findViewById(R.id.balloon7);
        b8 = findViewById(R.id.balloon8);
        b9 = findViewById(R.id.balloon9);
        gridLayout = findViewById(R.id.gridLayout);

        mediaPlayer = MediaPlayer.create(this,R.raw.balloon_pop);

        balloonsArray = new ImageView[]{b1,b2,b3,b4,b5,b6,b7,b8,b9};


        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                textViewCountDown.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {

                balloonsControl();

                new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long l) {
                        String temp = String.valueOf(l/1000);
                        textViewTime.setText("Remaining Time : "+temp);
                    }

                    @Override
                    public void onFinish() {

                        Intent i = new Intent(MainActivity.this,ResultActivity.class);
                        i.putExtra("score",score);
                        startActivity(i);
                        finish();

                    }
                }.start();

            }
        }.start();
    }

    public void increaseScoreByOne(View view){

        score++;
        textViewScore.setText("Score : "+score);
        if (mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.start();
        }

    }

    public void balloonsControl(){

        textViewCountDown.setVisibility(View.INVISIBLE);
        textViewTime.setVisibility(View.VISIBLE);
        textViewScore.setVisibility(View.VISIBLE);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                for (ImageView balloon : balloonsArray){
                    balloon.setVisibility(View.INVISIBLE);
                }
                gridLayout.setVisibility(View.VISIBLE);

                Random random = new Random();

                int i = random.nextInt(balloonsArray.length);
                balloonsArray[i].setVisibility(View.VISIBLE);

                if (score <= 5){
                    handler.postDelayed(runnable,2000);
                } else if (score > 5 && score <=10){
                    handler.postDelayed(runnable,1500);
                } else if (score > 10 && score <= 15) {
                    handler.postDelayed(runnable,1000);
                } else if (score > 15) {
                    handler.postDelayed(runnable,500);
                }


            }
        };

        handler.post(runnable);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.volume){
            if (!status){
                mediaPlayer.setVolume(0,0);
                status = true;
            }
            else {
                mediaPlayer.setVolume(1,1);
                status = false;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}