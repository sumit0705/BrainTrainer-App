package com.example.braintrainerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button button;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;

    TextView resultTextView;
    TextView pointsTextView;
    TextView sumTextView;
    TextView timerTextView;

    RelativeLayout relativeLayout;

    ArrayList<Integer> answers=new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score=0;
    int numberOfQuestions=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton= findViewById(R.id.startButton);
        sumTextView= findViewById(R.id.sumTextView);

        button= findViewById(R.id.button);
        button1= findViewById(R.id.button1);
        button2= findViewById(R.id.button2);
        button3= findViewById(R.id.button3);

        resultTextView= findViewById(R.id.resultTextView);
        pointsTextView= findViewById(R.id.pointsTextView);
        timerTextView= findViewById(R.id.timerTextView);

        playAgainButton= findViewById(R.id.playAgainButton);

        relativeLayout = findViewById(R.id.relativeLayout);
    }

    public void setButtonClick(boolean vis) {
        button.setClickable(vis);
        button1.setClickable(vis);
        button2.setClickable(vis);
        button3.setClickable(vis);
    }

    public void playAgain(View view) {

        score=0;
        numberOfQuestions=0;

        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        setButtonClick(true);

        generateQuestion();

        new CountDownTimer(15100,100) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf((millisUntilFinished)/1000)+"s");

            }

            @Override
            public void onFinish() {
                setButtonClick(false);
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");

                resultTextView.setText("Your score: "+Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
            }
        }.start();
    }

    public void generateQuestion() {
        Random rand=new Random();

        int a=rand.nextInt(20);
        int b=rand.nextInt(20);

        // for 0-plus,1-minus,2-multiply,3-divide

        int operator=rand.nextInt(4);

        sumTextView.setText(Integer.toString(a)+" + "+Integer.toString(b));

        locationOfCorrectAnswer=rand.nextInt(4);
        answers.clear();

        int incorrectAnswer;
        for(int i=0;i<4;i++) {
            if(locationOfCorrectAnswer==i) {
                answers.add(a+b);
            }
            else {
                incorrectAnswer=rand.nextInt(41);
                while(incorrectAnswer==a+b) {
                    incorrectAnswer=rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        button.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void start(View view) {

        startButton.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);

        playAgain(playAgainButton);

    }

    public void chooseAnswer(View view) {
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score++;
            resultTextView.setText("Correct!");
        }
        else {
            resultTextView.setText("Wrong!");
        }
        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));

        generateQuestion();

    }
}