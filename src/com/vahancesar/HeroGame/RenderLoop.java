package com.vahancesar.HeroGame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/* Updates and render*/
public class RenderLoop extends Thread {
	private final int SPF = 1000 / 30;
	private SurfaceHolder mHolder;
	private LevelActivity mLevelActivity;
	private long startTime, sleepTime;
	private boolean mRunning = true;

	RenderLoop(LevelActivity gameActivity) {
		mHolder = gameActivity.getHolder();
		mLevelActivity = gameActivity;
	}

	public void stopRender() {
		mRunning = false;
	}

	public void run() {
		while (mRunning) {

			startTime = System.currentTimeMillis();
			Canvas c = null;
			c = mHolder.lockCanvas();
			if (c != null) {
				mLevelActivity.onDraw(c);
				mHolder.unlockCanvasAndPost(c);
			}
			// Lets wait
			sleepTime = SPF - (System.currentTimeMillis() - startTime);
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
