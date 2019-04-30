package com.example.ultrafalco;

public class Ray {
    Vector pos, vel;

    Ray(Vector p) {
        this(p, new Vector());
    }
    Ray(Vector p, Vector v) {
        pos = p;
        vel = v;
    }

    public void update(Vector acc) {
        vel.add(acc);
        pos.add(vel);
    }
}
