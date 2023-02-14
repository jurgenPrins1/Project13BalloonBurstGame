package com.example.balloonburstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.reflect.Array.getInt;

public class ResultActivity extends AppCompatActivity {

    private TextView textViewInfo,textViewMyScore,textViewHighestScore;
    private Button buttonPlayAgain,buttonQuitGame;
    int score;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewInfo = findViewById(R.id.textViewInfo);
        textViewMyScore = findViewById(R.id.textViewMyScore);
        textViewHighestScore = findViewById(R.id.textViewHighest);
        buttonPlayAgain = findViewById(R.id.buttonPlayAgain);
        buttonQuitGame = findViewById(R.id.buttonQuitGame);

        score = getIntent().getIntExtra("score",0);
        textViewMyScore.setText("Your Score : "+score);

        sharedPreferences = this.getSharedPreferences("Score", Context.MODE_PRIVATE);
        int highestScore = sharedPreferences.getInt("highestScore", 0);

        if (score >= highestScore){
            sharedPreferences.edit().putInt("highestScore",score).apply();
            textViewHighestScore.setText("Highest Score : "+score);
            textViewInfo.setText("new highscore");
        }else {
            textViewHighestScore.setText("Highest Score : "+highestScore);
            textViewInfo.setText("u suck");
        }

        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        buttonQuitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);

            }
        });
    }
}