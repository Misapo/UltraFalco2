package com.example.ultrafalco;

import android.graphics.Point;

class Ball {
    int rad;
    Vector pos, vel;

    Ball(Vector p, Vector v, int r) {
        pos = p;
        vel = v;
        rad = r;
    }
    Ball(Vector p, int r) {
        this(p, new Vector(), r);
    }
    void update() {
        pos.add(vel);
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
    private double constrain(double a, int min, int max) {
        return Math.min(Math.max(a, min), max);
    }
}
