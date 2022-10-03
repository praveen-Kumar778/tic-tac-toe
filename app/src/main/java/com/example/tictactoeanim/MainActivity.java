package com.example.tictactoeanim;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //0: forRed , 1: forYellow , 2: for empty

    int activePlayer = 0;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPosition = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameActive = true;

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameActive) {
            counter.setTranslationY(-1500);
            gameState[tappedCounter] = activePlayer;
            if (activePlayer == 0) {
                MediaPlayer jump = MediaPlayer.create(this,R.raw.jump);
                counter.setImageResource(R.drawable.red);
                jump.start();
                activePlayer = 1;
            } else {
                MediaPlayer sparkle = MediaPlayer.create(this,R.raw.sparkle);
                counter.setImageResource(R.drawable.yellow);
                sparkle.start();
                activePlayer = 0;
            }

            for (int[] winningPositions : winningPosition) {
                if (gameState[winningPositions[0]] == gameState[winningPositions[1]]
                        && gameState[winningPositions[1]] == gameState[winningPositions[2]]
                        && gameState[winningPositions[0]] != 2) {
                    gameActive = false;
                    String winner = "";
                    if (activePlayer == 0) {
                        winner = "Yellow";
                    } else {
                        winner = "Red";
                    }
                    MediaPlayer wins = MediaPlayer.create(this,R.raw.winning);
                    wins.start();
                    LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.anim_view);
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                    Button playAgain = (Button) findViewById(R.id.button);
                    playAgain.setText(R.string.playAgain);
                    TextView winText = (TextView) findViewById(R.id.text);
                    String won = winner + " "+ getString(R.string.won) ;
                    winText.setText(won);
                    winText.setVisibility(View.VISIBLE);
                    playAgain.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            animationView.pauseAnimation();
                            animationView.setImageDrawable(null);
                        }
                    },5000);
                }
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);
        }
    }

    public void reset(View view){
        Button playAgain = (Button) findViewById(R.id.button);
        TextView winText = (TextView) findViewById(R.id.text);
        playAgain.setVisibility(View.INVISIBLE);
        winText.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.GridLayout);

        for(int i=0 ;i < gridLayout.getChildCount() ;i++ ){
            ImageView counter = (ImageView) gridLayout.getChildAt(i) ;
            counter.setImageDrawable(null);
        }

        Arrays.fill(gameState, 2);
        activePlayer = 0 ;
        gameActive = true ;

    }

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}