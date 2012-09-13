package com.vahancesar.HeroGame;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LevelActivity extends Activity implements
		SurfaceHolder.Callback {

	private Hero mHero;
	private Level mLevel;

	public static int mWidth;
	public static int mHeight;
	private SurfaceHolder mHolder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWidth = getWindowManager().getDefaultDisplay().getWidth();
		mHeight = getWindowManager().getDefaultDisplay().getHeight();

		SurfaceView mainSurfaceView = new SurfaceView(this);
		mHolder = mainSurfaceView.getHolder();

		setContentView(mainSurfaceView);
		mainSurfaceView.getHolder().addCallback(this);

		mHero = new Hero(this.getApplicationContext());
		mLevel = new Level(0, mWidth, mHeight, this, mHero);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mLevel.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mLevel.startLevel();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int frmt, int w, int h) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//mLevel.stopLevel();
	}

	protected void onDraw(Canvas c) {
		mLevel.nextFrame(c);
	}
		
	public SurfaceHolder getHolder(){ return mHolder;}

	


}
