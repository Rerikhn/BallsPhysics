package com.example.ballsphysics.BallSource;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.ballsphysics.R;

import java.util.ArrayList;

/**
 * Created by Nikita on 26.11.2017.
 */

public class BallsGame extends View {

    // Draw frame per second
    private static int UPDATE_RATE = 120;
    //Main Thread
    private Thread game;
    //private Thread timer;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    //
    private boolean collide = getCollide();
    private boolean eatSmall = getEatSmall();
    private boolean reverse = getReverse();
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
        for (Ball ball : balls) ball.paint(canvas);
    }

    public void start() {
        game = new Thread() {
            @Override
            public void run() {
                while (true) {
                    updateFrame(); //
                    postInvalidate(); //repaint
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
            balls.get(i).movePhysics();
            //balls.get(i).movePhysics();
            for (int j = i + 1; j < balls.size(); j++) {
                if (balls.get(i).colliding(balls.get(j))) {
                    //countCollide++;
                    balls.get(i).resolveCollision(balls.get(j), collide);
                    balls.get(i).eatSmall(balls.get(j), j, i, balls, eatSmall);
                    balls.get(i).reverseColor(balls.get(j), reverse);
                    //balls.get(i).resolveCollision(balls.get(j)/*, truthCollide)*/);
                    //balls.get(i).reverseColor(balls.get(j));
                    //balls.get(i).eatSmall(balls.get(j), j, i, balls, truthIO);
                    //ball[i].agarIO(ball[j], balls, j, truthIO);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //count += click;
                if(balls.size() <= 30)
                balls.add(new Ball(getContext()));
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

    public boolean getReverse () {
        return reverse;
    }

    public void setReverse (boolean b) {
        this.reverse = b;
    }

    public boolean getCollide () {
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
