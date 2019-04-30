package com.example.ultrafalco;

public class Vector {
    public double i, j, k;

    public Vector() {
        this(0, 0, 0);
    }
    public Vector(double i, double j) {
        this(i, j, 0);
    }
    public Vector(double i, double j, double k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }
    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }
    public double magnitudeSquared() {
        return i * i + j * j + k * k;
    }
    public double facing() {
        return Math.atan2(j, i);
    }
    public double dot(Vector v) {
        return i * v.i + j * v.j + k * v.k;
    }
    public double angleBetween(Vector v) {
        return Math.acos(this.dot(v) / Math.sqrt(this.magnitudeSquared() * v.magnitudeSquared()));
    }
    public void setMagnitude(double len) {
        this.multiply(len / magnitude());
    }
    public void unit() {
        this.divide(magnitude());
    }
    public void constrain(double d) {
        if (this.magnitudeSquared() > d * d) this.setMagnitude(d);
    }
    public void add(double a, double b, double c) {
        i += a;
        j += b;
        k += c;
    }
    public void add(double a, double b) {
        add(a, b, 0);
    }
    public void add(Vector v) {
        add(v.i, v.j, v.k);
    }
    public static Vector add(Vector a, Vector b) {
        return new Vector(a.i + b.i, a.j + b.j, a.k + b.k);
    }
    public static Vector add(Vector a, double x, double y, double z) {
        return new Vector(a.i + x, a.j + y, a.k + z);
    }
    public static Vector add(Vector a, double x, double y) {
        return add(a, x, y, 0);
    }
    public void subtract(Vector v) {
        this.i -= v.i;
        this.j -= v.j;
        this.k -= v.k;
    }
    public static Vector subtract(Vector a, Vector b) {
        return new Vector(a.i - b.i, a.j - b.j, a.k - b.k);
    }
    public void multiply(double d) {
        this.i *= d;
        this.j *= d;
        this.k *= d;
    }
    public static Vector multiply(Vector v, double d) {
        return new Vector(v.i * d, v.j * d, v.k * d);
    }
    public void divide(double d) {
        multiply(1.0 / d);
    }
    public static Vector divide(Vector v, double d) {
        return multiply(v, 1.0 / d);
    }
    public Vector cross(Vector v) {
        double i = this.j * v.k - this.k * v.j, j = this.i * v.k - this.k * v.i, k = this.i * v.j - this.j * v.i;
        return new Vector(i, -j, k);
    }
    public void limit(double d) {
        if (this.magnitude() > d) this.setMagnitude(d);
    }
    public String toString() {
        return toString(2);
    }
    public String toString(int n) {
        return String.format(String.format("<%%.%1$df,%%.%1$df,%%.%1$df>", n), i, j, k);
    }
    public static Vector copy(Vector v) {
        return new Vector(v.i, v.j, v.k);
    }
    public static Vector random2D() {
        double t = Math.random() * Math.PI * 2;
        return new Vector(Math.cos(t), Math.sin(t));
    }
    public static Vector random2D(double mag) {
        double t = Math.random() * Math.PI * 2;
        return new Vector(mag * Math.cos(t), mag * Math.sin(t));
    }
    public static Vector random2D(double minMag, double maxMag) {
        double t = Math.random() * Math.PI * 2, mag = Math.random() * (maxMag - minMag) + minMag;
        return new Vector(mag * Math.cos(t), mag * Math.sin(t));
    }
    public static Vector randomPositive2D() {
        double t = Math.random() * Math.PI / 2;
        return new Vector(Math.cos(t), Math.sin(t));
    }
    public static Vector randomPositive2D(double mag) {
        double t = Math.random() * Math.PI / 2;
        return new Vector(mag * Math.cos(t), mag * Math.sin(t));
    }
    public int getX() {
        return (int) i;
    }
    public int getY() {
        return (int) j;
    }
    public int getZ() {
        return (int) k;
    }
    public void set(double newI, double newJ, double newK) {
        set(newI, newJ);
        k = newK;
    }
    public void set(double newI, double newJ) {
        i = newI;
        j = newJ;
    }
    public void set(Vector v) {
        set(v.i, v.j, v.k);
    }
    public double distance(Vector v) {
        return Math.sqrt(distanceSquared(v));
    }
    public double distanceSquared(Vector v) {
        double dI = v.i - this.i, dJ = v.j - this.j, dK = v.k - this.k;
        return dI * dI + dJ * dJ + dK * dK;
    }
}

