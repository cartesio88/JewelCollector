package com.vahancesar.HeroGame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.content.Context;

public class Sprite {
	private Bitmap mBitmaps[];
	private long mTiming;
	private int mCurrentBitmap;
	private int mNImages;
	private long startTime;
	private Context mContext;
	BitmapFactory.Options mOptions;
	
	Sprite(int nImages, long timing, Context c) {
		mBitmaps = new Bitmap[nImages];
		mTiming = timing;
		mNImages = 0;
		mCurrentBitmap = 0;
		mContext = c;
		startTime = System.currentTimeMillis();
		mOptions = new BitmapFactory.Options();
		mOptions.inScaled = false;
	}

	public void addImage(int id) {
		if (mNImages < mBitmaps.length) {
			mBitmaps[mNImages] = BitmapFactory.decodeResource(mContext.getResources(), id, mOptions);
			mNImages++;
		}
	}
	
	public void addImageInversed(int id) {
		if (mNImages < mBitmaps.length) {
			Bitmap aux = BitmapFactory.decodeResource(mContext.getResources(), id, mOptions);
			Matrix m = new Matrix();
			m.preScale(-1, 1);
			mBitmaps[mNImages] = Bitmap.createBitmap(aux, 0, 0, aux.getWidth(), aux.getHeight(), m, false);
			mBitmaps[mNImages].setDensity(DisplayMetrics.DENSITY_DEFAULT);
			mNImages++;
		}
	}
	
	public Bitmap getNextSprite(){
		if((System.currentTimeMillis() - startTime)>mTiming){
			startTime = System.currentTimeMillis();
			mCurrentBitmap = (mCurrentBitmap+1) % mNImages;
		}
		
		return mBitmaps[mCurrentBitmap];
	}
	
	public Bitmap getSprite(int n){
		if(n<mNImages) return mBitmaps[n];
		return null;
	}

}
