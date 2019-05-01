package com.example.ultrafalco;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private SensorManager senMan;
    private GameManager manager;

    public GameView(Context context) {
        this(context, ((Activity) context).getPreferences(Context.MODE_PRIVATE));
    }
    public GameView(Context context, SharedPreferences sPref) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        senMan = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        this.setOnTouchListener((v, event) -> {
            v.performClick();
            if (!thread.isAlive()) {
                manager.reset();
                thread = new MainThread(getHolder(), this);
                thread.start();
            }
            return true;
        });

        Point size = new Point();
        ((Activity) context).getWindowManager().getDefaultDisplay().getSize(size);
        manager = new GameManager(size, sPref);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        if (c == null) {
            return;
        }
        int cont = manager.update();
        manager.draw(c);

        if (cont > 0) {
            stopThread();
            manager.updateLeaderboard(c, cont);
        }
    }

    private void stopThread() {
        thread.interrupt();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        senMan.registerListener(manager, senMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        senMan.unregisterListener(manager);
        stopThread();
    }
}
