package com.vahancesar.HeroGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Jewel implements MatrixElements{
	public static Bitmap mBlueBitmap = null;
	public static Bitmap mYellowBitmap = null;
	public static Bitmap mRedBitmap = null;
	public static Bitmap mGreenBitmap = null;
	public static Bitmap mCrystalBitmap = null;
	public static Bitmap mZirconBitmap = null;
	
	
	Jewel() {
	}

	public static int getJewelValue(int type) {

		switch (type) {
		case BLUE_JEWEL:
			return 5;
		case YELLOW_JEWEL:
			return 10;
		case RED_JEWEL:
			return 20;
		case GREEN_JEWEL:
			return 30;
		case CRYSTAL_JEWEL:
			return 100;
		case ZIRCON_JEWEL:
			return 50;
		}
		return 0;
	}

	public static Bitmap getJewelBitMap(int type) {

		switch (type) {
		case BLUE_JEWEL:
			return mBlueBitmap;
		case YELLOW_JEWEL:
			return mYellowBitmap;
		case RED_JEWEL:
			return mRedBitmap;
		case GREEN_JEWEL:
			return mGreenBitmap;
		case CRYSTAL_JEWEL:
			return mCrystalBitmap;
		case ZIRCON_JEWEL:
			return mZirconBitmap;
		}
		return null;
	}

	
	public static void loadBitmaps(Context c) {
		if (mBlueBitmap != null)
			return; // Already loaded
		
		BitmapFactory.Options mOptions = new BitmapFactory.Options();
		mOptions.inScaled = false;

		mBlueBitmap = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.bluejewel0001, mOptions);
		mYellowBitmap = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.yellowjewel0001, mOptions);
		mRedBitmap = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.redjewel0001, mOptions);
		mGreenBitmap = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.greenjewel0001, mOptions);
		mCrystalBitmap = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.diamond0001, mOptions);
		mZirconBitmap = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.purple0001, mOptions);
	}

}
