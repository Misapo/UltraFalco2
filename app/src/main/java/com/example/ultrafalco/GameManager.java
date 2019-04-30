package com.example.ultrafalco;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class GameManager implements SensorEventListener {
    private double xAcc, yAcc;
    private Point size;
    private Ball ball;

    private final Paint RED;

    GameManager(Point s) {
        xAcc = 0;
        yAcc = 0;

        size = s;
        ball = new Ball(new Vector(size.x / 2.0, size.y / 2.0), 50);

        RED = new Paint();
        RED.setARGB(255, 255, 0, 0);
        RED.setTextSize(80);
    }

    void update() {
        //vel.set(xAcc * 6.5, yAcc * 6.5);
        Vector ballAcc = new Vector(xAcc, yAcc);
        ballAcc.subtract(ball.getDrag(0.0015));

        ball.update(ballAcc);
        ball.walls(size);
    }

    void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
//        canvas.drawText(String.format(Locale.getDefault(), "x: %.2f%ny: %.2f%nz: %.2f", xAcc, yAcc, zAcc), 100, 100, RED);
        canvas.drawCircle(ball.pos.getX(), ball.pos.getY(), ball.rad, RED);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xAcc = -event.values[0];
        yAcc = event.values[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
