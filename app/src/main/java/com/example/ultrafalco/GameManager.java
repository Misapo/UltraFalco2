package com.example.ultrafalco;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class GameManager implements SensorEventListener {
    private long frameCounter;
    private double xAcc, yAcc, sen;
    private Point size;
    private Ball ball;
    private List<Ball> missiles;
    private final Vector center;
    private SharedPreferences sPref;

    private final Paint RED, WHITE, SCORE;
    private final static List<Paint> colors = new ArrayList<>();
    private final static String[] MONTHS = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    GameManager(Point s, SharedPreferences preferences) {
        xAcc = 0;
        yAcc = 0;
        sPref = preferences;
        sen = sPref.getInt("Volume", 6);

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
        WHITE.setTextSize(180);
        WHITE.setTextAlign(Paint.Align.CENTER);

        colors.add(initPaint(0, 0, 255));
        colors.add(initPaint(206, 59, 199));
        colors.add(initPaint(79, 206, 59));
    }

    int update() {
        frameCounter++;

        if (Math.random() < Math.max(0.025, Math.cbrt(frameCounter) / 400.0)) {
            Vector pos = Vector.add(Vector.random2D(size.y / 1.4), new Vector(size.x / 2.0, size.y / 2.0));
            Vector vel = Vector.add(Vector.random2D(10), Vector.subtract(ball.pos, pos));
            vel.setMagnitude(Math.max(1, Math.random() * 4));
            missiles.add(new Ball(pos, vel, (int) (Math.random() * 90 + 20), randomPick()));
        }

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
        int yPos = (int) (-SCORE.ascent());
        canvas.drawText("" + (frameCounter), 20, yPos, SCORE);
        //canvas.drawRect(20, 100, 200, 200, BLACK);
        canvas.drawCircle(ball.pos.getX(), ball.pos.getY(), ball.rad, RED);
        missiles.forEach(r -> canvas.drawCircle(r.pos.getX(), r.pos.getY(), r.rad, r.color));
    }
    void reset() {
        frameCounter = 0;
        ball = new Ball(new Vector(size.x / 2.0, size.y / 2.0), 50);
        missiles = new ArrayList<>();
    }
    void updateLeaderboard(Canvas c, int score) {
        c.drawColor(0x99333333);
        int xPos = (c.getWidth() / 2);
        int yPos = (int) ((c.getHeight() / 2) - ((WHITE.descent() + WHITE.ascent()) / 2)) ;
        c.drawText("GAME OVER", xPos, yPos, WHITE);
        Set<String> scores = sPref.getStringSet("Scores", new HashSet<>());
        if (scores == null) {
            return;
        }
        List<String> scoreArr = new ArrayList<>(Arrays.asList(scores.stream().sorted().toArray(String[]::new)));
        if (scoreArr.size() < 10) {
            scoreArr.add(getLeaderboardString(score));
        } else {
            for (int i = 0; i < scoreArr.size(); i++) {
                int leaderScore = Integer.parseInt(scoreArr.get(i).split(": ")[0]);
                if (score > leaderScore) {
                    scoreArr.add(i, getLeaderboardString(score));
                    scoreArr.remove(scoreArr.size() - 1);
                    break;
                }
            }
        }
        //scoreArr.add("DJALSKJDLA");
        Set<String> set = new HashSet<>(scoreArr);
        //set.add("DJALSKJDLA");
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("hi", "test");
        editor.putStringSet("Scores", set);
        editor.apply();
    }
    private String getLeaderboardString(int score) {
        String mon = MONTHS[Calendar.getInstance().get(Calendar.MONTH)];
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return String.format(Locale.getDefault(), "%d:  %s %d", score, mon, day);
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
