package com.example.ballsphysics.BallSource;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Nikita on 26.11.2017.
 */

public class BallsGame extends View {

    // Draw frame per second
    private static int UPDATE_RATE = 120;

    //Main Thread
    private Thread game;
    private ArrayList<Ball> balls = new ArrayList<Ball>();

    // Count of balls
    int count = 0;

    public BallsGame(Context context) {
        super(context);
        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.BLACK);
        for (Ball ball : balls) ball.paint(canvas);
    }

    public void start() {
        game = new Thread() {
            @Override
            public void run() {
                while (true) {
                    updateFrame();
                    postInvalidate(); //update repaint
                    try {
                        Thread.sleep(1000 / UPDATE_RATE);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        game.start();
    }

    public void updateFrame() {
        //Ball[] ball = balls.toArray(new Ball[balls.size()]);
        for (int i = 0; i < balls.size(); i++) {
            //ball[i].movePhysics();
            balls.get(i).movePhysics();
            for (int j = i + 1; j < balls.size(); j++) {
                if (balls.get(i).colliding(balls.get(j))) {
                    //countCollide++;
                    balls.get(i).resolveCollision(balls.get(j)/*, truthCollide)*/);
                    //balls.get(i).reverseColor(balls.get(j), truthColor);
                    //balls.get(i).eatSmall(balls.get(j), j, i, balls, truthIO);
                    //ball[i].agarIO(ball[j], balls, j, truthIO);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                balls.add(new Ball(getContext()));
        }
        return true;
    }

    /*@Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                balls.add(new Ball(getContext()));
        }
        return true;
    }*/
}
