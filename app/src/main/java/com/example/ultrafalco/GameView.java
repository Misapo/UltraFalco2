package com.example.ultrafalco;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Locale;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    private MainThread thread;
    private SensorManager senMan;

    private double xAcc, yAcc, zAcc;

    private final Point size;

    private final Paint RED;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        senMan = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        size = new Point();
        Display d = ((Activity) context).getWindowManager().getDefaultDisplay();
        d.getSize(size);

        RED = new Paint();
        RED.setARGB(255, 255, 0, 0);
        RED.setTextSize(100);

        xAcc = 0;
        yAcc = 0;
        zAcc = 0;
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        if (c == null) {
            return;
        }
        c.drawColor(Color.WHITE);
        c.drawText(String.format(Locale.getDefault(), "x: %.2f%ny: %.2f%nz: %.2f", xAcc, yAcc, zAcc), 100, 100, RED);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            xAcc = event.values[0];
            yAcc = event.values[1];
            zAcc = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        senMan.registerListener(this, senMan.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_GAME);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        senMan.unregisterListener(this);
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
}
