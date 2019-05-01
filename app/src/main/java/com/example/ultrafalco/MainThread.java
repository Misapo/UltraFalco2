package com.example.ultrafalco;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private GameView gameView;

    MainThread(SurfaceHolder s, GameView g) {
        super();
        surfaceHolder = s;
        gameView = g;
    }
    @Override
    public void run() {
        long startTime, timeMillis, waitTime;
        int targetFPS = 60;
        long targetTime = 1000 / targetFPS;
        Canvas canvas;

        while (!Thread.interrupted()) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    gameView.draw(canvas);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        // e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if (waitTime > 0) {
                    sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
