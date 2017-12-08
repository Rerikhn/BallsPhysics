package com.example.ballsphysics.BallSource;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

import com.example.ballsphysics.R;

import java.util.ArrayList;

/*
 * Created by Nikita on 26.11.2017.
 */

public class BallsGame extends View {

    MediaPlayer bounce= MediaPlayer.create(this.getContext(), R.raw.bounce);
    // Draw frame per second
    private static int UPDATE_RATE = 120;
    //private Thread timer;
    private ArrayList<Ball> balls = new ArrayList<>();
    //
    private boolean collide = getCollide();
    private boolean eatSmall = getEatSmall();
    private boolean reverse = getReverse();
    private boolean paused = false;
    // Count of balls
    /*int count = 0;
    int click = 0;*/

    public BallsGame(Context context) {
        super(context);
        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.BLACK);
        for (int i = 0; i < balls.size(); i++)
            balls.get(i).paint(canvas);
    }

    public void start() {
        Thread game = new Thread() {
            @Override
            public void run() {
                while (true) {
                    long beginTimeMillis, timeTakenMillis, timeLeftMillis;
                    beginTimeMillis = System.currentTimeMillis();
                    if (!paused) {
                        updateFrame();
                        postInvalidate();
                    }
                    timeTakenMillis = System.currentTimeMillis() - beginTimeMillis;
                    timeLeftMillis = 1000L / UPDATE_RATE - timeTakenMillis;
                    if (timeLeftMillis < 5) timeLeftMillis = 5; // Set a minimum
                    try {
                        Thread.sleep(timeLeftMillis);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        };
        game.start();
    }

    public void updateFrame() {
        //Ball[] ball = balls.toArray(new Ball[balls.size()]);
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).movePhysics();
            bounce.setVolume(balls.get(i).getMass(),balls.get(i).getMass());
            //balls.get(i).movePhysics();
            for (int j = i + 1; j < balls.size(); j++) {
                if (balls.get(i).colliding(balls.get(j))) {
                    //countCollide++;
                    balls.get(i).resolveCollision(balls.get(j), collide);
                    bounce.start();
                    balls.get(i).reverseColor(balls.get(j), reverse);
                    balls.get(i).eatSmall(balls.get(j), j, i, balls, eatSmall);
                }
            }
        }
    }

    //@Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //count += click;
                if (balls.size() <= 100)
                    balls.add(new Ball(this.getContext()));
        }
        return true;
    }

    /*public void addByTimer(boolean t) {
        if (t == true) {
            timer = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (balls.size() < 1500) {
                        count += click;
                        for (int i = 0; i < click; i++)
                            balls.add(new Ball(getContext()));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });
            timer.start();
        } else {
            timer.stop();
        }
    }*/

    public boolean getReverse() {
        return reverse;
    }

    public void setReverse(boolean b) {
        this.reverse = b;
    }

    public boolean getCollide() {
        return collide;
    }

    public void setCollide(boolean b) {
        this.collide = b;
    }

    public boolean getEatSmall() {
        return eatSmall;
    }

    public void setEatSmall(boolean b) {
        this.eatSmall = b;
    }
}
