package com.example.maruta.braintrainer;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrainMain extends AppCompatActivity {
    /**
     * problemView (the problems)
     * finalView (the final Score)
     * clockTimer countdown
     * scoreView the currect score
     * restartButton restart the game
     */

    //Ui
    private Button button12, button11, button21, button22, restartButton;
    private TextView problemView, clockTimer, scoreView, finalView;
    private List<Button> buttons;

    //Rest
    private CountDownTimer countDown;


    private boolean isOn = false;
    private int solution;
    private int choice;
    private int leftOp, rightOp;
    private int correctAnswers;
    private int rounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain_main);

        rounds = 1;
        correctAnswers = 0;
        initUi();




    }

    /**Initialize the ui elems**/
    private void initUi() {

        button11 = findViewById(R.id.button11);
        button12 = findViewById(R.id.button12);
        button21 = findViewById(R.id.button21);
        button22 = findViewById(R.id.button22);

        buttons = new ArrayList<>();

        buttons.add(button11);
        buttons.add(button12);
        buttons.add(button21);
        buttons.add(button22);

        restartButton = findViewById(R.id.restartButton);

        problemView = findViewById(R.id.problemView);
        problemView.setText("");
        clockTimer = findViewById(R.id.clockTimer);
        scoreView = findViewById(R.id.scoreView);
        finalView = findViewById(R.id.finalView);

        hideUi();

        countDown = new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                clockTimer.setText(millisUntilFinished/1000 + "s");
                isOn = true;

            }

            @Override
            public void onFinish() {

                hideUi();
                isOn = false;
                restartButton.setText("Start Again?");
            }
        };

        //setup the listeners
        listSet();
    }

    private void listSet(){

        restartButton.setOnClickListener((View event) -> {
            activateGame();
        });


        button11.setOnClickListener((View event) -> {
            checkSolution(event);
        });

        button11.setTag(1);

        button12.setOnClickListener((View event) -> {
            checkSolution(event);
        });

        button12.setTag(2);

        button21.setOnClickListener((View event) -> {
            checkSolution(event);
        });

        button21.setTag(3);

        button22.setOnClickListener((View event) -> {
            checkSolution(event);
        });

        button22.setTag(4);


    }

    private void generateChoice() {

        Random rng = new Random();

        int fix;

        for(Button b: buttons){
            if((int)b.getTag() == choice){
                b.setText("'" + solution + "'");

            } else {
                fix = rng.nextInt(45) + 1;
                b.setText("'" + (solution + fix) + "'");
            }
        }

    }


    private void checkSolution(View v) {

        if(isOn) {
            if ((int) v.getTag() == choice) {
                incrementCorrect();
                incrementRounds();
                updateScore();
                generateMath();
            } else {
                incrementRounds();
                generateMath();
            }

            /**check if this is the last round**/

            if (rounds == 16) {
                endGame();
                isOn = false;
            }
        }

    }

    private void endGame() {

        countDown.cancel();
        isOn = false;

        /*disable the buttons*/
        for(Button b: buttons){
            b.setEnabled(false);
        }

        restartButton.setText("Play Another?");
        finalView.setText("Your Score is: " + scoreView.getText());
        finalView.setVisibility(View.VISIBLE);


    }

    private void hideUi(){

        if(finalView.getVisibility() == View.VISIBLE){

            finalView.setVisibility(View.INVISIBLE);

        } else {

            finalView.setVisibility(View.VISIBLE);
        }

    }

    private void generateMath(){

        Random rng = new Random();

        int operation = rng.nextInt(4) + 1;


        if(operation < 2){
            leftOp = rng.nextInt(100);
            rightOp = rng.nextInt(100);
        } else {
            leftOp = rng.nextInt(20);
            rightOp = rng.nextInt(20);
        }

        if(operation == 1){
            problemView.setText(leftOp + "+" + rightOp);
            solution = leftOp + rightOp;
        } else if(operation == 2){

            if(leftOp < rightOp){
                int aux = rightOp;
                rightOp = leftOp;
                leftOp = aux;
            }

            problemView.setText(leftOp + "-" + rightOp);
            solution = leftOp - rightOp;
        }else  if(operation == 3){
            problemView.setText(leftOp + "*" + rightOp);
            solution = leftOp * rightOp;
        } else if(operation == 4){
            problemView.setText(leftOp + "%" + rightOp);
            solution = leftOp % rightOp;
        }

        choice = rng.nextInt(4) + 1;


        generateChoice();

    }

    private void activateGame(){

        if(!isOn) {

            restartGame();

            finalView.setVisibility(View.INVISIBLE);

            for (Button b : buttons) {
                if (!b.isEnabled()) {
                    b.setEnabled(true);
                }
            }

            isOn = true;


            generateMath();
            countDown.start();

        } else {

            restartGame();
        }


    }


    private void restartGame(){

        isOn = true;
        correctAnswers = 0;
        rounds = 0;
        scoreView.setText("0/16");
        hideUi();
        countDown.start();
        generateMath();


    }

    /*increment the correct choice*/
    private void incrementCorrect(){correctAnswers ++;}

    private void incrementRounds(){rounds ++;}

    private int getCorrect(){return correctAnswers;}

    private void updateScore(){scoreView.setText(getCorrect()+ "/16");}


}
