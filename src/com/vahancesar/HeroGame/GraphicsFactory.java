package com.vahancesar.HeroGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GraphicsFactory implements MatrixElements{
	public static Bitmap mBarrier = null;

	 public static Bitmap getJewelBitMap(int type) {

		switch (type) {
		case BARRIER:
			return mBarrier;
		
		}
		return null;
	}
	 
	 public static void loadBitmaps(Context c) {
			if (mBarrier != null)
				return; // Already loaded
			
			BitmapFactory.Options mOptions = new BitmapFactory.Options();
			mOptions.inScaled = false;
			
			mBarrier = BitmapFactory.decodeResource(c.getResources(), R.drawable.normalblock, mOptions);
		}
}
