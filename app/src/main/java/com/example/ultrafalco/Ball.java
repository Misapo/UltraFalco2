package com.example.ultrafalco;

import android.graphics.Point;

class Ball extends Ray {
    int rad;
    Ball(Vector pos, int r) {
        super(pos);
        rad = r;
    }
    void walls(Point size) {
        if (pos.i < rad || pos.i + rad > size.x) {
            pos.i = constrain(pos.i, rad, size.x - rad);
            vel.i *= -0.9;
        }
        if (pos.j < rad || pos.j + rad > size.y) {
            pos.j = constrain(pos.j, rad, size.y - rad);
            vel.j *= -0.9;
        }
    }
    Vector getDrag(double cd) {
        return new Vector(vel.i * Math.abs(vel.i) * cd, vel.j * Math.abs(vel.j) * cd);
    }
    private double constrain(double a, int min, int max) {
        return Math.min(Math.max(a, min), max);
    }
}
