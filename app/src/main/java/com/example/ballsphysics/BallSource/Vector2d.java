package com.example.ballsphysics.BallSource;

/*
 * Created by Nikita on 26.11.2017.
 */

class Vector2d {

    private float x;
    private float y;

    private Vector2d() {
        this.setX(0);
        this.setY(0);
    }

    Vector2d(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    void setX(float x) {
        this.x = x;
    }

    float getX() {
        return x;
    }

    void setY(float y) {
        this.y = y;
    }

    float getY() {
        return y;
    }

    void set(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    float dot(Vector2d v2) {
        float result;
        result = this.getX() * v2.getX() + this.getY() * v2.getY();
        return result;
    }

    float getLength() {
        return (float) Math.sqrt(getX() * getX() + getY() * getY());
    }

    Vector2d add(Vector2d v2) {
        Vector2d result = new Vector2d();
        result.setX(getX() + v2.getX());
        result.setY(getY() + v2.getY());
        return result;
    }

    Vector2d subtract(Vector2d v2) {
        Vector2d result = new Vector2d();
        result.setX(this.getX() - v2.getX());
        result.setY(this.getY() - v2.getY());
        return result;
    }

    Vector2d multiply(float scaleFactor) {
        Vector2d result = new Vector2d();
        result.setX(this.getX() * scaleFactor);
        result.setY(this.getY() * scaleFactor);
        return result;
    }

    Vector2d normalize() {
        float len = getLength();
        if (len != 0.0f) {
            this.setX(this.getX() / len);
            this.setY(this.getY() / len);
        } else {
            this.setX(0.0f);
            this.setY(0.0f);
        }
        return this;
    }

}
