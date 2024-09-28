package com.alpha01.darkludo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView ludoBoard;
    private ImageView ludoBoardblur;
    private Button restartButton;

    private int currentPlayerIndex;
    private int[][] cci = {
            {40,40,40},
            {40,40,40}
    };

    private int selectedPieceIndex;
    private int diceRoll;
    private Random random;
    private TextView diceval;
    private TextView health[][]= {
            {null, null, null},
            {null, null, null}
    };

    private int stage;
    private int healthvalue[][]= {
            {0,0,0},
            {0,0,0}
    };
    private Boolean[][] opened = {
            {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
            {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE}
    };

    private Boolean[][] passed = {
            {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE},
            {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE}
    };

    private ImageView pc[][]= {
            {null, null, null},
            {null, null, null}
    };

    private ImageView ar[][]= {
            {null, null, null, null},
            {null, null, null, null}
    };
    private ImageView dr1;
    private ImageView dr2;

    private ImageView bg1;
    private ImageView bg2;


    private float[][] coordinates = {
            {0.135922330097087f,0.172645739910314f},
            {0.16747572815534f,0.217488789237668f},
            {0.199029126213592f,0.266816143497758f},
            {0.230582524271845f,0.312780269058296f},
            {0.257281553398058f,0.359865470852018f},
            {0.293689320388349f,0.409192825112108f},
            {0.325242718446602f,0.461883408071749f},
            {0.293689320388349f,0.51457399103139f},
            {0.257281553398058f,0.56390134529148f},
            {0.230582524271845f,0.610986547085202f},
            {0.199029126213592f,0.65695067264574f},
            {0.16747572815534f,0.70627802690583f},
            {0.140776699029126f,0.751121076233184f},
            {0.422330097087379f,0.312780269058296f},
            {0.393203883495146f,0.359865470852018f},
            {0.359223300970874f,0.409192825112108f},
            {0.356796116504854f,0.51457399103139f},
            {0.390776699029126f,0.56390134529148f},
            {0.419902912621359f,0.610986547085202f},
            {0.468446601941748f,0.245515695067265f},
            {0.514563106796116f,0.312780269058296f},
            {0.543689320388349f,0.359865470852018f},
            {0.577669902912621f,0.409192825112108f},
            {0.580097087378641f,0.51457399103139f},
            {0.546116504854369f,0.56390134529148f},
            {0.516990291262136f,0.610986547085202f},
            {0.468446601941748f,0.680493273542601f},
            {0.803398058252427f,0.172645739910314f},
            {0.771844660194175f,0.217488789237668f},
            {0.740291262135922f,0.266816143497758f},
            {0.70873786407767f,0.312780269058296f},
            {0.682038834951456f,0.359865470852018f},
            {0.645631067961165f,0.409192825112108f},
            {0.614077669902913f,0.461883408071749f},
            {0.645631067961165f,0.51457399103139f},
            {0.682038834951456f,0.56390134529148f},
            {0.70873786407767f,0.610986547085202f},
            {0.740291262135922f,0.65695067264574f},
            {0.771844660194175f,0.70627802690583f},
            {0.798543689320388f,0.751121076233184f}

    };


    private float[][] homeCoordinates = {
            {0.118932038834951f,0.131165919282511f},
            {0.822815533980582f,0.131165919282511f},
            {0.118932038834951f,0.795964125560538f},
            {0.825242718446602f,0.797085201793722f}

    };

    private float[][] stopCoordinates = {
            {0.325242718446602f,0.461883408071749f},    //7
            {0.468446601941748f,0.245515695067265f},    //20
            {0.468446601941748f,0.680493273542601f},    //27
            {0.614077669902913f,0.461883408071749f}     //34

    };

    // hash map to store connections between coordinates array
    private Map<Integer, List<Integer>> connections = new HashMap<>();



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ludoBoard = findViewById(R.id.ludoBoard);
        ludoBoardblur = findViewById(R.id.lbb);

        dr1 = findViewById(R.id.dr1);
        dr2 = findViewById(R.id.dr2);
        ar[0][0] = findViewById(R.id.ar11);
        ar[0][1] = findViewById(R.id.ar12);
        ar[0][2] = findViewById(R.id.ar13);
        ar[0][3] = findViewById(R.id.ar14);
        ar[1][0] = findViewById(R.id.ar21);
        ar[1][1] = findViewById(R.id.ar22);
        ar[1][2] = findViewById(R.id.ar23);
        ar[1][3] = findViewById(R.id.ar24);
        bg1 = findViewById(R.id.bg1);
        bg2 = findViewById(R.id.bg2);

        restartButton = findViewById(R.id.restart);

        diceval = findViewById(R.id.textView);
        health[0][0] = findViewById(R.id.h11);
        health[0][1] = findViewById(R.id.h12);
        health[0][2] = findViewById(R.id.h13);
        health[1][0] = findViewById(R.id.h21);
        health[1][1] = findViewById(R.id.h22);
        health[1][2] = findViewById(R.id.h23);

        pc[0][0] = findViewById(R.id.pc11);
        pc[0][1] = findViewById(R.id.pc12);
        pc[0][2] = findViewById(R.id.pc13);

        pc[1][0] = findViewById(R.id.pc21);
        pc[1][1] = findViewById(R.id.pc22);
        pc[1][2] = findViewById(R.id.pc23);

        random = new Random();
        currentPlayerIndex = 0;
        diceRoll = 0;
        stage = 1;

        connections.put(0, Arrays.asList(40, 40, 1, 40));
        connections.put(1, Arrays.asList(0, 40, 2, 40));
        connections.put(2, Arrays.asList(1, 40, 3, 40));
        connections.put(3, Arrays.asList(2, 40, 4, 40));
        connections.put(4, Arrays.asList(3, 40, 5, 40));
        connections.put(5, Arrays.asList(4, 40, 6, 40));
        connections.put(6, Arrays.asList(5, 7, 16, 15));
        connections.put(7, Arrays.asList(40, 8, 40, 6));
        connections.put(8, Arrays.asList(40, 9, 40, 7));
        connections.put(9, Arrays.asList(40, 10, 40, 8));
        connections.put(10, Arrays.asList(40, 11, 40, 9));
        connections.put(11, Arrays.asList(40, 12, 40, 10));
        connections.put(12, Arrays.asList(40, 40, 40, 11));
        connections.put(13, Arrays.asList(40, 14, 40, 19));
        connections.put(14, Arrays.asList(40, 15, 40, 13));
        connections.put(15, Arrays.asList(40, 6, 40, 14));
        connections.put(16, Arrays.asList(6, 40, 17, 40));
        connections.put(17, Arrays.asList(16, 40, 18, 40));
        connections.put(18, Arrays.asList(17, 40, 26, 40));
        connections.put(19, Arrays.asList(40, 13, 20, 40));
        connections.put(20, Arrays.asList(19, 40, 21, 40));
        connections.put(21, Arrays.asList(20, 40, 22, 40));
        connections.put(22, Arrays.asList(21, 40, 33, 40));
        connections.put(23, Arrays.asList(40, 24, 40, 33));
        connections.put(24, Arrays.asList(40, 25, 40, 23));
        connections.put(25, Arrays.asList(40, 26, 40, 24));
        connections.put(26, Arrays.asList(18, 40, 40, 25));
        connections.put(27, Arrays.asList(40, 28, 40, 40));
        connections.put(28, Arrays.asList(40, 29, 40, 27));
        connections.put(29, Arrays.asList(40, 30, 40, 28));
        connections.put(30, Arrays.asList(40, 31, 40, 29));
        connections.put(31, Arrays.asList(40, 32, 40, 30));
        connections.put(32, Arrays.asList(40, 33, 40, 31));
        connections.put(33, Arrays.asList(22, 23, 34, 32));
        connections.put(34, Arrays.asList(33, 40, 35, 40));
        connections.put(35, Arrays.asList(34, 40, 36, 40));
        connections.put(36, Arrays.asList(35, 40, 37, 40));
        connections.put(37, Arrays.asList(36, 40, 38, 40));
        connections.put(38, Arrays.asList(37, 40, 39, 40));
        connections.put(39, Arrays.asList(38, 40, 40, 40));


        ludoBoard.post(new Runnable() {
            @Override
            public void run() {

                for (int j = 0; j < 3; j++) {
                    pc[0][j].setX(homeCoordinates[2][0] * ludoBoard.getWidth());
                    pc[0][j].setY(homeCoordinates[2][1] * ludoBoard.getHeight());
                }
                for (int j = 0; j < 3; j++) {
                    pc[1][j].setX(homeCoordinates[0][0] * ludoBoard.getWidth());
                    pc[1][j].setY(homeCoordinates[0][1] * ludoBoard.getHeight());
                }

                dr1.setX(186 / 412f * ludoBoard.getWidth());
                dr1.setY(0.80f * ludoBoard.getHeight());

                dr2.setX(186 / 412f * ludoBoard.getWidth());
                dr2.setY(0.10f * ludoBoard.getHeight());

                ar[0][0].setX(150 / 412f * ludoBoard.getWidth());
                ar[0][0].setY(689 / 892f * ludoBoard.getHeight());

                ar[0][1].setX(150 / 412f * ludoBoard.getWidth());
                ar[0][1].setY(752 / 892f * ludoBoard.getHeight());

                ar[0][2].setX(230 / 412f * ludoBoard.getWidth());
                ar[0][2].setY(752 / 892f * ludoBoard.getHeight());

                ar[0][3].setX(230 / 412f * ludoBoard.getWidth());
                ar[0][3].setY(689 / 892f * ludoBoard.getHeight());

                ar[1][0].setX(150 / 412f * ludoBoard.getWidth());
                ar[1][0].setY(64 / 892f * ludoBoard.getHeight());

                ar[1][1].setX(150 / 412f * ludoBoard.getWidth());
                ar[1][1].setY(127 / 892f * ludoBoard.getHeight());

                ar[1][2].setX(230 / 412f * ludoBoard.getWidth());
                ar[1][2].setY(127 / 892f * ludoBoard.getHeight());

                ar[1][3].setX(230 / 412f * ludoBoard.getWidth());
                ar[1][3].setY(64 / 892f * ludoBoard.getHeight());

                bg1.setX(ludoBoard.getWidth() * 0.5f - 50);
                bg1.setY(ludoBoard.getHeight() * 0.5f - 110);

                bg2.setX(ludoBoard.getWidth() * 0.5f - 50);
                bg2.setY(ludoBoard.getHeight() * 0.5f - 110);

                diceval.setX(ludoBoard.getWidth() * 0.5f - 55);
                diceval.setY(ludoBoard.getHeight() * 0.5f - 110);

                health[0][0].setX(ludoBoard.getWidth() * 0.5f - 45);
                health[0][0].setY(ludoBoard.getHeight() * 0.95f - 80);
                health[0][1].setX(ludoBoard.getWidth() * 0.5f - 145);
                health[0][1].setY(ludoBoard.getHeight() * 0.95f - 80);
                health[0][2].setX(ludoBoard.getWidth() * 0.5f + 55);
                health[0][2].setY(ludoBoard.getHeight() * 0.95f - 80);

                health[1][0].setX(ludoBoard.getWidth() * 0.5f - 45);
                health[1][0].setY(ludoBoard.getHeight() * 0.05f - 80);
                health[1][1].setX(ludoBoard.getWidth() * 0.5f - 145);
                health[1][1].setY(ludoBoard.getHeight() * 0.05f - 80);
                health[1][2].setX(ludoBoard.getWidth() * 0.5f + 55);
                health[1][2].setY(ludoBoard.getHeight() * 0.05f - 80);

                bg1.setVisibility(View.INVISIBLE);
                bg2.setVisibility(View.INVISIBLE);
                ludoBoardblur.setVisibility(View.INVISIBLE);

                restartButton.setVisibility(View.INVISIBLE);

                currentPlayerIndex = random.nextInt(2) + 1;

                if (currentPlayerIndex == 1) {
                    bg1.setVisibility(View.VISIBLE);
                    bg2.setVisibility(View.INVISIBLE);
                }
                if (currentPlayerIndex == 2) {
                    bg1.setVisibility(View.INVISIBLE);
                    bg2.setVisibility(View.VISIBLE);
                }

                diceval.setText("Start");
                Log.i("On create", "cconnections: " + connections.get(0));

            }
        });

        dr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (stage == 1 && currentPlayerIndex == 1) {

                    diceRoll = random.nextInt(4) + 1;
                    stage = 2;
                    diceval.setAlpha(1f);
                    diceval.setText(String.valueOf(diceRoll));

                    if ((opened[0][0] == Boolean.FALSE && opened[0][1] == Boolean.FALSE && opened[0][2] == Boolean.FALSE && diceRoll != 3)) {
                        currentPlayerIndex = 2;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bg1.setVisibility(View.INVISIBLE);
                                bg2.setVisibility(View.VISIBLE);
                            }
                        }, 700);
                        stage = 1;
                        ObjectAnimator fadeOut2 = ObjectAnimator.ofFloat(diceval, "alpha", 1f, 0f);
                        fadeOut2.setDuration(1000);
                        fadeOut2.start();

                    }

                }

            }
        });

        dr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (stage == 1 && currentPlayerIndex == 2) {

                    diceRoll = random.nextInt(4) + 1;
                    stage = 2;
                    diceval.setAlpha(1f);
                    diceval.setText(String.valueOf(diceRoll));

                    if ((opened[1][0] == Boolean.FALSE && opened[1][1] == Boolean.FALSE && opened[1][2] == Boolean.FALSE && diceRoll != 3)) {
                        currentPlayerIndex = 1;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bg1.setVisibility(View.VISIBLE);
                                bg2.setVisibility(View.INVISIBLE);
                            }
                        }, 700);
                        stage = 1;
                        ObjectAnimator fadeOut2 = ObjectAnimator.ofFloat(diceval, "alpha", 1f, 0f);
                        fadeOut2.setDuration(1000);
                        fadeOut2.start();

                    }

                }

            }
        });

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                final int finalI = i;
                final int finalJ = j;
                pc[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("click pc", "stage: " + stage);
                        Log.i("click pc", "Player: " + currentPlayerIndex);

                        if (currentPlayerIndex == finalI + 1 && stage == 2) {
                            if (opened[finalI][finalJ]) {
                                stage = 3;
                                selectedPieceIndex = finalJ;

                                animateSelectedPiece(pc[finalI][finalJ]);
                                Log.i("click pc if", "stage: " + stage);

                            } else {
                                stage = 1;
                                if (diceRoll == 3) {
                                    opened[finalI][finalJ] = Boolean.TRUE;
                                    healthvalue[finalI][finalJ] = 12;
                                    health[finalI][finalJ].setText(String.valueOf(healthvalue[finalI][finalJ]));
                                    switch (finalI) {
                                        case 0:
                                            cci[finalI][finalJ] = 12;
                                            break;
                                        case 1:
                                            cci[finalI][finalJ] = 0;
                                            break;
                                    }

                                    Log.i("click pc if", "stage: " + stage);
                                    Log.i("click pc if", "current coord: " + cci[finalI][finalJ]);

                                    pc[finalI][finalJ].setX(coordinates[cci[finalI][finalJ]][0] * ludoBoard.getWidth());
                                    pc[finalI][finalJ].setY(coordinates[cci[finalI][finalJ]][1] * ludoBoard.getHeight());

                                }

                            }
                        }
                    }
                });
            }

        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                final int finalI = i;
                final int finalJ = j;
                ar[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (stage == 3 && currentPlayerIndex == finalI + 1 && diceRoll != 0) {

                            pass(finalI, finalJ, selectedPieceIndex);
                            animate(finalI, selectedPieceIndex, finalJ, pc[finalI][selectedPieceIndex]);

                            if (diceRoll == 0) {
                                stage = 1;
                                fadeout(currentPlayerIndex, diceval);
                                resetPieceSize(pc[finalI][selectedPieceIndex]);
                                damage(finalI, selectedPieceIndex);
                            }
                        }
                    }
                });
            }
        }

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

    }

    private void animateSelectedPiece(ImageView piece) {
        // Scale animation to make the piece "pop" and stay enlarged
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(piece, "scaleX", 1f, 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(piece, "scaleY", 1f, 1.2f);
        scaleX.setDuration(0); // Instantly scale up
        scaleY.setDuration(0); // Instantly scale up

        // Brighten up the piece by increasing alpha (or apply a color filter for actual brightness)
        ObjectAnimator brighten = ObjectAnimator.ofFloat(piece, "alpha", 1f, 0.65f); // Placeholder for brightness effect
        brighten.setDuration(0);

        // AnimatorSet to play the scale and brighten animations together
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, brighten);
        animatorSet.start();
    }

    private void resetPieceSize(ImageView piece) {
        piece.setScaleX(1f); // Reset scale to original size
        piece.setScaleY(1f); // Reset scale to original size
        piece.setAlpha(1f); // Reset alpha to original state
    }


    private void animate(int a, int b, int c, ImageView piece) {

        Log.i("animate", "a b c: " + a + b + c);

        if (cci[a][b] != 40) {

            List<Integer> neighbors = connections.get(cci[a][b]);
            if (neighbors.get(c) == 40) {
                diceRoll = diceRoll + 1;
            } else {
                cci[a][b] = neighbors.get(c);
                ObjectAnimator animationX = ObjectAnimator.ofFloat(piece, "x", coordinates[cci[a][b]][0] * ludoBoard.getWidth());
                ObjectAnimator animationY = ObjectAnimator.ofFloat(piece, "y", coordinates[cci[a][b]][1] * ludoBoard.getHeight());
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animationX, animationY);
                animatorSet.setDuration(500);
                animatorSet.start();
            }
            diceRoll = diceRoll - 1;

        }

    }

    private void fadeout(int cpi, TextView dv) {

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(dv, "alpha",  1f, 0f);
        fadeOut.setDuration(500);
        fadeOut.start();

        switch (cpi) {
            case 1:
                bg1.setVisibility(View.INVISIBLE);
                bg2.setVisibility(View.VISIBLE);
                currentPlayerIndex=2;
                break;
            case 2:
                bg1.setVisibility(View.VISIBLE);
                bg2.setVisibility(View.INVISIBLE);
                currentPlayerIndex=1;
                break;
        }
    }

    private void pass(int a, int b, int spi) {

        switch (a){
            case 0:
                Log.i("pass 0", "a b spi: " + a + b + spi);
                if (cci[a][spi]==27 && diceRoll==1 && b == 3) {
                    cci[a][spi] = 40;
                    ObjectAnimator animationX = ObjectAnimator.ofFloat(pc[a][spi], "x", homeCoordinates[1][0] * ludoBoard.getWidth());
                    ObjectAnimator animationY = ObjectAnimator.ofFloat(pc[a][spi], "y", homeCoordinates[1][1] * ludoBoard.getHeight());
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(animationX, animationY);
                    animatorSet.setDuration(500);
                    animatorSet.start();
                    passed[a][spi] = Boolean.TRUE;
                    health[a][spi].setText("X");
                    restart();
                    diceRoll = diceRoll-1;
                }
                break;
            case 1:
                Log.i("pass 1", "a b spi: " + a + b + spi);
                if (cci[a][spi]==39 && diceRoll==1 && b == 3) {
                    cci[a][spi] = 40;
                    ObjectAnimator animationX = ObjectAnimator.ofFloat(pc[a][spi], "x", homeCoordinates[3][0] * ludoBoard.getWidth());
                    ObjectAnimator animationY = ObjectAnimator.ofFloat(pc[a][spi], "y", homeCoordinates[3][1] * ludoBoard.getHeight());
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(animationX, animationY);
                    animatorSet.setDuration(500);
                    animatorSet.start();
                    passed[a][spi] = Boolean.TRUE;
                    health[a][spi].setText("X");
                    restart();
                    diceRoll = diceRoll-1;
                }
                break;

        }

    }

    private void restart() {
        if (passed[0][0] && passed[0][1] && passed[0][2]) {

            bg1.setVisibility(View.VISIBLE);
            bg2.setVisibility(View.INVISIBLE);

            // Create and configure the scaleX animation
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(bg1, "scaleX", 10f);
            scaleXAnimator.setDuration(300); // Duration in milliseconds

            // Create and configure the scaleY animation
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(bg1, "scaleY", 10f);
            scaleYAnimator.setDuration(300); // Duration in milliseconds

            // Start the animations
            scaleXAnimator.start();
            scaleYAnimator.start();

            endscreen();

        }

        if (passed[1][0] && passed[1][1] && passed[1][2]) {

            bg1.setVisibility(View.INVISIBLE);
            bg2.setVisibility(View.VISIBLE);

            // Create and configure the scaleX animation
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(bg2, "scaleX", 10f);
            scaleXAnimator.setDuration(300); // Duration in milliseconds

            // Create and configure the scaleY animation
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(bg2, "scaleY", 10f);
            scaleYAnimator.setDuration(300); // Duration in milliseconds

            // Start the animations
            scaleXAnimator.start();
            scaleYAnimator.start();

            endscreen();

        }

    }

    private void endscreen() {

        diceval.setText("   You         🧐Won");

        // Create and configure the scaleX animation
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(diceval, "scaleX", 5f);
        scaleXAnimator.setDuration(300); // Duration in milliseconds

        // Create and configure the scaleY animation
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(diceval, "scaleY", 5f);
        scaleYAnimator.setDuration(300); // Duration in milliseconds

        // Start the animations
        scaleXAnimator.start();
        scaleYAnimator.start();

        restartButton.setX(ludoBoard.getWidth() * 0.5f - 125);
        restartButton.setY(ludoBoard.getHeight() * 0.5f + 150);
        restartButton.setVisibility(View.VISIBLE);
        ludoBoardblur.setVisibility(View.VISIBLE);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                pc[i][j].setVisibility(View.INVISIBLE);
            }
        }

    }

    private void damage( int a, int b) {

        switch (a) {
            case 0:
                int count = 0;
                for (int i = 0; i < 3; i++) {
                    if (cci[a][b] == cci[1][i]) {
                        count++;
                    }
                }
                for (int i = 0; i < 3; i++) {
                    if (cci[a][b] == cci[1][i]) {
                        healthvalue[1][i] = healthvalue[1][i] - healthvalue[0][b]/count;
                        healthvalue[0][b] = healthvalue[0][b] - healthvalue[0][b]/(2*count);
                        health[1][i].setText(String.valueOf(healthvalue[1][i]));
                        health[0][b].setText(String.valueOf(healthvalue[0][b]));
                        if (healthvalue[1][i] <= 0) {
                            opened[1][i] = Boolean.FALSE;
                            health[1][i].setText("");
                            pc[1][i].setX(homeCoordinates[0][0] * ludoBoard.getWidth());
                            pc[1][i].setY(homeCoordinates[0][1] * ludoBoard.getHeight());
                            cci[1][i] = 40;
                        }
                    }
                }
                break;
            case 1:
                int count1 = 0;
                for (int i = 0; i < 3; i++) {
                    if (cci[a][b] == cci[0][i]) {
                        count1++;
                    }
                }
                for (int i = 0; i < 3; i++) {
                    if (cci[a][b] == cci[0][i]) {
                        healthvalue[0][i] = healthvalue[0][i] - healthvalue[1][b]/count1;
                        healthvalue[1][b] = healthvalue[1][b] - healthvalue[1][b]/(2*count1);
                        health[0][i].setText(String.valueOf(healthvalue[0][i]));
                        health[1][b].setText(String.valueOf(healthvalue[1][b]));
                        if (healthvalue[0][i] <= 0) {
                            opened[0][i] = Boolean.FALSE;
                            health[0][i].setText("");
                            pc[0][i].setX(homeCoordinates[2][0] * ludoBoard.getWidth());
                            pc[0][i].setY(homeCoordinates[2][1] * ludoBoard.getHeight());
                            cci[0][i] = 40;
                        }
                    }
                }
                break;
        }

    }

}

