package com.example.ultrafalco;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements SensorEventListener {
    private long frameCounter;
    private double xAcc, yAcc;
    private Point size;
    private Ball ball;
    private List<Ball> missiles;
    private final Vector center;

    private final Paint RED, BLUE;

    GameManager(Point s) {
        xAcc = 0;
        yAcc = 0;

        size = s;
        center = new Vector(size.x / 2.0, size.y / 2.0);
        reset();

        RED = initPaint(255, 0, 0);
        RED.setTextSize(80);
        BLUE = initPaint(0, 0, 255);
        BLUE.setAlpha(200);
    }

    boolean update() {
        frameCounter++;

        if (Math.random() < Math.max(0.01, Math.cbrt(frameCounter) / 400.0)) {
            Vector pos = Vector.add(Vector.random2D(size.y), new Vector(size.x / 2.0, size.y / 2.0));
            Vector vel = Vector.add(Vector.random2D(10), Vector.subtract(ball.pos, pos));
            vel.setMagnitude(Math.max(1, Math.random() * 5));
            missiles.add(new Ball(pos, vel, (int) (Math.random() * 90 + 20)));
        }

        double sen = 6.5;
        ball.vel.set(sen * xAcc, sen * yAcc);
        ball.update();
        ball.walls(size);

        for (int i = missiles.size() - 1; i >= 0; i--) {
            Ball b = missiles.get(i);
            if (b.pos.distanceSquared(center) < size.y * size.y * 5) {
                b.update();
                if (b.pos.distance(ball.pos) < (b.rad + ball.rad)) {
                    return false;
                }
            } else {
                missiles.remove(i);
            }
        }
        return true;
    }

    void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawText("" + (frameCounter), 100, 100, RED);
        canvas.drawCircle(ball.pos.getX(), ball.pos.getY(), ball.rad, RED);
        missiles.forEach(r -> canvas.drawCircle(r.pos.getX(), r.pos.getY(), r.rad, BLUE));
    }
    private void reset() {
        frameCounter = 0;
        ball = new Ball(new Vector(size.x / 2.0, size.y / 2.0), 50);
        missiles = new ArrayList<>();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xAcc = -event.values[0];
        yAcc = event.values[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private Paint initPaint(int r, int g, int b) {
        Paint p = new Paint();
        p.setARGB(255, r, g, b);
        return p;
    }
}
