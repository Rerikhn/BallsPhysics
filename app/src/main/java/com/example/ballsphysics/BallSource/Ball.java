package com.example.ballsphysics.BallSource;

import android.content.Context;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

/*
 * Created by Nikita on 26.11.2017.
 */

public class Ball extends View {

    private int BOX_WIDTH;
    private int BOX_HEIGHT;

    /**
     * For Vectors
     */
    private Vector2d velocity;
    private Vector2d position;
    private float radius;
    private float mass;
    /**
     * Color by ARGB [0; 255];
     */
    private int transp; //transparency 0 - full transparent, 250 - non transparent
    private int red;
    private int green;
    private int blue;

    public Ball(Context context) {
        super(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        BOX_WIDTH = metrics.widthPixels;
        BOX_HEIGHT = metrics.heightPixels;
        Random r = new Random();
        radius = ((r.nextFloat() * (70 - 3) + 3));
        /* Main constructor of balls
         *  Randomize Vector's and color for each */
        velocity = new Vector2d((r.nextFloat() * (4f - 0.1f) + 0.1f),
                (r.nextFloat() * (4f - 0.1f) + 0.1f));
        position = new Vector2d((r.nextInt(BOX_WIDTH/2)), (r.nextInt(BOX_HEIGHT/2))
                /*(r.nextInt() * ((BOX_WIDTH - radius) - BOX_WIDTH / 2) + BOX_WIDTH / 2),
                (r.nextInt() * ((BOX_HEIGHT - radius) - BOX_HEIGHT / 2) + BOX_HEIGHT / 2)*/);
        mass = radius * Constants.pi;

        /*
         * Randomize colors and transparency
         */
        transp = (r.nextInt() * (255 - 100) + 150);
        red = r.nextInt(254);
        green = r.nextInt(254);
        blue = r.nextInt(254);

    }

    public void paint(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        Paint ballColor = new Paint();
        ballColor.setARGB(transp, red, green, blue);
        canvas.drawCircle(position.getX(), position.getY(), radius, ballColor);
    }

    public float getRadius() {
        return radius;
    }

    public float getMass() {
        return mass;
    }

    public void setRadius(float rad) {
        this.radius = rad;
    }

    public void movePhysics() {
        /*
         * Move the ball normally by X-axis and Y-axis
         * */
        position.set(position.getX() + velocity.getX(),
                position.getY() + velocity.getY());

        if (position.getX() - radius < 0) {
            velocity.setX(-velocity.getX());
            position.setX(radius);
        } else if (position.getX() + radius > BOX_WIDTH) {
            velocity.setX(-velocity.getX()); // Reflect along normal
            position.setX(BOX_WIDTH - radius);           // // Re-position the ball at the edge
        }

        if (position.getY() - radius < 0) {
            velocity.setY(-velocity.getY());
            position.setY(radius);
        } else if (position.getY() + radius > BOX_HEIGHT) {
            velocity.setY(-velocity.getY()); // Reflect along normal
            position.setY(BOX_HEIGHT - radius);
        }
    }

    public boolean colliding(Ball ball) {
        float xd = position.getX() - ball.position.getX();
        float yd = position.getY() - ball.position.getY();

        float sumRadius = getRadius() + ball.getRadius();
        float sqrRadius = sumRadius * sumRadius;

        float distSqr = (xd * xd) + (yd * yd);

        return distSqr <= sqrRadius;
    }

    public void resolveCollision(Ball ball, boolean truth) {
        if (truth) {
            // get the mtd
            Vector2d delta = (position.subtract(ball.position));
            float r = getRadius() + ball.getRadius();
            float dist2 = delta.dot(delta);

            if (dist2 > r * r) return; // they aren't colliding

            float d = delta.getLength();

            Vector2d mtd;
            if (d != 0.0f) {
                mtd = delta.multiply(((getRadius() + ball.getRadius()) - d) / d); // minimum translation distance to push balls apart after intersecting

            } else // Special case. Balls are exactly on top of eachother.  Don't want to divide by zero.
            {
                d = ball.getRadius() + getRadius() - 1.0f;
                delta = new Vector2d(ball.getRadius() + getRadius(), 0.0f);

                mtd = delta.multiply(((getRadius() + ball.getRadius()) - d) / d);
            }

            // resolve intersection
            float mass1 = 1 / getMass(); // inverse mass quantities
            float mass2 = 1 / ball.getMass();

            // push-pull them apart
            position = position.add(mtd.multiply(mass1 / (mass1 + mass2)));
            ball.position = ball.position.subtract(mtd.multiply(mass2 / (mass1 + mass2)));

            // impact speed
            Vector2d v = (this.velocity.subtract(ball.velocity));
            float vn = v.dot(mtd.normalize());

            // sphere intersecting but moving away from each other already
            if (vn > 0.0f) return;

            // collision impulse
            float i = (-(1.0f + Constants.restitution) * vn) / (mass1 + mass2);
            Vector2d impulse = mtd.multiply(i);

            // change in momentum
            this.velocity = this.velocity.add(impulse.multiply(mass1));
            ball.velocity = ball.velocity.subtract(impulse.multiply(mass2));
       }
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getTransp() {
        return  transp;
    }

    public void setARGB (int t, int r, int g, int b) {
        this.transp = t;
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public void eatSmall(Ball ball, int j, int i, ArrayList<Ball> temp, boolean t) {
        if (t) {
            if (getRadius() > ball.getRadius()) {
                setRadius(getRadius() + ball.getRadius() / 5);
                temp.remove(j);
            } else {
                ball.setRadius(ball.getRadius() + getRadius() / 5);
                temp.remove(i);
            }
            if (getRadius() >= 200) temp.remove(i);
            if (ball.getRadius() >= 200) temp.remove(j);
        }
    }

    public void reverseColor(Ball b1, boolean b) {
        if (b) {
            int ab1, rb1, gb1, bb1;
            ab1 = b1.getTransp();
            rb1 = b1.getRed();
            gb1 = b1.getGreen();
            bb1 = b1.getBlue();
            b1.setARGB(this.getTransp(), this.getRed(), this.getGreen(), this.getBlue());
            this.setARGB(ab1,rb1, gb1, bb1);
        }
    }
}
