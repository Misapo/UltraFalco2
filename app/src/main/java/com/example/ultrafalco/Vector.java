package com.example.ultrafalco;

class Vector {
    double i, j;

    Vector() {
        this(0, 0);
    }
    Vector(double i, double j) {
        this.i = i;
        this.j = j;
    }
    void setMagnitude(double len) {
        this.multiply(len / Math.sqrt(i * i + j * j));
    }
    private void add(double a, double b) {
        i += a;
        j += b;
    }
    void add(Vector v) {
        add(v.i, v.j);
    }
    static Vector add(Vector a, Vector b) {
        return new Vector(a.i + b.i, a.j + b.j);
    }
    static Vector subtract(Vector a, Vector b) {
        return new Vector(a.i - b.i, a.j - b.j);
    }
    private void multiply(double d) {
        this.i *= d;
        this.j *= d;
    }
    static Vector random2D(double mag) {
        double t = Math.random() * Math.PI * 2;
        return new Vector(mag * Math.cos(t), mag * Math.sin(t));
    }
    int getX() {
        return (int) i;
    }
    int getY() {
        return (int) j;
    }
    void set(double newI, double newJ) {
        i = newI;
        j = newJ;
    }
    double distance(Vector v) {
        return Math.sqrt(distanceSquared(v));
    }
    double distanceSquared(Vector v) {
        double dI = v.i - this.i, dJ = v.j - this.j;
        return dI * dI + dJ * dJ;
    }
}

