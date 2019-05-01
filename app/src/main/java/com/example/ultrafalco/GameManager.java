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

    private final Paint RED, WHITE, SCORE;
    private final static List<Paint> colors = new ArrayList<>();

    GameManager(Point s) {
        xAcc = 0;
        yAcc = 0;

        size = s;
        center = new Vector(size.x / 2.0, size.y / 2.0);
        reset();

        RED = initPaint(255, 0, 0);
        RED.setAlpha(255);
        SCORE = initPaint(255, 255, 255);
        SCORE.setAlpha(255);
        SCORE.setTextSize(80);
        WHITE = initPaint(255, 255, 255);
        WHITE.setAlpha(255);
        WHITE.setTextSize(200);
        WHITE.setTextAlign(Paint.Align.CENTER);

        colors.add(initPaint(0, 0, 255));
        colors.add(initPaint(206, 59, 199));
        colors.add(initPaint(79, 206, 59));
    }

    int update() {
        frameCounter++;

        if (Math.random() < Math.max(0.025, Math.cbrt(frameCounter) / 400.0)) {
            Vector pos = Vector.add(Vector.random2D(size.y), new Vector(size.x / 2.0, size.y / 2.0));
            Vector vel = Vector.add(Vector.random2D(10), Vector.subtract(ball.pos, pos));
            vel.setMagnitude(Math.max(1, Math.random() * 4));
            missiles.add(new Ball(pos, vel, (int) (Math.random() * 90 + 20), randomPick()));
        }

        double sen = 6.5;
        ball.vel.set(sen * xAcc, sen * yAcc);
        ball.update();
        ball.walls(size);

        for (int i = missiles.size() - 1; i >= 0; i--) {
            Ball b = missiles.get(i);
            if (b.pos.distanceSquared(center) < size.y * size.y * 5) {
                b.update();
                if (b.pos.distance(ball.pos) < 0.95 * (b.rad + ball.rad)) {
                    return (int) frameCounter;
                }
            } else {
                missiles.remove(i);
            }
        }
        return -1;
    }

    private Paint randomPick() {
        return colors.get((int) (Math.random() * colors.size()));
    }

    void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawText("" + (frameCounter), 20, 100, SCORE);
        //canvas.drawRect(20, 100, 200, 200, BLACK);
        canvas.drawCircle(ball.pos.getX(), ball.pos.getY(), ball.rad, RED);
        missiles.forEach(r -> canvas.drawCircle(r.pos.getX(), r.pos.getY(), r.rad, r.color));
    }
    private void reset() {
        frameCounter = 0;
        ball = new Ball(new Vector(size.x / 2.0, size.y / 2.0), 50);
        missiles = new ArrayList<>();
    }
    void updateLeaderboard(Canvas c, int score) {
        c.drawColor(0x99333333);
        int xPos = (c.getWidth() / 2);
        int yPos = (int) ((c.getHeight() / 2) - ((WHITE.descent() + WHITE.ascent()) / 2)) ;
        c.drawText("YOU LOSE", xPos, yPos, WHITE);
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
        p.setARGB(200, r, g, b);
        return p;
    }
}
